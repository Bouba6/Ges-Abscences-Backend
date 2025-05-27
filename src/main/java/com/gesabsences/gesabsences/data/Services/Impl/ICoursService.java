package com.gesabsences.gesabsences.data.Services.Impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gesabsences.gesabsences.config.Impl.IService;
import com.gesabsences.gesabsences.data.Entities.Classe;
import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.data.Entities.Professeur;
import com.gesabsences.gesabsences.data.Repositories.CoursRepository;
import com.gesabsences.gesabsences.data.Services.CoursService;

@Service
public class ICoursService extends IService<Cours, CoursRepository> implements CoursService {

    private final CoursRepository coursRepository;

    public ICoursService(CoursRepository repository) {
        super(repository);
        this.coursRepository = repository;
    }

    @Override
    public List<Cours> findByClasseAndDateBetween(Classe classe, LocalDate startDate, LocalDate endDate) {
        return coursRepository.findByClasseAndDateBetween(classe, startDate, endDate);
    }

    @Override
    public List<Cours> findByProfesseurAndDateBetween(Professeur professeur, LocalDate startDate, LocalDate endDate) {
        return coursRepository.findByProfesseurAndDateBetween(professeur, startDate, endDate);
    }

}
