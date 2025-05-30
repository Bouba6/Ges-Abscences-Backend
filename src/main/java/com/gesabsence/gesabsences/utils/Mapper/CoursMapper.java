package com.gesabsences.gesabsences.utils.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.Web.Dto.Response.CoursResponse;

@Mapper(componentModel = "spring") // Utilsable avec Spring
public interface CoursMapper {

    @Mapping(target = "nomCours", source = "cours.module.nom")
    @Mapping(target = "idClasse", source = "cours.classe.id")
    @Mapping(target = "nomProfesseur", source = "cours.professeur.nom")
    @Mapping(target = "nomClasse", source = "cours.classe.nomClasse")
    @Mapping(target = "nomModule", source = "cours.module.nom")
    CoursResponse toDto(Cours cours);
}
