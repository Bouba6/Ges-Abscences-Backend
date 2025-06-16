package com.gesabsences.gesabsences.data.Repositories;

import com.gesabsences.gesabsences.data.Entities.Abscence;
import com.gesabsences.gesabsences.data.Entities.AnneeScolaire;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnneeRepository extends MongoRepository<AnneeScolaire, String> {

}
