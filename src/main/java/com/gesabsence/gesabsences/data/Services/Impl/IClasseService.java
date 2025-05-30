package com.gesabsences.gesabsences.data.Services.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gesabsences.gesabsences.config.Impl.IService;
import com.gesabsences.gesabsences.data.Entities.Classe;
import com.gesabsences.gesabsences.data.Repositories.ClasseRepository;
import com.gesabsences.gesabsences.data.Services.ClasseService;

@Service

public class IClasseService extends IService<Classe, ClasseRepository> implements ClasseService {

    private final ClasseRepository classeRepository;

    public IClasseService(ClasseRepository classeRepository) {
        super(classeRepository); // Appel explicite au constructeur de IService
        this.classeRepository = classeRepository; // Initialisation du champ
    }

    @Override
    public Classe update(String id, Classe object) {
        return classeRepository.findById(id)
                .map(existingClasse -> {
                    existingClasse.setNomClasse(object.getNomClasse());
                    existingClasse.setNiveau(object.getNiveau());
                    // existingClasse.setProfesseurPrincipal(object.getProfesseurPrincipal());
                    existingClasse.setEtudiants(object.getEtudiants());
                    existingClasse.setEffectifs(object.getEffectifs());
                    return classeRepository.save(existingClasse);
                })
                .orElse(null);
    }

    @Override
    public Classe findByNomClasse(String nomClasse) {
        return classeRepository.findByNomClasse(nomClasse);
    }

}
