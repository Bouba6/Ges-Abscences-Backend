package com.gesabsences.gesabsences.Mobile.Dto.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gesabsences.gesabsences.data.Entities.Classe;
import com.gesabsences.gesabsences.data.Entities.Niveau;
import com.gesabsences.gesabsences.data.Entities.Professeur;
import com.gesabsences.gesabsences.Mobile.Dto.Response.ClasseResponse;
import com.gesabsences.gesabsences.Mobile.Dto.Response.NiveauResponse;

@Mapper(componentModel = "spring") // Utilsable avec Spring
public interface MobNiveauMapper {

    @Mapping(target = "nomNiveau", source = "niveauState")
    NiveauResponse toDto(Niveau niveau);

    @Mapping(target = "nomClasse", source = "nomClasse")
    @Mapping(target = "profPrincipal", source = "classe.profPrincipal.nom")
    ClasseResponse toDto(Classe classe);

    default String map(Professeur professeur) {
        return professeur.getNom();
    }

}
