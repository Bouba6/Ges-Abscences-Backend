package com.gesabsences.gesabsences.data.Services.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gesabsences.gesabsences.config.Impl.IService;
import com.gesabsences.gesabsences.data.Entities.Professeur;
import com.gesabsences.gesabsences.data.Repositories.ProfesseurRepository;
import com.gesabsences.gesabsences.data.Services.ProfesseurService;

import lombok.RequiredArgsConstructor;

@Service
public class IProfesseurService extends IService<Professeur, ProfesseurRepository> implements ProfesseurService {
    private final ProfesseurRepository professeurRepository;

    public IProfesseurService(ProfesseurRepository repository) {
        super(repository);
        this.professeurRepository = repository;
    }

}
