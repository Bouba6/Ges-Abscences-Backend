package com.gesabsences.gesabsences.Mobile.Dto.Mapper;

import com.gesabsences.gesabsences.data.Entities.Abscence;
import com.gesabsences.gesabsences.Mobile.Dto.Request.AbscenceRequest;
import com.gesabsences.gesabsences.Mobile.Dto.Response.AbsenceResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MobAbscenceMapper {

    @Mapping(target = "eleve", ignore = true) // Éviter de créer un objet Eleve
    @Mapping(target = "cours", ignore = true) // Éviter de créer un objet Cours
    Abscence toEntity(AbscenceRequest abscenceResponse);

    AbsenceResponse toDto(Abscence abscence);

}
