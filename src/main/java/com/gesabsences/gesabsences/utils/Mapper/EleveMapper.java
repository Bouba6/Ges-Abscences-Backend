package com.gesabsences.gesabsences.utils.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.Web.Dto.Response.EleveResponse;

@Mapper(componentModel = "spring")
public interface EleveMapper {

    @Mapping(target = "nomClasse", source = "classe.nomClasse")
    EleveResponse toDto(Eleve eleve);

    // @Mapping(target = "eleve.nomClasse", source = "classe.nomClasse")
    // @Mapping(target = "eleve.adresse", source = "adresse")
    // @Mapping(target = "eleve.dateNaissance", source = "dateNaissance")
    // @Mapping(target = "eleve.email", source = "email")
    // @Mapping(target = "eleve.nom", source = "nom")
    // @Mapping(target = "eleve.prenom", source = "prenom")
    // @Mapping(target = "eleve.sexe", source = "sexe")
    // @Mapping(target = "eleve.ville", source = "ville")
    // EleveResponseBulletin toDtoBulletin(Eleve eleve);

    // NoteResponse toDto(Note note);
}
