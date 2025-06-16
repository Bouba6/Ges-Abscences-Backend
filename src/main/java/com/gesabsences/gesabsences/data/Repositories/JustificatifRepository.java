package com.gesabsences.gesabsences.data.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Entities.Justification;

public interface JustificatifRepository extends MongoRepository<Justification, String> {

    List<Justification> findByAbscence_Eleve(Eleve eleve);

    Justification save(Justification justificatif);

}
