package com.gesabsences.gesabsences.utils.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.Web.Dto.Response.EleveResponse;

@Mapper(componentModel = "spring")
public interface EleveMapper {

    @Mapping(target = "nomClasse", source = "classe.nomClasse")
    EleveResponse toDto(Eleve eleve);
}
