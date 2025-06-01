package com.gesabsences.gesabsences.data.Entities;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.gesabsences.gesabsences.data.Enum.StatutJustification;

import lombok.Data;

@Data

@Document("justitfication")
public class Justitfication {

    private String justificatif;

    @DBRef
    private Abscence abscence;

    private StatutJustification statutJustification;

    public void setJustifiee(Boolean justifiee) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setJustifiee'");
    }
}
