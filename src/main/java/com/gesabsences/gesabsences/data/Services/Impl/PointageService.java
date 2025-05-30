package com.gesabsences.gesabsences.data.Services.Impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Entities.Pointage;
import com.gesabsences.gesabsences.data.Enum.TypeAbscence;
import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.Mobile.Dto.Request.PointageRequest;
import com.gesabsences.gesabsences.Mobile.Dto.Mapper.MobCoursMapper;
import com.gesabsences.gesabsences.Mobile.Dto.Mapper.MobPointageMapper;
import com.gesabsences.gesabsences.Mobile.Dto.Response.EleveAvecCoursResponse;
import com.gesabsences.gesabsences.Mobile.Dto.Response.PointageValideResponse;
import com.gesabsences.gesabsences.data.Entities.Classe;
import com.gesabsences.gesabsences.data.Repositories.EleveRepository;
import com.gesabsences.gesabsences.data.Repositories.PointageRepository;
import com.mongodb.client.model.geojson.Point;
import com.gesabsences.gesabsences.data.Repositories.CoursRepository;

@Service
public class PointageService {

    @Autowired
    private EleveRepository eleveRepository;

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private MobPointageMapper pointageMapper;

    @Autowired
    private MobCoursMapper coursMapper;

    @Autowired
    private PointageRepository pointageRepository;

    public List<Pointage> findAll() {
        return pointageRepository.findAll();
    }

    /**
     * Récupère tous les élèves qui ont cours aujourd'hui
     */
    public List<EleveAvecCoursResponse> getElevesAvecCoursAujourdhui() {
        return getElevesAvecCoursParDate(LocalDate.now());
    }

    public void save(PointageRequest pointageRequest) {
        pointageRepository.save(pointageMapper.toEntity(pointageRequest));
    }

    public void save(Pointage pointage) {
        pointageRepository.save(pointage);
    }

