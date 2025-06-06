package com.gesabsences.gesabsences.data.Entities;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Data

public class Personne extends AbstractType {

    private String nom;
    private String prenom;
    private String email;
    private LocalDate dateNaissance;
    private String sexe;
    private String adresse;
    private String ville;
}
