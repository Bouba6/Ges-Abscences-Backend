package com.gesabsences.gesabsences.data.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gesabsences.gesabsences.data.Entities.Professeur;
import com.gesabsences.gesabsences.data.Entities.ProfesseurClasse;
import com.gesabsences.gesabsences.data.Entities.ProfesseurModule;

public interface ProfesseurModuleRepository extends MongoRepository<ProfesseurModule, String> {

    List<ProfesseurModule> findByProfesseur(Professeur professeur);

}
