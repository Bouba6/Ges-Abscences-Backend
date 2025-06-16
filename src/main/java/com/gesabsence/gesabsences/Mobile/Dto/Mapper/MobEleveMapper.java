package com.gesabsences.gesabsences.Mobile.Dto.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.Mobile.Dto.Response.EleveResponse;

@Mapper(componentModel = "spring")
public interface MobEleveMapper {

    @Mapping(target = "nomClasse", source = "classe.nomClasse")
    EleveResponse toDto(Eleve eleve);
}
