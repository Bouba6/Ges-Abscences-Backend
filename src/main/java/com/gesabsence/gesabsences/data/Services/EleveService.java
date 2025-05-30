package com.gesabsences.gesabsences.data.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gesabsences.gesabsences.config.Service;
import com.gesabsences.gesabsences.data.Entities.Classe;
import com.gesabsences.gesabsences.data.Entities.Eleve;

public interface EleveService extends Service<Eleve> {

    Page<Eleve> selectByClasse(Pageable pageable, Classe classe);

}
