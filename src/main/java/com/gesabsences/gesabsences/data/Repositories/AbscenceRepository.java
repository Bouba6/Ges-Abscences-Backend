package com.gesabsences.gesabsences.data.Repositories;

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
}
