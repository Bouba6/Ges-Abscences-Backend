package com.gesabsences.gesabsences.data.Repositories;

import com.gesabsences.gesabsences.data.Entities.Pointage;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PointageRepository extends MongoRepository<Pointage, String> {

        // Méthode 1: Recherche par plage de dates
        List<Pointage> findByEtudiant_IdAndCours_IdAndHeurePointageBetween(
                        String eleveId, String coursId, Date startDate, Date endDate);

        boolean existsByEtudiantIdAndHeurePointageBetween(
                        String etudiantId, Date startDate, Date endDate);

        // Si vous voulez vérifier par cours aussi
        boolean existsByEtudiantIdAndCoursIdAndHeurePointageBetween(
                        String etudiantId, String coursId, Date startDate, Date endDate);

        /// recuperer la lsite des pointages en fonction du vigileid

        List<Pointage> findByVigileId(String vigileId);
}
