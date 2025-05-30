package com.gesabsences.gesabsences.Mobile.Dto.Mapper;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.gesabsences.gesabsences.Mobile.Dto.Request.PointageRequest;
import com.gesabsences.gesabsences.Mobile.Dto.Response.EleveAvecCoursResponse;
import com.gesabsences.gesabsences.Mobile.Dto.Response.PointageValideResponse;
import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Entities.Pointage;
import com.gesabsences.gesabsences.data.Entities.Professeur;
import com.gesabsences.gesabsences.data.Enum.TypeAbscence;

@Mapper(componentModel = "spring")
public interface MobPointageMapper {

    @Mapping(source = "id", target = "eleveId")
    @Mapping(source = "classe.nomClasse", target = "nomClasse")
    @Mapping(target = "coursJour", ignore = true) // On l'assigne manuellement
    @Mapping(target = "prochainCours", ignore = true) // On l'assigne manuellement
    @Mapping(target = "ACoursEnCours", ignore = true)
    EleveAvecCoursResponse eleveToElevePointageDTO(Eleve eleve);

    @Mapping(source = "eleve.id", target = "eleveId")
    @Mapping(source = "eleve", target = "nomComplet", qualifiedByName = "eleveToNomComplet")
    @Mapping(source = "cours.id", target = "coursId")
    @Mapping(source = "cours.module.nom", target = "module")
    @Mapping(source = "cours.heureDebut", target = "heureCoursDebut")
    @Mapping(source = "statut", target = "statut", qualifiedByName = "statutToString")
    PointageValideResponse toPointageValidationDTO(Eleve eleve, Cours cours, Date heureArrivee,
            TypeAbscence statut);

    @Mapping(source = "heurePointage", target = "heurePointage", qualifiedByName = "stringToDate")
    @Mapping(source = "idEleve", target = "etudiant.id")
    Pointage toEntity(PointageRequest pointageRequest);

    @Named("stringToDate")
    static Date mapHeure(String heurePointage) {
        return Date.from(OffsetDateTime.parse(heurePointage).toInstant());
    }

    @Named("professeurToString")
    default String professeurToString(Professeur professeur) {
        if (professeur != null) {
            return professeur.getPrenom() + " " + professeur.getNom();
        }
        return "Non assigné";
    }

    @Named("eleveToNomComplet")
    default String eleveToNomComplet(Eleve eleve) {
        return eleve.getPrenom() + " " + eleve.getNom();
    }

    @Named("statutToString")
    default String statutToString(TypeAbscence statut) {
        return statut != null ? statut.name() : "INCONNU";
    }

}
