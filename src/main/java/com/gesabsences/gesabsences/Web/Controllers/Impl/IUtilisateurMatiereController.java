package com.gesabsences.gesabsences.Web.Controllers.Impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gesabsences.gesabsences.Web.Controllers.UtilisateurMatiereController;
import com.gesabsences.gesabsences.Web.Dto.Response.UtilisateurMatiereResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.UtilisateurMatiere;
import com.gesabsences.gesabsences.data.Services.UtilisateurMatiereService;
import com.gesabsences.gesabsences.Web.Mapper.UtilisateurMatiereMapper;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IUtilisateurMatiereController implements UtilisateurMatiereController {

    @Autowired
    private UtilisateurMatiereService utilisateurMatiereService;

    @Autowired
    private UtilisateurMatiereMapper utilisateurMatiereMapper;

    @Override
    public ResponseEntity<Map<String, Object>> Create(UtilisateurMatiere objet) {
        try {
            UtilisateurMatiere createdUtilisateurMatiere = utilisateurMatiereService.create(objet);
            UtilisateurMatiereResponse response = utilisateurMatiereMapper.toDto(createdUtilisateurMatiere);
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.CREATED,
                    response,
                    "Association utilisateur-matière créée avec succès"),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Erreur lors de la création de l'association utilisateur-matière: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ... existing code ...
}
