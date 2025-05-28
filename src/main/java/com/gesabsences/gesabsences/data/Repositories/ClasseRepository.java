package com.gesabsences.gesabsences.data.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gesabsences.gesabsences.data.Entities.Classe;

public interface ClasseRepository extends MongoRepository<Classe, String> {

    Classe findByNomClasse(String nomClasse);

    List<Classe> findByEtudiants_Id(String idEtudiant);
}
