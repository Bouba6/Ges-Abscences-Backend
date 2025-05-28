package com.gesabsences.gesabsences.data.Entities;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
// @Document(collection = "classes")
public class Classe extends AbstractType {

    private String nomClasse;

    @DBRef
    private List<Eleve> etudiants;

    @DBRef
    private Niveau niveau;

    @DBRef
    private List<Professeur> professeurs;

    @DBRef
    private List<Cours> cours;

    @DBRef
    private Professeur profPrincipal;

    private int effectifs;
}
