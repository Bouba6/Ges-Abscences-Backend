package com.gesabsences.gesabsences.data.Services.Impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gesabsences.gesabsences.config.Impl.IService;
import com.gesabsences.gesabsences.data.Entities.Classe;
import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Entities.Professeur;
import com.gesabsences.gesabsences.data.Repositories.ClasseRepository;
import com.gesabsences.gesabsences.data.Repositories.CoursRepository;
import com.gesabsences.gesabsences.data.Services.CoursService;
import com.gesabsences.gesabsences.data.Services.EleveService;

@Service
public class ICoursService extends IService<Cours, CoursRepository> implements CoursService {

    private final CoursRepository coursRepository;
    private final ClasseRepository classeRepository;

    public ICoursService(CoursRepository repository, ClasseRepository classeRepository) {
        super(repository);
        this.coursRepository = repository;
        this.classeRepository = classeRepository;

    }

    @Override
    public List<Cours> findByClasseAndDateBetween(Classe classe, Date startDate, Date endDate) {
        return coursRepository.findByClasseAndDateBetween(classe, startDate, endDate);
    }

    @Override
    public List<Cours> findByProfesseurAndDateBetween(Professeur professeur, LocalDate startDate, LocalDate endDate) {
        return coursRepository.findByProfesseurAndDateBetween(professeur, startDate, endDate);
    }

    @Override
    public List<Cours> findCoursForEleve(Eleve eleve) {
        return coursRepository.findByClasse(eleve.getClasse());
    }

    public List<Cours> getCoursForEleveToday(Eleve eleve) {
        ZoneId zoneId = ZoneOffset.UTC; // Très important !
        LocalDate today = LocalDate.now(zoneId);

        // Début et fin de la journée en UTC
        Instant startOfDay = today.atStartOfDay(zoneId).toInstant();
        Instant endOfDay = today.plusDays(1).atStartOfDay(zoneId).minusNanos(1).toInstant();

        Date startDate = Date.from(startOfDay);
        Date endDate = Date.from(endOfDay);

        return coursRepository.findByClasseAndDateBetween(eleve.getClasse(), startDate, endDate);
    }

    @Override
    public List<Cours> findByDate(Date date) {
        return coursRepository.findByDate(date);
    }

}
