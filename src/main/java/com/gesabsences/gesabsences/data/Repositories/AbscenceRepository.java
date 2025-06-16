package com.gesabsences.gesabsences.data.Repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.gesabsences.gesabsences.data.Entities.Abscence;
import com.gesabsences.gesabsences.data.Entities.Cours;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Date;
import java.util.List;

public interface AbscenceRepository extends MongoRepository<Abscence, String> {
    List<Abscence> findByCours_DateBetween(Date start, Date end);
    List<Abscence> findByCours_Date(Date date);
    Abscence findByEleveIdAndCoursId(String id, String coursId);
    List<Abscence> findByCours(Cours cours);

    // boolean removeEleveFromAbsence(Long eleveId, Long coursId);

    @Query("{ 'eleve.id': ?0, 'cours.id': ?1 }")
    Abscence findByEleveIdAndCoursId(String eleveId, String coursId);


    List<Abscence> findByEleveId(String eleveId);

    Page<Abscence> findByStatutAbscence(String statutAbscence, Pageable pageable);

}
