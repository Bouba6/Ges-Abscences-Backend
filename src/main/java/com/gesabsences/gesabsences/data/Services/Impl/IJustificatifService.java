package com.gesabsences.gesabsences.data.Services.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gesabsences.gesabsences.config.Impl.IService;
import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Entities.Justification;
import com.gesabsences.gesabsences.data.Repositories.EleveRepository;
import com.gesabsences.gesabsences.data.Repositories.JustificatifRepository;
import com.gesabsences.gesabsences.data.Services.JustificatifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IJustificatifService extends IService<Justification, JustificatifRepository>
        implements JustificatifService {

    private final JustificatifRepository justificatifRepository;
    private final EleveRepository eleveRepository;

    public IJustificatifService(JustificatifRepository repository, EleveRepository eleveRepository) {
        super(repository);
        this.justificatifRepository = repository;
        this.eleveRepository = eleveRepository;
    }

    @Override
    public List<Justification> findByEleve(String eleveId) {
        Eleve eleve = eleveRepository.findById(eleveId).orElse(null);
        if (eleve == null) {
            return null;
        }
        return justificatifRepository.findByAbscence_Eleve(eleve);
    }

   

    @Override
    public Justification create(Justification justificatif) {
       return justificatifRepository.save(justificatif);

    }

}
