package com.gesabsences.gesabsences.utils.mock;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.gesabsences.gesabsences.data.Entities.*;
import com.gesabsences.gesabsences.data.Enum.NiveauState;
import com.gesabsences.gesabsences.data.Enum.StatutAbscence;
import com.gesabsences.gesabsences.data.Enum.StatutJustification;
import com.gesabsences.gesabsences.data.Enum.TypeAbscence;
import com.gesabsences.gesabsences.data.Repositories.*;
import com.gesabsences.gesabsences.data.Entities.Module;

@RequiredArgsConstructor
@Component
@Order(2)
public class ClasseMock implements CommandLineRunner {

    private final ClasseRepository classeRepository;
    private final NiveauRepository niveauRepository;
    private final ProfesseurRepository professeurRepository;
    private final ProfesseurClasseRepository professeurClasseRepository;
    private final ProfesseurModuleRepository professeurModuleRepository;
    private final ModuleRepository moduleRepository;
    private final EleveRepository eleveRepository;
    private final CoursRepository coursRepository;
    private final AbscenceRepository absenceRepository;
    private final JustificatifRepository justificatifRepository;
    private final AnneeRepository anneeScolaireRepository;
    private final InscriptionRepository inscriptionRepository;

    // Données prédéfinies réduites
    private static final List<String> FIRST_NAMES = Arrays.asList(
            "Mamadou", "Fatou", "Cheikh", "Awa", "Ousmane", "Aminata");

    private static final List<String> LAST_NAMES = Arrays.asList(
            "Diop", "Ndiaye", "Ba", "Fall", "Sow", "Gueye");

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("=== DÉBUT DE LA GÉNÉRATION DES DONNÉES OPTIMISÉES ===");
        // clearDatabase();
        // Vérifier si des données existent déjà
        if (absenceRepository.count() > 0) {
            System.out.println("⚠️ Données déjà existantes, génération annulée");
            return;
        }

