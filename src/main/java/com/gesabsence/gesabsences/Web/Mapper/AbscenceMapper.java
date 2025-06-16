package com.gesabsences.gesabsences.Web.Mapper;

import com.gesabsences.gesabsences.data.Entities.Abscence;
import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Entities.Justification;
import com.gesabsences.gesabsences.data.Enum.StatutAbscence;
import com.gesabsences.gesabsences.data.Enum.TypeAbscence;
import com.gesabsences.gesabsences.Web.Dto.Request.AbscenceRequest;
import com.gesabsences.gesabsences.Web.Dto.Response.AbsenceResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Mapper(componentModel = "spring")
@Primary
public interface AbscenceMapper {

    @Mapping(target = "eleve", ignore = true) // Éviter de créer un objet Eleve
    @Mapping(target = "cours", ignore = true) // Éviter de créer un objet Cours
    Abscence toEntity(AbscenceRequest abscenceResponse);


    @Mapping(target = "id", source="id")
    @Mapping(target="idJustification",source="justificatif.id")
    @Mapping(target="nomCours",source="cours.module.nom")
    @Mapping(target="typeAbscence",source="typeAbscence")
    @Mapping(target="statutAbscence",source="statutAbscence")
    @Mapping(target = "statutJustificatif", source = "justificatif.statutJustification")
    @Mapping(target = "nomEleve", source = "eleve.nom")
    AbsenceResponse toDto(Abscence abscence);

}

//   @DBRef
//     private Justification justificatif;
//     private TypeAbscence typeAbscence;
//     @DBRef
//     private Eleve eleve;
//     @DBRef
//     private Cours cours;
//     private StatutAbscence statutAbscence;

//     private String id;
//     private String idJustification;
//     private String nomCours;
//     private String typeAbscence;
//     private String statutAbscence;