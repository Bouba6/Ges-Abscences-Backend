package com.gesabsences.gesabsences.Mobile.Dto.Request;

import com.mongodb.lang.Nullable;

import lombok.Data;

@Data
public class PointageRequest {

    private String idEleve;

    @Nullable
    private String idCours;


    private String heurePointage;

    private String idVigile;

}
