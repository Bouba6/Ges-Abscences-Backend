package com.gesabsences.gesabsences.data.Entities;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.gesabsences.gesabsences.data.Enum.StatutJustification;

import lombok.Data;

@Data

@Document("justification")
public class Justification extends AbstractType {

    private String justificatif;

    @DBRef
    private Abscence abscence;

    private StatutJustification statutJustification;
}
