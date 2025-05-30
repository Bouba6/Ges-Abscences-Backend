package com.gesabsences.gesabsences.data.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gesabsences.gesabsences.data.Entities.Professeur;

public interface ProfesseurRepository extends MongoRepository<Professeur, String> {

}
