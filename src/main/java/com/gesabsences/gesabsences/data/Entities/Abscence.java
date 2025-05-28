package com.gesabsences.gesabsences.data.Entities;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.gesabsences.gesabsences.data.Enum.StatutAbscence;
import com.gesabsences.gesabsences.data.Enum.TypeAbscence;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
// @Document("abscence")
public class Abscence extends AbstractType {

    @DBRef
    private Justitfication justificatif;

    private TypeAbscence typeAbscence;

    @DBRef
    private Eleve eleve;

    @DBRef
    private Cours cours;

    private StatutAbscence statutAbscence;

}
