package com.gesabsences.gesabsences.Mobile.Dto.Response;

import java.util.List;

import lombok.Data;

@Data
public class EleveAvecCoursResponse {

    private String eleveId;
    private String nom;
    private String prenom;
    private String classeId;
    private String nomClasse;
    private List<CoursResponse> coursJour;
    private CoursResponse prochainCours;
    private boolean aCoursEnCours;
}
