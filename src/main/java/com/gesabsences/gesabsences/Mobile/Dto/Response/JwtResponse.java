package com.gesabsences.gesabsences.Mobile.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String role;
    private String userId;
    private String nom;
    private String prenom;

    public JwtResponse(String accessToken, String role, String userId, String nom, String prenom) {
        this.token = accessToken;
        this.role = role;
        this.userId = userId;
        this.nom = nom;
        this.prenom = prenom;
    }
}