package com.gesabsences.gesabsences.data.Entities;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("professeurClasse")
public class ProfesseurClasse extends AbstractType {
    @DBRef
    private Professeur professeur;

    @DBRef
    private Classe classe;

}
