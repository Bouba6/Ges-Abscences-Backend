package com.gesabsences.gesabsences.data.Repositories;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.domain.Pageable;

import com.gesabsences.gesabsences.data.Entities.Classe;
import com.gesabsences.gesabsences.data.Entities.Eleve;

public interface EleveRepository extends MongoRepository<Eleve, String> {

    Page<Eleve> findByClasse(Classe classe, Pageable pageable);

    List<Eleve> findByClasse(Classe classe);

    /**
     * Trouve tous les élèves qui ont cours dans la journée spécifiée
     * Utilise une agrégation pour joindre avec les cours via la classe
     */
    @Aggregation(pipeline = {
            // Jointure avec la collection classes
            "{ $lookup: { from: 'classes', localField: 'classe.$id', foreignField: '_id', as: 'classeInfo' } }",
            // Déplie le tableau classeInfo
            "{ $unwind: '$classeInfo' }",
            // Jointure avec les cours de la classe
            "{ $lookup: { from: 'cours', localField: 'classeInfo._id', foreignField: 'classe.$id', as: 'coursJour' } }",
            // Filtre les cours pour la date donnée
            "{ $match: { 'coursJour.date': { $gte: ?0, $lt: ?1 } } }",
            // Projection pour nettoyer le résultat
            "{ $project: { _id: 1, nom: 1, prenom: 1, classe: 1, coursJour: 1 } }"
    })
    List<Eleve> findElevesWithCoursOnDate(Date startOfDay, Date endOfDay);

    /**
     * Version simplifiée si vous préférez une query normale
     * Nécessite que vous passiez les IDs des classes qui ont cours
     */
    @Query("{ 'classe.$id': { $in: ?0 } }")
    List<Eleve> findElevesByClasseIds(List<ObjectId> classeIds);
}
