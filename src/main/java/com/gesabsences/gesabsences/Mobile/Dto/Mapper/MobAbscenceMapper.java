package com.gesabsences.gesabsences.Mobile.Dto.Mapper;

import com.gesabsences.gesabsences.data.Entities.Abscence;
import com.gesabsences.gesabsences.Mobile.Dto.Request.AbscenceRequest;
import com.gesabsences.gesabsences.Mobile.Dto.Response.AbsenceResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MobAbscenceMapper {

    // @Mapping(target = "eleve", ignore = true) // Éviter de créer un objet Eleve
    // @Mapping(target = "cours", ignore = true) // Éviter de créer un objet Cours

    // @Mapping(target = "eleve", ignore = true) // Éviter de créer un objet Eleve
    // @Mapping(target = "cours", ignore = true) // Éviter de créer un objet Cours

    @Mapping(target = "statutAbscence", source = "statutAbscence")
    @Mapping(target = "typeAbscence", source = "typeAbscence")
    // @Mapping(target = "justificatif.id", source = "justificatifId")
    @Mapping(target = "eleve.id", source = "eleveId")
    @Mapping(target = "cours.id", source = "coursId")
    Abscence toEntity(AbscenceRequest abscenceResponse);

    @Mapping(target = "eleveId", source = "eleve.id")
    @Mapping(target = "coursId", source = "cours.id")
    @Mapping(target = "statutAbscence", source = "statutAbscence")
    @Mapping(target = "statutjustificatif", source = "justificatif.statutJustification")
    AbsenceResponse toDto(Abscence abscence);

}
