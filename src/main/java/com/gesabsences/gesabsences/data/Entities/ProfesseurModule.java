package com.gesabsences.gesabsences.data.Entities;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("ProfesseurModule")
public class ProfesseurModule extends AbstractType {

    @DBRef
    private Professeur professeur;

    @DBRef
    private Module module;

}
