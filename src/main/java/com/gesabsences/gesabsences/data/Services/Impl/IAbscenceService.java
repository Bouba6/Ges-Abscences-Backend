package com.gesabsences.gesabsences.data.Services.Impl;

import com.gesabsences.gesabsences.data.Entities.Abscence;
import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.data.Repositories.AbscenceRepository;
import com.gesabsences.gesabsences.data.Services.AbscenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class IAbscenceService implements AbscenceService {

    @Autowired
    private AbscenceRepository abscenceRepository;

    @Override
    public List<Abscence> findAbsencesBetweenDates(LocalDate start, LocalDate end) {
        Date startDate = Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return abscenceRepository.findByCours_DateBetween(startDate, endDate);
    }

    @Override
    public List<Abscence> findAbsencesByDate(LocalDate date) {
        Date d = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return abscenceRepository.findByCours_Date(d);
    }

    @Override
    public List<Abscence> findByCours(Cours cours) {
        return abscenceRepository.findByCours(cours);
    }

    // @Override
    // public boolean removeEleveFromAbsence(Long eleveId, Long coursId) {
    // return absenceRepository.removeEleveFromAbsence(eleveId, coursId);
    // }

    @Override
    public Abscence getAbsenceDetails(String id, String coursId) {

        return abscenceRepository.findByEleveIdAndCoursId(id, coursId);
    }

    @Override
    public Abscence updateAbsence(String id, Abscence abscence) {
        Abscence abscence1 = abscenceRepository.findById(id).get();
        // abscence1.setJustifiee(abscence.getJustifiee());
        // abscence1.setMotif(abscence.getMotif());
        
        return absenceRepository.save(abscence1);
    }

    @Override
    public List<Abscence> findByEleveId(String eleveId) {
        return absenceRepository.findByEleveId(eleveId);
    }

}
