package com.gesabsences.gesabsences.Mobile.Dto.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.gesabsences.gesabsences.Mobile.Dto.Request.JustifierRequest;
import com.gesabsences.gesabsences.Mobile.Dto.Response.justificatifResponse;
import com.gesabsences.gesabsences.data.Entities.Abscence;
import com.gesabsences.gesabsences.data.Entities.Justification;
import com.gesabsences.gesabsences.data.Enum.StatutJustification;

@Mapper(componentModel = "spring")
public interface MobJusticatifMapper {

    @Mapping(target = "nomEleve", source = "justificatif.abscence.eleve.nom")
    @Mapping(target = "nomCours", source = "justificatif.abscence.cours.module.nom")
    @Mapping(target = "statutAbscence", source = "justificatif.abscence.statutAbscence")
    @Mapping(target = "statutJustification", source = "justificatif.statutJustification")
    @Mapping(target = "typeAbscence", source = "justificatif.abscence.typeAbscence")
    justificatifResponse toDto(Justification justificatif);

    @Mapping(target = "statutJustification", source = "statutJustification")
    @Mapping(target = "abscence.id", source = "abscenceId")
    Justification toEntity(JustifierRequest justificatifResponse);

     List<justificatifResponse> toDto(List<Justification> justificatifs);

    // private String justificatif;

    // @DBRef
    // private Abscence abscence;

    // private StatutJustification statutJustification;

}
