package com.gesabsences.gesabsences.data.Services;

import java.util.List;

import com.gesabsences.gesabsences.config.Service;
import com.gesabsences.gesabsences.data.Entities.Classe;
import com.gesabsences.gesabsences.data.Entities.Professeur;
import com.gesabsences.gesabsences.data.Entities.ProfesseurClasse;

public interface ProfesseurClasseService extends Service<ProfesseurClasse> {

    List<Classe> findClassesByProfesseur(Professeur professeur);

}
