package com.gesabsences.gesabsences.Mobile.Dto.Response;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class ClasseDetailResponse {
    ClasseResponse classe;
    List<EleveResponse> etudiants;

}
