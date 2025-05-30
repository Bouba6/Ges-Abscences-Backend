package com.gesabsences.gesabsences.data.Services.Impl;

import org.springframework.stereotype.Service;

import com.gesabsences.gesabsences.config.Impl.IService;
import com.gesabsences.gesabsences.data.Entities.Professeur;
import com.gesabsences.gesabsences.data.Entities.Vigile;
import com.gesabsences.gesabsences.data.Repositories.ProfesseurRepository;
import com.gesabsences.gesabsences.data.Repositories.VigileRepository;
import com.gesabsences.gesabsences.data.Services.ProfesseurService;
import com.gesabsences.gesabsences.data.Services.VigileService;

@Service
public class IVigileService extends IService<Vigile, VigileRepository> implements VigileService {

    private final VigileRepository repository;

    public IVigileService(VigileRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Vigile findByLoginAndPassword(String login, String password) {
        return repository.findByUserLoginAndUserPassword(login, password);
    }

}
