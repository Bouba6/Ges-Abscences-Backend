package com.gesabsences.gesabsences.data.Repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Pageable;

import com.gesabsences.gesabsences.data.Entities.Classe;
import com.gesabsences.gesabsences.data.Entities.Eleve;

public interface EleveRepository extends MongoRepository<Eleve, String> {

    Page<Eleve> findByClasse(Classe classe, Pageable pageable);

    List<Eleve> findByClasse(Classe classe);
}
