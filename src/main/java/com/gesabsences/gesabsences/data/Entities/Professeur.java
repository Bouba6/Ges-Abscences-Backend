package com.gesabsences.gesabsences.data.Entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Data;

@Data
@Document(collection = "professeurs")
public class Professeur extends Personne {

    @DBRef
    private List<ProfesseurClasse> professeurClasses = new ArrayList<>();

    @DBRef
    private List<ProfesseurModule> professeurModules = new ArrayList<>();

    @DBRef
    private List<Cours> cours = new ArrayList<>();
}
