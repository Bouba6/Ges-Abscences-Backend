package com.gesabsences.gesabsences.data.Entities;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Document("eleve")
public class Eleve extends Personne {

    @DBRef
    private List<Abscence> absences;

    @DBRef
    private Classe classe;

    @DBRef
    private User user;

    @DBRef
    private List<Inscription> inscriptions;

}
