package com.gesabsences.gesabsences.data.Entities;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class AnneeScolaire extends AbstractType {
    
    private String libelle; 

    private boolean estActive;
}
