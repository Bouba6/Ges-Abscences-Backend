package com.gesabsences.gesabsences.data.Services.Impl;

import org.springframework.stereotype.Service;

import com.gesabsences.gesabsences.config.Impl.IService;
import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Entities.Justitfication;
import com.gesabsences.gesabsences.data.Repositories.EleveRepository;
import com.gesabsences.gesabsences.data.Repositories.JustificatifRepository;
import com.gesabsences.gesabsences.data.Services.JustificatifService;

@Service
public class IJustificatifService extends IService<Justitfication, JustificatifRepository> implements JustificatifService {
    
    private final JustificatifRepository justificatifRepository;

    public IJustificatifService(JustificatifRepository repository) {
        super(repository);
        this.justificatifRepository = repository;
    }
}
