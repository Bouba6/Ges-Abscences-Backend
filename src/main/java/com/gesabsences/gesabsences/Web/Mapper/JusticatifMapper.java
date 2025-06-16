package com.gesabsences.gesabsences.Web.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Primary;

import com.gesabsences.gesabsences.Web.Dto.Request.JustificatifRequest;
import com.gesabsences.gesabsences.Web.Dto.Response.justificatifResponse;
import com.gesabsences.gesabsences.data.Entities.Justification;

@Mapper(componentModel = "spring")
@Primary
public interface JusticatifMapper {

    @Mapping(target = "nomEleve", source = "justificatif.abscence.eleve.nom")
    @Mapping(target = "nomCours", source = "justificatif.abscence.cours.module.nom")
    @Mapping(target = "statutAbscence", source = "justificatif.abscence.statutAbscence")
    @Mapping(target = "statutJustification", source = "justificatif.statutJustification")
    @Mapping(target = "typeAbscence", source = "justificatif.abscence.typeAbscence")
    @Mapping(target = "abscenceId", source = "justificatif.abscence.id")
    @Mapping(target = "justificatif", source = "justificatif.justificatif")
    @Mapping(target = "imgUrl", source = "justificatif.imageUrl")
    justificatifResponse toDto(Justification justificatif);

    @Mapping(target = "justificatif", source = "statutJustificatif")
    Justification toEntity(JustificatifRequest justificatifRequest);
}
