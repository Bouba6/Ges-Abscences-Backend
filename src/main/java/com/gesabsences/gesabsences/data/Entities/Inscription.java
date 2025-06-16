package com.gesabsences.gesabsences.data.Entities;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Inscription extends AbstractType {

    @DBRef
    private Eleve eleve;

    @DBRef
    private AnneeScolaire anneeScolaire;

    @DBRef
    private Classe classe;

    private LocalDate dateInscription;

    private boolean estActive;
}
