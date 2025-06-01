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

import com.gesabsences.gesabsences.Web.Controllers.UtilisateurJustificationController;
import com.gesabsences.gesabsences.Web.Dto.Response.UtilisateurJustificationResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.UtilisateurJustification;
import com.gesabsences.gesabsences.data.Services.UtilisateurJustificationService;
import com.gesabsences.gesabsences.Web.Mapper.UtilisateurJustificationMapper;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IUtilisateurJustificationController implements UtilisateurJustificationController {

    @Autowired
    private UtilisateurJustificationService utilisateurJustificationService;

    @Autowired
    private UtilisateurJustificationMapper utilisateurJustificationMapper;

    @Override
    public ResponseEntity<Map<String, Object>> Create(UtilisateurJustification objet) {
        try {
            UtilisateurJustification createdUtilisateurJustification = utilisateurJustificationService.create(objet);
            UtilisateurJustificationResponse response = utilisateurJustificationMapper.toDto(createdUtilisateurJustification);
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.CREATED,
                    response,
                    "Association utilisateur-justification créée avec succès"),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Erreur lors de la création de l'association utilisateur-justification: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ... existing code ...
}
