package com.gesabsences.gesabsences.Mobile.Dto.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gesabsences.gesabsences.Mobile.Dto.Response.justificatifResponse;
import com.gesabsences.gesabsences.data.Entities.Justitfication;

@Mapper(componentModel = "spring")
public interface MobJusticatifMapper {

    @Mapping(target = "nomEleve", source = "justificatif.abscence.eleve.nom")
    @Mapping(target = "nomCours", source = "justificatif.abscence.cours.module.nom")
    @Mapping(target = "statutAbscence", source = "justificatif.abscence.statutAbscence")
    @Mapping(target = "statutJustification", source = "justificatif.statutJustification")
    @Mapping(target = "typeAbscence", source = "justificatif.abscence.typeAbscence")
    justificatifResponse toDto(Justitfication justificatif);
}
