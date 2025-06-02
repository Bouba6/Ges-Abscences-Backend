package com.gesabsences.gesabsences.data.Repositories;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.gesabsences.gesabsences.data.Entities.Classe;
import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Entities.Professeur;

public interface CoursRepository extends MongoRepository<Cours, String> {

    List<Cours> findByClasseAndDateBetween(Classe classe, Date startDate, Date endDate);

    List<Cours> findByProfesseurAndDateBetween(Professeur professeur, LocalDate startDate, LocalDate endDate);

    List<Cours> findByClasseIn(List<Classe> classes);

    List<Cours> findByClasse(Classe classe);

    List<Cours> findByClasseAndDateBetween(Classe classe, LocalDate startDate, LocalDate endDate);

    List<Cours> findByDate(Date date);

    List<Cours> findByDateBetween(Date startDate, Date endDate);

    
    // Cours d'un élève pour un professeur spécifique
    List<Cours> findByClasseAndProfesseur(Classe classe, Professeur professeur);
    
    // Cours d'un élève pour un module spécifique
    List<Cours> findByClasseAndModule(Classe classe, Module module);
    
    // Query personnalisées avec MongoDB
    
    // Trouver les cours où un élève spécifique est présent dans la classe
    @Query("{'classe.etudiants': ?0}")
    List<Cours> findCoursForEleve(Eleve eleve);
    
    // Cours d'un élève par son ID (plus performant)
    @Query("{'classe.etudiants': {'$elemMatch': {'$eq': ?0}}}")
    List<Cours> findCoursForEleveById(String eleveId);
    
    // Cours d'un élève dans une période avec query personnalisée
    @Query("{'classe.etudiants': ?0, 'date': {'$gte': ?1, '$lte': ?2}}")
    List<Cours> findCoursForEleveAndPeriod(Eleve eleve, Date startDate, Date endDate);
    
    // Cours d'aujourd'hui pour un élève
    @Query("{'classe.etudiants': ?0, 'date': ?1}")
    List<Cours> findCoursForEleveToday(Eleve eleve, Date today);
    
    // Cours futurs pour un élève
    @Query("{'classe.etudiants': ?0, 'date': {'$gte': ?1}}")
    List<Cours> findFutureCoursForEleve(Eleve eleve, Date fromDate);

}
