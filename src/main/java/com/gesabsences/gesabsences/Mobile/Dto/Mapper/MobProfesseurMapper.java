package com.gesabsences.gesabsences.Mobile.Dto.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gesabsences.gesabsences.data.Entities.Classe;
import com.gesabsences.gesabsences.data.Entities.Professeur;
import com.gesabsences.gesabsences.data.Entities.ProfesseurClasse;
import com.gesabsences.gesabsences.data.Entities.ProfesseurModule;
import com.gesabsences.gesabsences.Mobile.Dto.Response.ClasseResponse;
import com.gesabsences.gesabsences.Mobile.Dto.Response.ModuleResponse;
import com.gesabsences.gesabsences.Mobile.Dto.Response.ProfesseurResponse;
import com.gesabsences.gesabsences.Mobile.Dto.Response.ProfesseurResponseClasse;

@Mapper(componentModel = "spring")
public interface MobProfesseurMapper {
    @Mapping(target = "professeurModules", source = "professeurModules")
    ProfesseurResponse toDto(Professeur professeur);

    @Mapping(target = "nom", source = "module.nom")
    @Mapping(target = "coef", source = "module.coef")
    ModuleResponse toDto(ProfesseurModule professeurModule);

    // @Mapping(target = "nomProf", source = "professeur.nom")
    @Mapping(target = "classes", source = "classe")
    ProfesseurResponseClasse toDto(ProfesseurClasse professeurClasse);

    @Mapping(target = "profPrincipal", source = "classe.profPrincipal.nom")
    ClasseResponse toDto(Classe classe);

}
