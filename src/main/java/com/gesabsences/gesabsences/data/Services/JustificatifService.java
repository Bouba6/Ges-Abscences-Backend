package com.gesabsences.gesabsences.data.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gesabsences.gesabsences.data.Entities.Justitfication;

public interface JustificatifService {
    // autres méthodes...
    Justitfication justifierAbsence(String absenceId, Justitfication justificatif);

    Page<Justitfication> findAll(Pageable pageable);
}
