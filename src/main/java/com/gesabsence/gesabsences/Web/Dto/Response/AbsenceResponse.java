package com.gesabsences.gesabsences.Web.Dto.Response;

import lombok.Data;

@Data
public class AbsenceResponse {

    private String id;
    private EleveResponse eleve;
    private CoursResponse cours;
    private String motif;
    private Boolean justifiee;

}
