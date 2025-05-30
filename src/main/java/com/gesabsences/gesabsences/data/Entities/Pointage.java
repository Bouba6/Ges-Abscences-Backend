package com.gesabsences.gesabsences.data.Entities;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Pointage extends AbstractType {

    private Eleve etudiant;

    private Cours cours;

    private Date heurePointage;

}
