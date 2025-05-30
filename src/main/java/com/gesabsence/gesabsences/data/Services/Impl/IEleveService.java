package com.gesabsences.gesabsences.data.Services.Impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gesabsences.gesabsences.config.Impl.IService;
import com.gesabsences.gesabsences.data.Entities.Classe;
import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Repositories.EleveRepository;
import com.gesabsences.gesabsences.data.Services.EleveService;



@Service
public class IEleveService extends IService<Eleve, EleveRepository> implements EleveService {
    private final EleveRepository eleveRepository;

    public IEleveService(EleveRepository repository) {
        super(repository);
        this.eleveRepository = repository;
    }

    @Override
    public Page<Eleve> selectByClasse(Pageable pageable, Classe classe) {

        return eleveRepository.findByClasse(classe, pageable);
    }

}
