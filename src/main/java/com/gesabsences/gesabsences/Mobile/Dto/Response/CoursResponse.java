package com.gesabsences.gesabsences.Mobile.Dto.Response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class CoursResponse {

    private String id;
    private String nomCours;
    private String idClasse;
    private String nomClasse;
    private String nomProfesseur;
    private String nomModule;
    private int nbHeures;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private LocalDate date;
}
