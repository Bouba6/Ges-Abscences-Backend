package com.gesabsences.gesabsences.data.Services.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gesabsences.gesabsences.config.Impl.IService;
import com.gesabsences.gesabsences.data.Entities.Abscence;
import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.data.Repositories.AbscenceRepository;
import com.gesabsences.gesabsences.data.Services.AbscenceService;

@Service
public class IAbscenceService extends IService<Abscence, AbscenceRepository> implements AbscenceService {

    private final AbscenceRepository absenceRepository;

    public IAbscenceService(AbscenceRepository repository) {
        super(repository);
        this.absenceRepository = repository;
    }

    @Override
    public List<Abscence> findByCours(Cours cours) {
        return absenceRepository.findByCours(cours);
    }

    // @Override
    // public boolean removeEleveFromAbsence(Long eleveId, Long coursId) {
    // return absenceRepository.removeEleveFromAbsence(eleveId, coursId);
    // }

    @Override
    public Abscence getAbsenceDetails(String id, String coursId) {

        return absenceRepository.findByEleveIdAndCoursId(id, coursId);
    }

    @Override
    public Abscence updateAbsence(String id, Abscence abscence) {
        Abscence abscence1 = absenceRepository.findById(id).get();
        // abscence1.setJustifiee(abscence.getJustifiee());
        // abscence1.setMotif(abscence.getMotif());
        
        return absenceRepository.save(abscence1);
    }

    @Override
    public List<Abscence> findByEleveId(String eleveId) {
        return absenceRepository.findByEleveId(eleveId);
    }

}
