package com.gesabsences.gesabsences.Mobile.Dto.Request;

import com.mongodb.lang.Nullable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class JustifierRequest {

    private String justificatif;

    private String statutJustification;

    private String abscenceId;

    @Nullable
    private String imageUrl;

}

