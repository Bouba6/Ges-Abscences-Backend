package com.gesabsences.gesabsences.Web.Dto.Response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Date;

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
    private Date heureDebut;
    private Date heureFin;
    private LocalDate date;
}
