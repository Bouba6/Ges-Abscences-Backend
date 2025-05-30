package com.gesabsences.gesabsences.data.Repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gesabsences.gesabsences.data.Entities.User;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByLogin(String login); // Pour l’authentification avec "login"
}