package com.gesabsences.gesabsences.data.Services.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gesabsences.gesabsences.data.Entities.Abscence;
import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Entities.Pointage;
import com.gesabsences.gesabsences.data.Enum.StatutAbscence;
import com.gesabsences.gesabsences.data.Enum.TypeAbscence;
import com.gesabsences.gesabsences.data.Repositories.AbscenceRepository;
import com.gesabsences.gesabsences.data.Repositories.CoursRepository;
import com.gesabsences.gesabsences.data.Repositories.EleveRepository;
import com.gesabsences.gesabsences.data.Repositories.PointageRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AutoAbsenceSchedulerService {

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private EleveRepository eleveRepository;

    @Autowired
    private PointageRepository pointageRepository;

    @Autowired
    private AbscenceRepository absenceRepository;

    private static final ZoneId ZONE_DAKAR = ZoneId.of("Africa/Dakar");

    /**
     * Tâche programmée qui s'exécute toutes les 15 minutes pour vérifier
     * les cours terminés et marquer les élèves non pointés comme absents
     */
    // @Scheduled(cron = "0 0/15 * * * *")

    @PostConstruct
    public void init() {
        marquerAbsencesAutomatiques(); // Lancement immédiat au démarrage
    }

    private List<Cours> getCoursTerminesRecemment(Date maintenant) {
        // Utiliser le fuseau horaire système (PDT dans votre cas)
        ZonedDateTime maintenantLocal = maintenant.toInstant().atZone(ZoneId.systemDefault());
        ZonedDateTime il60Minutes = maintenantLocal.minusMinutes(60);

        Date debutPeriode = Date.from(il60Minutes.toInstant());

        log.info("Recherche des cours terminés entre {} et {} (fuseau local)",
                il60Minutes, maintenantLocal);

        // *** CHANGEMENT MAJEUR : Chercher sur plusieurs jours pour éviter les
        // problèmes de fuseau ***
        // Prendre une plage plus large pour couvrir les décalages de fuseaux horaires
        LocalDate aujourdhuiLocal = LocalDate.now(ZoneId.systemDefault());
        LocalDate hierLocal = aujourdhuiLocal.minusDays(1);
        LocalDate demainLocal = aujourdhuiLocal.plusDays(1);

        // Créer une plage de 3 jours pour être sûr de capturer tous les cours
        Date debutPlage = Date.from(hierLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date finPlage = Date.from(demainLocal.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        log.info("Recherche des cours entre {} et {} (plage étendue)", debutPlage, finPlage);

        List<Cours> coursPlage = coursRepository.findByDateBetween(debutPlage, finPlage);
        log.info("Nombre total de cours trouvés dans la plage étendue: {}", coursPlage.size());

        // *** ALTERNATIVE : Si la requête par date ne fonctionne pas, chercher tous les
        // cours récents ***
        if (coursPlage.isEmpty()) {
            log.warn("Aucun cours trouvé avec findByDateBetween, tentative avec tous les cours...");
            List<Cours> tousLesCours = coursRepository.findAll();

            // Filtrer les cours des 24 dernières heures
            Date il24h = Date.from(maintenantLocal.minusHours(24).toInstant());
            coursPlage = tousLesCours.stream()
                    .filter(cours -> cours.getHeureFin().after(il24h))
                    .collect(Collectors.toList());

            log.info("Nombre de cours trouvés dans les 24 dernières heures: {}", coursPlage.size());
        }

        // Filtrer les cours qui se sont terminés dans la période cible (60 dernières
        // minutes)
        List<Cours> coursTermines = coursPlage.stream()
                .filter(cours -> {
                    boolean estTermine = cours.getHeureFin().before(maintenant);
                    boolean termineRecemment = cours.getHeureFin().after(debutPeriode);

                    log.info("Cours {} - Heure fin: {}, Terminé: {}, Récemment: {}, Maintenant: {}",
                            cours.getId(), cours.getHeureFin(), estTermine, termineRecemment, maintenant);

                    return estTermine && termineRecemment;
                })
                .collect(Collectors.toList());

        log.info("Cours terminés récemment après filtrage: {}", coursTermines.size());
        return coursTermines;
    }

    @Scheduled(fixedRate = 15 * 60 * 1000) // Toutes les 15 minutes
    @Transactional
    public void marquerAbsencesAutomatiques() {
        log.info("=== DÉBUT de la vérification automatique des absences ===");

        try {
            // Utiliser l'heure système (PDT dans votre cas) pour être cohérent
            ZonedDateTime maintenantLocal = ZonedDateTime.now(ZoneId.systemDefault());
            Date maintenant = Date.from(maintenantLocal.toInstant());

            log.info("Heure actuelle (fuseau système): {}", maintenantLocal);
            log.info("Fuseau horaire système: {}", ZoneId.systemDefault());

            // Récupérer tous les cours terminés dans les 60 dernières minutes
            List<Cours> coursTermines = getCoursTerminesRecemment(maintenant);

            log.info("Nombre de cours terminés trouvés: {}", coursTermines.size());

            if (coursTermines.isEmpty()) {
                log.info("Aucun cours terminé récemment. Aucune action nécessaire.");
                return;
            }

            // Traiter chaque cours terminé
            int totalAbsencesCreees = 0;
            for (Cours cours : coursTermines) {
                try {
                    int absencesCreees = traiterCoursTermine(cours, maintenant);
                    totalAbsencesCreees += absencesCreees;

                    log.info("Cours {} - {} absences automatiques créées",
                            cours.getId(), absencesCreees);

                } catch (Exception e) {
                    log.error("Erreur lors du traitement du cours {}: {}",
                            cours.getId(), e.getMessage(), e);
                }
            }

            log.info("=== FIN - Total: {} absences automatiques créées ===", totalAbsencesCreees);

        } catch (Exception e) {
            log.error("Erreur critique dans la tâche automatique d'absences: {}", e.getMessage(), e);
        }
    }

    // Méthode de test pour forcer la vérification d'un cours spécifique
    public void testerCoursSpecifique(String coursId) {
        log.info("=== TEST pour le cours {} ===", coursId);

        try {
            Optional<Cours> coursOpt = coursRepository.findById(coursId);
            if (coursOpt.isPresent()) {
                Cours cours = coursOpt.get();
                Date maintenant = new Date();

                log.info("Cours trouvé: {} - Fin: {}, Maintenant: {}",
                        cours.getId(), cours.getHeureFin(), maintenant);
                log.info("Est terminé: {}", cours.getHeureFin().before(maintenant));

                if (cours.getHeureFin().before(maintenant)) {
                    int absences = traiterCoursTermine(cours, maintenant);
                    log.info("Absences créées pour le test: {}", absences);
                }
            } else {
                log.warn("Cours {} non trouvé", coursId);
            }
        } catch (Exception e) {
            log.error("Erreur lors du test: {}", e.getMessage(), e);
        }
    }

    /**
     * Traite un cours terminé en marquant les élèves non pointés comme absents
     */
    @Transactional
    private int traiterCoursTermine(Cours cours, Date heureTraitement) {
        log.info("Traitement du cours terminé: {} - Classe: {} - Module: {}",
                cours.getId(), cours.getClasse().getNomClasse(), cours.getModule().getNom());

        // 1. Récupérer tous les élèves de la classe
        List<Eleve> elevesClasse = eleveRepository.findByClasse(cours.getClasse());
        log.info("Nombre d'élèves dans la classe: {}", elevesClasse.size());

        if (elevesClasse.isEmpty()) {
            log.warn("Aucun élève trouvé pour la classe {}", cours.getClasse().getNomClasse());
            return 0;
        }

        // 2. Récupérer tous les pointages existants pour ce cours
        List<Pointage> pointagesCours = pointageRepository.findByCoursId(cours.getId());
        List<String> elevesPointes = pointagesCours.stream()
                .map(Pointage::getEtudiant)
                .map(Eleve::getId)
                .collect(Collectors.toList());

        log.info("Nombre d'élèves déjà pointés: {}", elevesPointes.size());

        // 3. Récupérer les absences déjà enregistrées pour ce cours
        List<Abscence> absencesExistantes = absenceRepository.findByCours(cours);
        List<String> elevesAvecAbsence = absencesExistantes.stream()
                .map(a -> a.getEleve().getId())
                .collect(Collectors.toList());

        log.info("Nombre d'élèves avec absences déjà enregistrées: {}", elevesAvecAbsence.size());

        // 4. Identifier les élèves non pointés et sans absence enregistrée
        List<Eleve> elevesAbsents = elevesClasse.stream()
                .filter(eleve -> !elevesPointes.contains(eleve.getId()))
                .filter(eleve -> !elevesAvecAbsence.contains(eleve.getId()))
                .collect(Collectors.toList());

        log.info("Nombre d'élèves à marquer absents: {}", elevesAbsents.size());

        // 5. Créer les absences automatiques
        int absencesCreees = 0;
        for (Eleve eleve : elevesAbsents) {
            try {
                Abscence absence = creerAbsenceAutomatique(eleve, cours, heureTraitement);
                absenceRepository.save(absence);
                absencesCreees++;

                log.debug("Absence automatique créée pour l'élève {} {} (ID: {})",
                        eleve.getPrenom(), eleve.getNom(), eleve.getId());

            } catch (Exception e) {
                log.error("Erreur lors de la création d'absence pour l'élève {}: {}",
                        eleve.getId(), e.getMessage(), e);
            }
        }

        return absencesCreees;
    }

    /**
     * Crée une absence automatique pour un élève
     */
    private Abscence creerAbsenceAutomatique(Eleve eleve, Cours cours, Date heureTraitement) {
        Abscence absence = new Abscence();
        absence.setEleve(eleve);
        absence.setCours(cours);
        absence.setTypeAbscence(TypeAbscence.Absent);
        absence.setStatutAbscence(StatutAbscence.NON_JUSTIFIER);

        // Marquer que c'est une absence automatique (vous pouvez ajouter un champ dans
        // votre entité)
        // absence.setEstAutomatique(true);

        log.debug("Absence automatique créée: Élève {} - Cours {} - Type: {}",
                eleve.getId(), cours.getId(), TypeAbscence.Absent);

        return absence;
    }

    /**
     * Méthode utilitaire pour forcer l'exécution manuelle (pour les tests)
     */
    public void executerVerificationManuelle() {
        log.info("Exécution manuelle de la vérification des absences déclenchée");
        marquerAbsencesAutomatiques();
    }

    /**
     * Tâche de nettoyage qui s'exécute une fois par jour à 1h du matin
     * pour nettoyer les anciens logs ou faire de la maintenance
     */
    @Scheduled(cron = "0 0 1 * * ?") // Tous les jours à 1h00
    public void tacheMaintenanceQuotidienne() {
        log.info("=== Exécution de la tâche de maintenance quotidienne ===");

        try {
            // Ici vous pouvez ajouter des tâches de maintenance
            // par exemple : nettoyer les anciens logs, archiver des données, etc.

            long totalAbsences = absenceRepository.count();
            long totalPointages = pointageRepository.count();

            log.info("Statistiques - Total absences: {}, Total pointages: {}",
                    totalAbsences, totalPointages);

        } catch (Exception e) {
            log.error("Erreur lors de la maintenance quotidienne: {}", e.getMessage(), e);
        }
    }
}