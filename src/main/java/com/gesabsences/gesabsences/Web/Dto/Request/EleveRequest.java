package com.gesabsences.gesabsences.Web.Dto.Request;

import java.time.LocalDate;

import com.gesabsences.gesabsences.data.Entities.Classe;

import lombok.Data;

@Data
public class EleveRequest {

    private String nom;
    private String prenom;
    private String email;
    private LocalDate dateNaissance;
    private String sexe;
    private String adresse;
    private String ville;
    private Classe classe;
}
