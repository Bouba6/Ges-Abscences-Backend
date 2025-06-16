package com.gesabsences.gesabsences.Mobile.Dto.Response;

import com.gesabsences.gesabsences.Web.Dto.Response.ClasseResponse;
import lombok.Data;

@Data
public class NiveauResponse {

    private String id;
    private String nomNiveau;
    private ClasseResponse[] classes;

}
