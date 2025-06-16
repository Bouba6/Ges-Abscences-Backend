package com.gesabsences.gesabsences.Mobile.Dto.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.Mobile.Dto.Response.CoursResponse;

@Mapper(componentModel = "spring") // Utilsable avec Spring
public interface MobCoursMapper {

    @Mapping(target = "nomCours", source = "cours.module.nom")
    @Mapping(target = "idClasse", source = "cours.classe.id")
    @Mapping(target = "nomProfesseur", source = "cours.professeur.nom")
    @Mapping(target = "nomClasse", source = "cours.classe.nomClasse")
    @Mapping(target = "nomModule", source = "cours.module.nom")
    CoursResponse toDto(Cours cours);

    List<CoursResponse> coursListToCoursDTO(List<Cours> cours);
}
