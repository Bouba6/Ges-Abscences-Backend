package com.gesabsences.gesabsences.data.Services;

import com.gesabsences.gesabsences.config.Service;
import com.gesabsences.gesabsences.data.Entities.Classe;

public interface ClasseService extends Service<Classe> {

    Classe update(String id, Classe object);

    Classe findByNomClasse(String nomClasse);
}
