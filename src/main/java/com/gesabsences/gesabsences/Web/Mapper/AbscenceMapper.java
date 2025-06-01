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
    @Mapping(target = "id", source = "motif")
    @Mapping(target = "justificatif", source = "justifiee")
    //    @Mapping(target = "statutAbscence", source = "statutAbscence")
    // Removed invalid mapping for typeAbscence
    Abscence toEntity(AbscenceRequest abscenceResponse);

    static AbsenceResponse toDto(Abscence abscence) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toDto'");
    }

    // Custom mapping method for Boolean to Justitfication
    default com.gesabsences.gesabsences.data.Entities.Justitfication map(Boolean justifiee) {
        if (justifiee == null) return null;
        com.gesabsences.gesabsences.data.Entities.Justitfication justificatif = new com.gesabsences.gesabsences.data.Entities.Justitfication();
        justificatif.setJustifiee(justifiee);
        return justificatif;
    }
}
