package com.gesabsences.gesabsences.data.Services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.gesabsences.gesabsences.config.Service;
import com.gesabsences.gesabsences.data.Entities.Classe;
import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.data.Entities.Professeur;

public interface CoursService extends Service<Cours> {

    List<Cours> findByClasseAndDateBetween(Classe classe, Date startDate, Date endDate);

    List<Cours> findByProfesseurAndDateBetween(Professeur professeur, LocalDate startDate, LocalDate endDate);
}
