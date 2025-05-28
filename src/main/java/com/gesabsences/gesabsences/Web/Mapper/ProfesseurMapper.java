package com.gesabsences.gesabsences.Web.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Primary;

import com.gesabsences.gesabsences.data.Entities.Classe;
import com.gesabsences.gesabsences.data.Entities.Professeur;
import com.gesabsences.gesabsences.data.Entities.ProfesseurClasse;
import com.gesabsences.gesabsences.data.Entities.ProfesseurModule;
import com.gesabsences.gesabsences.Web.Dto.Response.ClasseResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.ModuleResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.ProfesseurResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.ProfesseurResponseClasse;

@Mapper(componentModel = "spring")
@Primary
public interface ProfesseurMapper {
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