        try {
            // Récupérer les modules existants
            List<Module> modules = moduleRepository.findAll();
            if (modules.isEmpty()) {
                System.err.println("❌ ERREUR: Aucun module trouvé. Veuillez d'abord créer les modules.");
                return;
            }
            System.out.println("✅ Modules disponibles : " + modules.size());

            // Générer les données
            generateOptimizedMockData(modules);

            // Afficher les résultats
            printFinalResults();

        } catch (Exception e) {
            System.err.println("❌ ERREUR CRITIQUE dans la génération : " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private void clearDatabase() {
        // Supprimer les dépendances en premier
        absenceRepository.deleteAll();
        justificatifRepository.deleteAll();
        inscriptionRepository.deleteAll();
        coursRepository.deleteAll();
        professeurClasseRepository.deleteAll();
        professeurModuleRepository.deleteAll();

        // Supprimer les entités principales ensuite
        eleveRepository.deleteAll();
        moduleRepository.deleteAll();
        professeurRepository.deleteAll();
        classeRepository.deleteAll();
        niveauRepository.deleteAll();
        anneeScolaireRepository.deleteAll();

        System.out.println("✅ Base de données nettoyée avec succès.");
    }

    @Transactional
    private void generateOptimizedMockData(List<Module> modules) {
        System.out.println("🚀 Génération des données optimisées...");
        Random random = new Random();

        // Phase 1: Créer l'année scolaire active
        AnneeScolaire anneeScolaire = createAnneeScolaire();
        System.out.println("✅ Phase 1 terminée: Année scolaire créée");

        // Phase 2: Créer MOINS de niveaux et classes (seulement 2 niveaux)
        List<Classe> allClasses = createReducedNiveauxAndClasses();
        System.out.println("✅ Phase 2 terminée: " + allClasses.size() + " classes créées");

        // Phase 3: Créer MOINS de professeurs
        List<Professeur> allProfesseurs = createReducedProfesseurs(modules);
        System.out.println("✅ Phase 3 terminée: " + allProfesseurs.size() + " professeurs créés");

        // Phase 4: Assigner les professeurs aux classes
        assignProfesseursToClasses(allProfesseurs, allClasses);
        System.out.println("✅ Phase 4 terminée: Professeurs assignés aux classes");

        // Phase 5: Créer MOINS d'élèves avec inscriptions
        createOptimizedStudentsWithInscriptions(allClasses, anneeScolaire);
        System.out.println("✅ Phase 5 terminée: Élèves créés avec inscriptions");

        // Phase 6: Créer les cours sur 3 JOURS SEULEMENT (lundi, mardi, mercredi)
        List<Cours> coursCrees = createOptimizedCourses(allProfesseurs, allClasses, modules);
        System.out.println("✅ Phase 6 terminée: " + coursCrees.size() + " cours créés");

        // Phase 7: Créer MOINS d'absences
        if (!coursCrees.isEmpty()) {
            generateOptimizedAbsences(coursCrees);
            System.out.println("✅ Phase 7 terminée: Absences générées");
        }
    }

    private AnneeScolaire createAnneeScolaire() {
        System.out.println("📅 Création de l'année scolaire...");

        AnneeScolaire anneeScolaire = new AnneeScolaire();
        anneeScolaire.setLibelle("2024-2025");
        anneeScolaire.setEstActive(true);

        AnneeScolaire savedAnneeScolaire = anneeScolaireRepository.save(anneeScolaire);
        System.out.println("   ✓ Année scolaire créée: " + savedAnneeScolaire.getLibelle());

        return savedAnneeScolaire;
    }

    private List<Classe> createReducedNiveauxAndClasses() {
        System.out.println("📚 Création des niveaux et classes (RÉDUIT)...");
        List<Classe> allClasses = new ArrayList<>();

        // SEULEMENT 2 niveaux au lieu de 4
        List<NiveauState> niveauxStates = Arrays.asList(
                NiveauState.SIXIEME,
                NiveauState.CINQUIEME);

        for (NiveauState niveauState : niveauxStates) {
            // Créer le niveau
            Niveau niveau = new Niveau();
            niveau.setNiveauState(niveauState);
            niveau = niveauRepository.save(niveau);

            // SEULEMENT 1 classe par niveau
            Classe classe = new Classe();
            classe.setNomClasse(niveauState.toString() + " - Classe A");
            classe.setNiveau(niveau);
            classe.setEffectifs(0);

            Classe savedClasse = classeRepository.save(classe);
            allClasses.add(savedClasse);

            System.out.println("   ✓ Classe créée: " + savedClasse.getNomClasse());
        }

        return allClasses;
    }

    private List<Professeur> createReducedProfesseurs(List<Module> modules) {
        System.out.println("👨‍🏫 Création des professeurs (RÉDUIT)...");
        List<Professeur> professeurs = new ArrayList<>();
        Random random = new Random();

        // SEULEMENT 3 professeurs au lieu de 6-8
        int nombreProfesseurs = 3;

        for (int i = 0; i < nombreProfesseurs; i++) {
            try {
                // Créer le professeur
                Professeur professeur = new Professeur();
                professeur.setNom(LAST_NAMES.get(i % LAST_NAMES.size()));
                professeur.setPrenom(FIRST_NAMES.get(i % FIRST_NAMES.size()));
                professeur.setEmail(professeur.getPrenom().toLowerCase() + "." +
                        professeur.getNom().toLowerCase() + "@ecole.sn");
                professeur.setDateNaissance(
                        LocalDate.of(1975 + i, 1 + random.nextInt(12), 1 + random.nextInt(28)));
                professeur.setSexe(i % 2 == 0 ? "M" : "F");
                professeur.setAdresse((10 + i * 10) + " Avenue Bourguiba");
                professeur.setVille("Dakar");

                // Sauvegarder le professeur
                Professeur savedProfesseur = professeurRepository.save(professeur);

                // Assigner des modules au professeur
                assignModulesToProfesseur(savedProfesseur, modules);

                professeurs.add(savedProfesseur);
                System.out.println(
                        "   ✓ Professeur créé: " + savedProfesseur.getPrenom() + " " + savedProfesseur.getNom());

            } catch (Exception e) {
                System.err.println("   ❌ Erreur création professeur " + (i + 1) + ": " + e.getMessage());
            }
        }

        return professeurs;
    }

    private void assignModulesToProfesseur(Professeur professeur, List<Module> modules) {
        Random random = new Random();

        // Assigner 2 modules par professeur pour avoir de la variété
        List<Module> shuffledModules = new ArrayList<>(modules);
        Collections.shuffle(shuffledModules);

        int nombreModules = Math.min(2, modules.size());

        for (int i = 0; i < nombreModules; i++) {
            try {
                ProfesseurModule professeurModule = new ProfesseurModule();
                professeurModule.setProfesseur(professeur);
                professeurModule.setModule(shuffledModules.get(i));
                professeurModuleRepository.save(professeurModule);
            } catch (Exception e) {
                System.err.println("     ❌ Erreur assignation module: " + e.getMessage());
            }
        }
    }

    private void assignProfesseursToClasses(List<Professeur> professeurs, List<Classe> classes) {
        System.out.println("🔗 Attribution des professeurs aux classes...");
        Random random = new Random();

        if (professeurs.isEmpty() || classes.isEmpty()) {
            System.err.println("❌ Impossible d'assigner: professeurs ou classes vides");
            return;
        }

        // Étape 1: Assigner un professeur principal à chaque classe
        for (int i = 0; i < classes.size(); i++) {
            try {
                Classe classe = classes.get(i);
                Professeur profPrincipal = professeurs.get(i % professeurs.size());
                classe.setProfPrincipal(profPrincipal);
                classeRepository.save(classe);

                System.out.println("   ✓ Prof principal " + classe.getNomClasse() +
                        ": " + profPrincipal.getPrenom() + " " + profPrincipal.getNom());
            } catch (Exception e) {
                System.err.println("   ❌ Erreur assignation prof principal: " + e.getMessage());
            }
        }

        // Étape 2: Créer les relations ProfesseurClasse - CHAQUE PROF ENSEIGNE DANS
        // TOUTES LES CLASSES
        for (Professeur professeur : professeurs) {
            for (Classe classe : classes) {
                try {
                    ProfesseurClasse professeurClasse = new ProfesseurClasse();
                    professeurClasse.setProfesseur(professeur);
                    professeurClasse.setClasse(classe);
                    professeurClasseRepository.save(professeurClasse);

                    System.out.println("   ✓ Relation créée: " + professeur.getPrenom() +
                            " -> " + classe.getNomClasse());
                } catch (Exception e) {
                    System.err.println("   ❌ Erreur création relations: " + e.getMessage());
                }
            }
        }
    }

    private void createOptimizedStudentsWithInscriptions(List<Classe> classes, AnneeScolaire anneeScolaire) {
        System.out.println("👨‍🎓 Création des élèves avec inscriptions (OPTIMISÉ)...");
        Random random = new Random();
        int totalEleves = 0;

        for (Classe classe : classes) {
            try {
                // SEULEMENT 5 élèves par classe au lieu de 8-12
                int nombreEleves = 5;
                List<Eleve> eleves = new ArrayList<>();
                List<Inscription> inscriptions = new ArrayList<>();

                for (int i = 0; i < nombreEleves; i++) {
                    // Créer l'élève
                    Eleve eleve = new Eleve();
                    eleve.setNom(LAST_NAMES.get(i % LAST_NAMES.size()));
                    eleve.setPrenom(FIRST_NAMES.get(i % FIRST_NAMES.size()));
                    eleve.setDateNaissance(LocalDate.now().minusYears(12 + i));
                    eleve.setSexe(i % 2 == 0 ? "M" : "F");
                    eleve.setAdresse((10 + i * 5) + " Rue de Sandaga");
                    eleve.setVille("Dakar");
                    eleve.setEmail(eleve.getPrenom().toLowerCase() + "." +
                            eleve.getNom().toLowerCase() + "@eleve.sn");
                    eleve.setClasse(classe);

                    eleves.add(eleve);
                }

                // Sauvegarder tous les élèves de la classe
                List<Eleve> savedEleves = eleveRepository.saveAll(eleves);

                // Créer les inscriptions pour chaque élève
                for (Eleve eleve : savedEleves) {
                    Inscription inscription = new Inscription();
                    inscription.setEleve(eleve);
                    inscription.setAnneeScolaire(anneeScolaire);
                    inscription.setClasse(classe);
                    inscription.setDateInscription(LocalDate.now().minusDays(30));
                    inscription.setEstActive(true);

                    inscriptions.add(inscription);
                }

                // Sauvegarder toutes les inscriptions
                inscriptionRepository.saveAll(inscriptions);

                // Mettre à jour les effectifs de la classe
                classe.setEffectifs(eleves.size());
                classeRepository.save(classe);

                totalEleves += eleves.size();
                System.out.println("   ✓ " + classe.getNomClasse() + ": " + eleves.size() +
                        " élèves avec inscriptions");

            } catch (Exception e) {
                System.err.println("   ❌ Erreur création élèves pour " + classe.getNomClasse() +
                        ": " + e.getMessage());
            }
        }

        System.out.println("   ✅ Total élèves créés: " + totalEleves);
    }

    private List<Cours> createOptimizedCourses(List<Professeur> professeurs, List<Classe> classes,
            List<Module> modules) {
        System.out.println("📖 Création des cours (OPTIMISÉ - 3 jours seulement)...");

        if (professeurs.isEmpty() || classes.isEmpty() || modules.isEmpty()) {
            System.err.println("❌ Impossible de créer les cours: données manquantes");
            return new ArrayList<>();
        }

        List<Cours> coursList = new ArrayList<>();
        Random random = new Random();

        // Créneaux horaires réduits
        List<LocalTime> timeSlots = Arrays.asList(
                LocalTime.of(8, 0),
                LocalTime.of(10, 0),
                LocalTime.of(14, 0));

        // SEULEMENT 3 JOURS : Lundi, Mardi, Mercredi
        LocalDate startDate = LocalDate.now().with(DayOfWeek.THURSDAY);
        List<LocalDate> joursOuvrables = Arrays.asList(
                startDate, // Lundi
                startDate.plusDays(1), // Mardi
                startDate.plusDays(2) // Mercredi
        );

        System.out.println("   📅 Génération sur 3 jours: " + joursOuvrables);

        // Garantir exactement 2 cours par jour pour chaque classe
        for (Classe classe : classes) {
            for (LocalDate jour : joursOuvrables) {
                // 2 cours par jour par classe
                for (int coursIndex = 0; coursIndex < 2; coursIndex++) {
                    try {
                        CombinaisonCours combinaison = trouverCombinaisonPourClasse(classe, professeurs, modules);

                        if (combinaison != null) {
                            Cours cours = creerCoursOptimise(combinaison, jour, timeSlots.get(coursIndex), random);
                            if (cours != null) {
                                coursList.add(cours);
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("     ❌ Erreur création cours: " + e.getMessage());
                    }
                }
            }
        }

        System.out.println("   🎯 TOTAL: " + coursList.size() + " cours générés sur 3 jours");
        return coursList;
    }

    private CombinaisonCours trouverCombinaisonPourClasse(Classe classe, List<Professeur> professeurs,
            List<Module> modules) {
        Random random = new Random();

        List<ProfesseurClasse> profClasses = professeurClasseRepository.findByClasse(classe);

        if (profClasses.isEmpty()) {
            return null;
        }

        ProfesseurClasse profClasseChoice = profClasses.get(random.nextInt(profClasses.size()));
        Professeur professeur = profClasseChoice.getProfesseur();

        List<ProfesseurModule> profModules = professeurModuleRepository.findByProfesseur(professeur);

        if (profModules.isEmpty()) {
            return null;
        }

        ProfesseurModule profModuleChoice = profModules.get(random.nextInt(profModules.size()));
        Module module = profModuleChoice.getModule();

        return new CombinaisonCours(professeur, module, classe);
    }

    private Cours creerCoursOptimise(CombinaisonCours combinaison, LocalDate jour, LocalTime heureDebut,
            Random random) {
        try {
            Cours cours = new Cours();
            cours.setProfesseur(combinaison.professeur);
            cours.setModule(combinaison.module);
            cours.setClasse(combinaison.classe);

            ZoneId zoneId = ZoneId.systemDefault();
            Date convertedDate = Date.from(jour.atStartOfDay(zoneId).toInstant());
            cours.setDate(convertedDate);

            Date heureDebutDate = Date.from(heureDebut.atDate(jour).atZone(zoneId).toInstant());
            cours.setHeureDebut(heureDebutDate);

            LocalDateTime heureDebutLdt = heureDebutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            cours.setHeureFin(
                    Date.from(heureDebutLdt.plusHours(1).plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant()));
            cours.setNbrHeures(2);

            return coursRepository.save(cours);

        } catch (Exception e) {
            System.err.println("     ❌ Erreur création cours: " + e.getMessage());
            return null;
        }
    }

    private static class CombinaisonCours {
        final Professeur professeur;
        final Module module;
        final Classe classe;

        public CombinaisonCours(Professeur professeur, Module module, Classe classe) {
            this.professeur = professeur;
            this.module = module;
            this.classe = classe;
        }
    }

    private void generateOptimizedAbsences(List<Cours> coursList) {
        System.out.println("📋 Génération des absences (OPTIMISÉ)...");
        Random random = new Random();
        List<Abscence> absencesList = new ArrayList<>();
        List<Justification> justificatifs = new ArrayList<>();

        int totalAbsencesGenerees = 0;

        for (Cours cours : coursList) {
            try {
                if (cours.getClasse() == null) {
                    continue;
                }

                List<Eleve> eleves = eleveRepository.findByClasse(cours.getClasse());

                if (eleves.isEmpty()) {
                    continue;
                }

                for (Eleve eleve : eleves) {
                    try {
                        // SEULEMENT 5% de chance d'être absent au lieu de 10%
                        if (random.nextDouble() < 0.05) {
                            Abscence absence = new Abscence();
                            absence.setEleve(eleve);
                            absence.setCours(cours);
                            absence.setTypeAbscence(random.nextBoolean() ? TypeAbscence.Absent : TypeAbscence.Retard);
                            absence.setStatutAbscence(StatutAbscence.NON_JUSTIFIER);

                            absencesList.add(absence);
                            totalAbsencesGenerees++;

                            // 20% de chance d'être justifié
                            if (random.nextDouble() < 0.20) {
                                absence.setStatutAbscence(StatutAbscence.JUSTIFIER);

                                Justification justificatif = new Justification();
                                justificatif.setAbscence(absence);
                                justificatif.setStatutJustification(getRandomStatutJustification(random));
                                justificatif.setJustificatif(getRandomJustificatif(random));

                                justificatifs.add(justificatif);
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("     ❌ Erreur génération absence: " + e.getMessage());
                    }
                }
            } catch (Exception e) {
                System.err.println("   ❌ Erreur génération absences pour cours: " + e.getMessage());
            }
        }

        // Sauvegarder les absences et justificatifs
        try {
            if (!absencesList.isEmpty()) {
                absenceRepository.saveAll(absencesList);
                System.out.println("   ✅ " + absencesList.size() + " absences sauvegardées");

                if (!justificatifs.isEmpty()) {
                    justificatifRepository.saveAll(justificatifs);
                    System.out.println("   ✅ " + justificatifs.size() + " justificatifs sauvegardés");
                }
            }
        } catch (Exception e) {
            System.err.println("   ❌ ERREUR lors de la sauvegarde: " + e.getMessage());
            throw new RuntimeException("Échec de la sauvegarde des absences", e);
        }

        System.out.println("   📊 Résumé: " + totalAbsencesGenerees + " absences générées");
    }

    private StatutJustification getRandomStatutJustification(Random random) {
        StatutJustification[] statuts = StatutJustification.values();
        return statuts[random.nextInt(statuts.length)];
    }

    private String getRandomJustificatif(Random random) {
        List<String> justificatifs = Arrays.asList(
                "Rendez-vous médical",
                "Maladie",
                "Problème de transport");
        return justificatifs.get(random.nextInt(justificatifs.size()));
    }

    private void printFinalResults() {
        System.out.println("\n=== 📊 RÉSULTATS FINAUX OPTIMISÉS ===");
        try {
            System.out.println("📅 Années scolaires     : " + anneeScolaireRepository.count());
            System.out.println("👨‍🏫 Professeurs créés    : " + professeurRepository.count());
            System.out.println("🏫 Niveaux créés        : " + niveauRepository.count());
            System.out.println("📚 Classes créées       : " + classeRepository.count());
            System.out.println("👨‍🎓 Élèves créés         : " + eleveRepository.count());
            System.out.println("📝 Inscriptions créées  : " + inscriptionRepository.count());
            System.out.println("📖 Cours créés          : " + coursRepository.count());
            System.out.println("❌ Absences créées      : " + absenceRepository.count());
            System.out.println("=== ✅ GÉNÉRATION OPTIMISÉE TERMINÉE ===");
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'affichage des résultats: " + e.getMessage());
        }
    }
}