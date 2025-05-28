package com.gesabsences.gesabsences.data.Repositories;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gesabsences.gesabsences.data.Entities.Classe;
import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Entities.Professeur;

public interface CoursRepository extends MongoRepository<Cours, String> {

    List<Cours> findByClasseAndDateBetween(Classe classe, Date startDate, Date endDate);

    List<Cours> findByProfesseurAndDateBetween(Professeur professeur, LocalDate startDate, LocalDate endDate);


    List<Cours> findByClasseIn(List<Classe> classes);

    List<Cours> findByClasse(Classe classe);

}