    /**
     * Récupère tous les élèves qui ont cours à une date donnée
     */
    public List<EleveAvecCoursResponse> getElevesAvecCoursParDate(LocalDate date) {
        ZoneId localZone = ZoneId.of("Africa/Dakar");

        ZonedDateTime startOfDayLocal = date.atStartOfDay(localZone);
        ZonedDateTime endOfDayLocal = date.plusDays(1).atStartOfDay(localZone);

        Date startOfDay = Date.from(startOfDayLocal.withZoneSameInstant(ZoneOffset.UTC).toInstant());
        Date endOfDay = Date.from(endOfDayLocal.withZoneSameInstant(ZoneOffset.UTC).toInstant());

        System.out.println("Date recherchée: " + date);
        System.out.println("Début du jour (UTC): " + startOfDay);
        System.out.println("Fin du jour (UTC): " + endOfDay);

        List<Cours> coursJour = coursRepository.findByDateBetween(startOfDay, endOfDay);
        System.out.println("Nombre de cours trouvés: " + coursJour.size());

        if (coursJour.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> classeIds = coursJour.stream()
                .map(cours -> cours.getClasse().getId())
                .distinct()
                .collect(Collectors.toList());

        System.out.println("Classes avec cours: " + classeIds);

        List<ObjectId> classeObjectIds = classeIds.stream()
                .map(ObjectId::new)
                .collect(Collectors.toList());

        List<Eleve> eleves = eleveRepository.findElevesByClasseIds(classeObjectIds);
        System.out.println("Nombre d'élèves trouvés: " + eleves.size());

        ZonedDateTime maintenantZoned = ZonedDateTime.now(localZone);
        Date maintenant = Date.from(maintenantZoned.toInstant());

        return eleves.stream()
                .map(eleve -> {
                    EleveAvecCoursResponse dto = pointageMapper.eleveToElevePointageDTO(eleve);

                    List<Cours> coursEleve = coursJour.stream()
                            .filter(cours -> cours.getClasse().getId().equals(eleve.getClasse().getId()))
                            .sorted(Comparator.comparing(Cours::getHeureDebut))
                            .collect(Collectors.toList());

                    dto.setCoursJour(coursMapper.coursListToCoursDTO(coursEleve));

                    Optional<Cours> prochainCours = coursEleve.stream()
                            .filter(c -> c.getHeureDebut().after(maintenant))
                            .findFirst();

                    prochainCours.ifPresent(c -> dto.setProchainCours(coursMapper.toDto(c)));

                    boolean coursEnCours = coursEleve.stream()
                            .anyMatch(c -> !maintenant.before(c.getHeureDebut()) &&
                                    !maintenant.after(c.getHeureFin()));
                    dto.setACoursEnCours(coursEnCours);

                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Trouve le cours le plus proche de l'heure d'arrivée pour un élève
     */
    private LocalTime toLocalTime(ZonedDateTime zdt, ZoneId zone) {
        return zdt.withZoneSameInstant(zone).toLocalTime();
    }

    public Optional<Cours> getCoursLePlusProche(String eleveId, Date heureArrivee) {
        System.out.println("---- getCoursLePlusProche appelé ----");
        System.out.println("Élève ID reçu : " + eleveId);
        System.out.println("Heure d'arrivée reçue (UTC) : " + heureArrivee);

        ZoneId localZone = ZoneId.of("Africa/Dakar");
        LocalDate aujourdhui = LocalDate.now(localZone);
        System.out.println("Date locale aujourd'hui (Africa/Dakar) : " + aujourdhui);

        // Définir la journée en UTC
        Date startOfDay = Date.from(aujourdhui.atStartOfDay(localZone).withZoneSameInstant(ZoneOffset.UTC).toInstant());
        Date endOfDay = Date
                .from(aujourdhui.plusDays(1).atStartOfDay(localZone).withZoneSameInstant(ZoneOffset.UTC).toInstant());

        System.out.println("Début de journée UTC : " + startOfDay);
        System.out.println("Fin de journée UTC : " + endOfDay);

        // Récupérer les cours du jour
        List<Cours> coursJour = coursRepository.findByDateBetween(startOfDay, endOfDay);
        System.out.println("Nombre de cours récupérés pour la journée : " + coursJour.size());

        if (coursJour.isEmpty()) {
            System.out.println("Aucun cours trouvé entre " + startOfDay + " et " + endOfDay);
            return Optional.empty();
        }

        // Récupérer l'élève et sa classe
        Optional<Eleve> eleveOpt = eleveRepository.findById(eleveId);
        if (eleveOpt.isEmpty()) {
            System.out.println("Aucun élève trouvé avec l'ID : " + eleveId);
            return Optional.empty();
        }
        String classeId = eleveOpt.get().getClasse().getId();
        System.out.println("Classe ID de l'élève : " + classeId);

        // Filtrer les cours de la classe de l'élève
        List<Cours> coursClasse = coursJour.stream()
                .filter(cours -> cours.getClasse().getId().equals(classeId))
                .collect(Collectors.toList());

        System.out.println("Nombre de cours trouvés pour la classe de l'élève : " + coursClasse.size());
        if (coursClasse.isEmpty()) {
            System.out.println("Aucun cours trouvé pour la classe " + classeId);
            return Optional.empty();
        }

        // Maintenant filtrer par heure
        Optional<Cours> coursLePlusProche = coursClasse.stream()
                .filter(cours -> {
                    System.out.println("Cours ID: " + cours.getId());
                    System.out.println(" - Heure début : " + cours.getHeureDebut());
                    System.out.println(" - Heure fin : " + cours.getHeureFin());
                    System.out.println(" - Heure arrivée reçue : " + heureArrivee);
                    System.out.println(" - début.after(arrivée)? " + cours.getHeureDebut().after(heureArrivee));
                    System.out.println(" - fin.after(arrivée)? " + cours.getHeureFin().after(heureArrivee));
                    Date debut = cours.getHeureDebut();
                    Date fin = cours.getHeureFin();
                    return debut.after(heureArrivee) || fin.after(heureArrivee);
                })
                .min(Comparator
                        .comparingLong(cours -> Math.abs(cours.getHeureDebut().getTime() - heureArrivee.getTime())));

        if (coursLePlusProche.isPresent()) {
            System.out.println("Cours le plus proche trouvé : " + coursLePlusProche.get().getId());
        } else {
            System.out.println("Aucun cours ne correspond à l'heure d'arrivée.");
        }

        return coursLePlusProche;
    }

    /**
     * Valide la présence d'un élève et retourne un DTO avec MapStruct
     */
    public Optional<PointageValideResponse> validerPresenceEleve(String eleveId, Date heureArrivee) {
        Optional<Eleve> eleveOpt = eleveRepository.findById(eleveId);
        if (!eleveOpt.isPresent()) {
            return Optional.empty();
        }

        Eleve eleve = eleveOpt.get();
        Optional<Cours> coursOpt = getCoursLePlusProche(eleveId, heureArrivee);

        if (coursOpt.isPresent()) {
            Cours cours = coursOpt.get();
            TypeAbscence statut = determinerStatutPresence(cours, heureArrivee);

            // Utiliser MapStruct pour créer le DTO
            PointageValideResponse dto = pointageMapper.toPointageValidationDTO(eleve, cours, heureArrivee, statut);

            // sauvegarder le pointage

            // Ajouter un message personnalisé
            String message = genererMessageStatut(statut, heureArrivee, cours.getHeureDebut());
            dto.setMessage(message);

            return Optional.of(dto);
        }

        return Optional.empty();
    }

    /**
     * Génère un message selon le statut
     */
    private String genererMessageStatut(TypeAbscence statut, Date heureArrivee, Date heureDebut) {
        switch (statut) {
            case PRESENT:
                return "Élève présent à l'heure";
            case Retard:
                Instant instantDebut = heureDebut.toInstant();
                Instant instantArrivee = heureArrivee.toInstant();
                long minutesRetard = java.time.Duration.between(instantDebut, instantArrivee).toMinutes();
                return "Élève en retard de " + minutesRetard + " minute(s)";
            case Absent:
                return "Élève marqué absent (retard trop important)";
            default:
                return "Statut inconnu";
        }
    }

    /**
     * Détermine le statut de présence selon l'heure d'arrivée
     */
    public TypeAbscence determinerStatutPresence(Cours cours, Date heureArrivee) {
        LocalDateTime heureArriveeLdt = heureArrivee.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime heureDebutLdt = cours.getHeureDebut().toInstant().atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        if (heureArriveeLdt.isBefore(heureDebutLdt) || heureArriveeLdt.equals(heureDebutLdt)) {
            return TypeAbscence.PRESENT;
        } else if (heureArriveeLdt.isBefore(heureDebutLdt.plusMinutes(15))) { // Tolérance de 15 minutes
            return TypeAbscence.Retard;
        } else {
            return TypeAbscence.Absent;
        }
    }

}