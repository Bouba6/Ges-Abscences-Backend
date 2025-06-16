package com.gesabsences.gesabsences.data.Services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import com.gesabsences.gesabsences.config.Service;
import com.gesabsences.gesabsences.data.Entities.Abscence;
import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.data.Entities.Professeur;
import org.springframework.data.domain.Pageable;
public interface AbscenceService extends Service<Abscence> {

    List<Abscence> findByCours(Cours cours);

    Abscence getAbsenceDetails(String id, String coursId);

    Abscence updateAbsence(String id, Abscence absence);

    List<Abscence> findByEleveId(String eleveId);

    Page<Abscence> findByStatutAbscence(String statutAbscence, Pageable pageable);
}
