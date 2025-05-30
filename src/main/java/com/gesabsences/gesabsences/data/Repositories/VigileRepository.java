package com.gesabsences.gesabsences.data.Repositories;

import com.gesabsences.gesabsences.data.Entities.User;
import com.gesabsences.gesabsences.data.Entities.Vigile;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface VigileRepository extends MongoRepository<Vigile, String> {

    Vigile findByUserLoginAndUserPassword(String login, String password);

}
