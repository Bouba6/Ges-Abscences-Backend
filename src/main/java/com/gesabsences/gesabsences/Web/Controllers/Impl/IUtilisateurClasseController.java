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

import com.gesabsences.gesabsences.Web.Controllers.UtilisateurClasseController;
import com.gesabsences.gesabsences.Web.Dto.Response.UtilisateurClasseResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.UtilisateurClasse;
import com.gesabsences.gesabsences.data.Services.UtilisateurClasseService;
import com.gesabsences.gesabsences.Web.Mapper.UtilisateurClasseMapper;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IUtilisateurClasseController implements UtilisateurClasseController {

    @Autowired
    private UtilisateurClasseService utilisateurClasseService;

    @Autowired
    private UtilisateurClasseMapper utilisateurClasseMapper;

    @Override
    public ResponseEntity<Map<String, Object>> Create(UtilisateurClasse objet) {
        try {
            UtilisateurClasse createdUtilisateurClasse = utilisateurClasseService.create(objet);
            UtilisateurClasseResponse response = utilisateurClasseMapper.toDto(createdUtilisateurClasse);
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.CREATED,
                    response,
                    "Association utilisateur-classe créée avec succès"),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Erreur lors de la création de l'association utilisateur-classe: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ... existing code ...
}
