package com.gesabsences.gesabsences.data.Services;

import java.util.List;

import com.gesabsences.gesabsences.config.Service;
import com.gesabsences.gesabsences.data.Entities.Abscence;
import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.data.Entities.Professeur;

public interface AbscenceService extends Service<Abscence> {

    List<Abscence> findByCours(Cours cours);

    Abscence getAbsenceDetails(String id, String coursId);

    Abscence updateAbsence(String id, Abscence absence);

    List<Abscence> findByEleveId(String eleveId);
}
