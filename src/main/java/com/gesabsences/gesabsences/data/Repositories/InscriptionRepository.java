package com.gesabsences.gesabsences.data.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gesabsences.gesabsences.data.Entities.Inscription;

public interface InscriptionRepository extends MongoRepository<Inscription,String> {
    
    
}
