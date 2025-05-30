package com.gesabsences.gesabsences.data.Entities;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Document("ProfesseurModule")
public class ProfesseurModule extends AbstractType {

    @DBRef
    private Professeur professeur;

    @DBRef
    private Module module;

}
