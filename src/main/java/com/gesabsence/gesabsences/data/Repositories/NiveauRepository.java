package com.gesabsences.gesabsences.data.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gesabsences.gesabsences.data.Entities.Niveau;

public interface NiveauRepository extends MongoRepository<Niveau, String> {
    
}
