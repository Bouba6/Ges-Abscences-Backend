package com.gesabsences.gesabsences.Web.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Primary;

import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.Web.Dto.Response.EleveResponse;

@Mapper(componentModel = "spring")
@Primary
public interface EleveMapper {

    @Mapping(target = "nomClasse", source = "classe.nomClasse")
    EleveResponse toDto(Eleve eleve);
}
