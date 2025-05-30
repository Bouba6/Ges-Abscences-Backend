package com.gesabsences.gesabsences.data.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gesabsences.gesabsences.data.Entities.Professeur;
import com.gesabsences.gesabsences.data.Entities.ProfesseurClasse;

public interface ProfesseurClasseRepository extends MongoRepository<ProfesseurClasse, String> {

    List<ProfesseurClasse> findByProfesseur(Professeur professeur);
}
