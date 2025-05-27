package com.gesabsences.gesabsences.Web.Dto.Response;

import lombok.Data;

@Data
public class ProfesseurResponseClasse {

    private String id;
    private String nomProf;
    private ClasseResponse classes;
}
