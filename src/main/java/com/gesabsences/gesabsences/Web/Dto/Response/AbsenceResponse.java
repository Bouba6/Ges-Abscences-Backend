package com.gesabsences.gesabsences.Web.Dto.Response;

import lombok.Data;

@Data
public class AbsenceResponse {

    private String id;
    private String idJustification;
    private String statutJustificatif;
    private String nomCours;
    private String typeAbscence;
    private String statutAbscence;
    private String nomEleve;

}
