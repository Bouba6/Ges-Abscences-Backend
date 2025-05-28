package com.gesabsences.gesabsences.Mobile.Dto.Request;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AbscenceRequest {

    @Nullable
    private String eleveId;

    @Nullable
    private String motif;

    @Nullable
    private Boolean justifiee;

    @Nullable
    private String coursId;
}
