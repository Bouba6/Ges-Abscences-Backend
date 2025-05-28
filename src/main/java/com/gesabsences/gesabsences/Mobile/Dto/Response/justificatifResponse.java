package com.gesabsences.gesabsences.Mobile.Dto.Response;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.gesabsences.gesabsences.data.Entities.Abscence;
import com.gesabsences.gesabsences.data.Enum.StatutJustification;

import lombok.Data;

@Data
public class justificatifResponse {
    

    private String statutJustification;

    private String typeAbscence;

    private String nomEleve;

    private String nomCours;

    private String statutAbscence;


}


