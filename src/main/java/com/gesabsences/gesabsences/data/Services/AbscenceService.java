package com.gesabsences.gesabsences.data.Services;

import java.time.LocalDate;
import java.util.List;

import com.gesabsences.gesabsences.config.Service;
import com.gesabsences.gesabsences.data.Entities.Abscence;
import com.gesabsences.gesabsences.data.Entities.Cours;

public interface AbscenceService extends Service<Abscence> {

    List<Abscence> findByCours(Cours cours);

    Abscence getAbsenceDetails(String id, String coursId);

    Abscence updateAbsence(String id, Abscence absence);
    
    List<Abscence> findAbsencesBetweenDates(LocalDate start, LocalDate end);
    List<Abscence> findAbsencesByDate(LocalDate date);
}
