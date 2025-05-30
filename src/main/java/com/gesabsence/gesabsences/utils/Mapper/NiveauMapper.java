package com.gesabsences.gesabsences.utils.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gesabsences.gesabsences.data.Entities.Classe;
import com.gesabsences.gesabsences.data.Entities.Niveau;
import com.gesabsences.gesabsences.Web.Dto.Response.ClasseResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.NiveauResponse;

@Mapper(componentModel = "spring") // Utilsable avec Spring
public interface NiveauMapper {

    @Mapping(target = "nomNiveau", source = "niveauState")
    NiveauResponse toDto(Niveau niveau);

    @Mapping(target = "nomClasse", source = "nomClasse")
    @Mapping(target = "profPrincipal", source = "classe.profPrincipal.nom")
    ClasseResponse toDto(Classe classe);

}
