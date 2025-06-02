package com.gesabsences.gesabsences.Web.Dto.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class JustificatifRequest {
    @JsonProperty("statutJustificatif")
    private String statutJustificatif;
}
