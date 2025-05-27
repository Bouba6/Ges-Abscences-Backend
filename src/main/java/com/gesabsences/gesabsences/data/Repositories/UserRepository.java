package com.gesabsences.gesabsences.data.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gesabsences.gesabsences.data.Entities.User;

public interface UserRepository extends MongoRepository<User, String> {
    
}
