package com.gesabsences.gesabsences.utils.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gesabsences.gesabsences.Web.Dto.Response.justificatifResponse;
import com.gesabsences.gesabsences.data.Entities.Justitfication;

@Mapper(componentModel = "spring")
public interface JusticatifMapper {

    @Mapping(target = "nomEleve", source = "justificatif.abscence.eleve.nom")
    @Mapping(target = "nomCours", source = "justificatif.abscence.cours.module.nom")
    @Mapping(target = "statutAbscence", source = "justificatif.abscence.statutAbscence")
    @Mapping(target = "statutJustification", source = "justificatif.statutJustification")
    @Mapping(target = "typeAbscence", source = "justificatif.abscence.typeAbscence")
    justificatifResponse toDto(Justitfication justificatif);
}
