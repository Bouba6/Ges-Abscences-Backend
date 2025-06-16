package com.gesabsences.gesabsences.Mobile.Dto.Response;

import java.time.LocalTime;
import java.util.Date;

import lombok.Data;

@Data
public class PointageValideResponse {
    private String eleveId;
    private String nomComplet;
    private String coursId;
    private String module;
    private Date heureArrivee;
    private Date heureCoursDebut;
    private String statut; // PRESENT, RETARD, ABSENT
    private String message;
}
