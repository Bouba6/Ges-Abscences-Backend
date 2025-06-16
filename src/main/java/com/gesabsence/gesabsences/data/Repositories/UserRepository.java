package com.gesabsences.gesabsences.data.Repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gesabsences.gesabsences.data.Entities.User;

public interface UserRepository extends MongoRepository<User, String> {

<<<<<<< HEAD:src/main/java/com/gesabsence/gesabsences/data/Repositories/UserRepository.java
    Optional<User> findByLogin(String login); // Pour l’authentification avec "login"
}
=======
    boolean existsByLogin(String login);

    Optional<User> findByLogin(String login);
}
>>>>>>> origin/master:src/main/java/com/gesabsences/gesabsences/data/Repositories/UserRepository.java
