package com.gesabsences.gesabsences.data.Services.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gesabsences.gesabsences.config.Impl.IService;
import com.gesabsences.gesabsences.data.Entities.Classe;
import com.gesabsences.gesabsences.data.Entities.Professeur;
import com.gesabsences.gesabsences.data.Entities.ProfesseurClasse;
import com.gesabsences.gesabsences.data.Repositories.ProfesseurClasseRepository;
import com.gesabsences.gesabsences.data.Services.ProfesseurClasseService;

@Service
public class IProfesseurClasseService extends IService<ProfesseurClasse, ProfesseurClasseRepository>
        implements ProfesseurClasseService {

    private ProfesseurClasseRepository repository;

    public IProfesseurClasseService(ProfesseurClasseRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<Classe> findClassesByProfesseur(Professeur professeur) {
        return repository.findByProfesseur(professeur).stream().map(ProfesseurClasse::getClasse).toList();
    }

}
