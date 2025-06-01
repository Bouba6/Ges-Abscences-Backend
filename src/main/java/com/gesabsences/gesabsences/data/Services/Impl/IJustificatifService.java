package com.gesabsences.gesabsences.data.Services.Impl;

import com.gesabsences.gesabsences.data.Entities.Abscence;
import com.gesabsences.gesabsences.data.Entities.Justitfication;
import com.gesabsences.gesabsences.data.Enum.StatutAbscence;
import com.gesabsences.gesabsences.data.Repositories.AbscenceRepository;
import com.gesabsences.gesabsences.data.Repositories.JustificatifRepository;
import com.gesabsences.gesabsences.data.Services.JustificatifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IJustificatifService implements JustificatifService {

    @Autowired
    private AbscenceRepository abscenceRepository;
    @Autowired
    private JustificatifRepository justificatifRepository;

    @Override
    public Justitfication justifierAbsence(String absenceId, Justitfication justificatif) {
        Abscence absence = abscenceRepository.findById(absenceId)
            .orElseThrow(() -> new RuntimeException("Absence non trouvée"));
        justificatif.setAbscence(absence);
        absence.setStatutAbscence(StatutAbscence.JUSTIFIER);
        abscenceRepository.save(absence);
        return justificatifRepository.save(justificatif);
    }

    @Override
    public Page<Justitfication> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    // autres méthodes...
}
