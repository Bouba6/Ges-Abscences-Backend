package com.gesabsences.gesabsences.data.Services;

import java.util.List;

import com.gesabsences.gesabsences.config.Service;
import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Entities.Justification;

public interface JustificatifService extends Service<Justification> {

    List<Justification> findByEleve(String eleveId);

    Justification create(Justification justificatif);
}
