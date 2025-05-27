package com.gesabsences.gesabsences.data.Entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Data;

@Data
@Document("cours")
public class Cours extends AbstractType {

   
    @DBRef
    private Classe classe;

    @DBRef
    private Professeur professeur;

    @DBRef
    private Module module;

    private int nbrHeures;

    private LocalTime heureDebut;

    private LocalTime heureFin;

    private LocalDate date;

    private String salle;

    @DBRef
    private List<Abscence> absences;

}
