package com.gesabsences.gesabsences.Mobile.Dto.Response;

import java.time.LocalTime;

import lombok.Data;

@Data
public class PointageRespoonse {

    private String eleveId;
    private String nomComplet;
    private String coursId;
    private String module;
    private LocalTime heureArrivee;
    private LocalTime heureCoursDebut;
    private String statut; // PRESENT, RETARD, ABSENT
    private String message;
}
