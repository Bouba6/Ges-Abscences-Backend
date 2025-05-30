package com.gesabsences.gesabsences.data.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.gesabsences.gesabsences.data.Entities.Abscence;
import com.gesabsences.gesabsences.data.Entities.Cours;

public interface AbscenceRepository extends MongoRepository<Abscence, String> {

    List<Abscence> findByCours(Cours cours);

    // boolean removeEleveFromAbsence(Long eleveId, Long coursId);

    @Query("{ 'eleve.id': ?0, 'cours.id': ?1 }")
    Abscence findByEleveIdAndCoursId(String eleveId, String coursId);

}
