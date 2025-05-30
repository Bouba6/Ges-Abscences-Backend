package com.gesabsences.gesabsences.Web.Dto.Response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EleveResponse {
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate dateNaissance;
    private String sexe;
    private String adresse;
    private String ville;
    private String nomClasse;

}
