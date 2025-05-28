package com.gesabsences.gesabsences.Web.Mapper;

import com.gesabsences.gesabsences.data.Entities.Abscence;
import com.gesabsences.gesabsences.Web.Dto.Request.AbscenceRequest;
import com.gesabsences.gesabsences.Web.Dto.Response.AbsenceResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Primary;

@Mapper(componentModel = "spring")
@Primary
public interface AbscenceMapper {

    @Mapping(target = "eleve", ignore = true) // Éviter de créer un objet Eleve
    @Mapping(target = "cours", ignore = true) // Éviter de créer un objet Cours
    Abscence toEntity(AbscenceRequest abscenceResponse);

    AbsenceResponse toDto(Abscence abscence);

}
