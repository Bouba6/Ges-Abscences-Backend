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

import com.gesabsences.gesabsences.Web.Controllers.UtilisateurProfesseurController;
import com.gesabsences.gesabsences.Web.Dto.Response.UtilisateurProfesseurResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.UtilisateurProfesseur;
import com.gesabsences.gesabsences.data.Services.UtilisateurProfesseurService;
import com.gesabsences.gesabsences.Web.Mapper.UtilisateurProfesseurMapper;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IUtilisateurProfesseurController implements UtilisateurProfesseurController {

    @Autowired
    private UtilisateurProfesseurService utilisateurProfesseurService;

    @Autowired
    private UtilisateurProfesseurMapper utilisateurProfesseurMapper;

    @Override
    public ResponseEntity<Map<String, Object>> Create(UtilisateurProfesseur objet) {
        try {
            UtilisateurProfesseur createdUtilisateurProfesseur = utilisateurProfesseurService.create(objet);
            UtilisateurProfesseurResponse response = utilisateurProfesseurMapper.toDto(createdUtilisateurProfesseur);
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.CREATED,
                    response,
                    "Association utilisateur-professeur créée avec succès"),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Erreur lors de la création de l'association utilisateur-professeur: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ... existing code ...
}
