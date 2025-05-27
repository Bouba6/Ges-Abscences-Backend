package com.gesabsences.gesabsences.utils.Mapper;

import com.gesabsences.gesabsences.data.Entities.Classe;
import com.gesabsences.gesabsences.Web.Dto.Response.ClasseDetailResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.ClasseResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") // Utilsable avec Spring
public interface ClasseMapper {

    @Mapping(target = "nomClasse", source = "nomClasse")
    @Mapping(target = "profPrincipal", source = "classe.profPrincipal.nom")
    ClasseResponse toDto(Classe classe);

    @Mapping(target = "classe.profPrincipal", source = "classe.profPrincipal.nom")
    @Mapping(target = "classe.nomClasse", source = "nomClasse")
    @Mapping(target = "classe.effectifs", source = "effectifs")
    ClasseDetailResponse toDtoDetail(Classe classe);

}
