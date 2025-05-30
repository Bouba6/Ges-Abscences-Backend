package com.gesabsences.gesabsences.data.Services.Impl;

import org.springframework.stereotype.Service;

import com.gesabsences.gesabsences.config.Impl.IService;
import com.gesabsences.gesabsences.data.Entities.Niveau;
import com.gesabsences.gesabsences.data.Repositories.NiveauRepository;
import com.gesabsences.gesabsences.data.Services.NiveauService;


@Service
public class INiveauService extends IService<Niveau, NiveauRepository> implements NiveauService {
    private NiveauRepository niveauRepository;

    public INiveauService(NiveauRepository repository) {
        super(repository);
        this.niveauRepository = repository;
    }

}
