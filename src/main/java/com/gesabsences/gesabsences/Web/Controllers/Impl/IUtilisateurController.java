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

import com.gesabsences.gesabsences.Web.Controllers.UtilisateurController;
import com.gesabsences.gesabsences.Web.Dto.Response.UtilisateurResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.Utilisateur;
import com.gesabsences.gesabsences.data.Services.UtilisateurService;
import com.gesabsences.gesabsences.Web.Mapper.UtilisateurMapper;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IUtilisateurController implements UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private UtilisateurMapper utilisateurMapper;

    @Override
    public ResponseEntity<Map<String, Object>> Create(Utilisateur objet) {
        try {
            Utilisateur createdUtilisateur = utilisateurService.create(objet);
            UtilisateurResponse response = utilisateurMapper.toDto(createdUtilisateur);
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.CREATED,
                    response,
                    "Utilisateur créé avec succès"),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Erreur lors de la création de l'utilisateur: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ... existing code ...
}
