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

import com.gesabsences.gesabsences.Web.Controllers.UtilisateurEleveController;
import com.gesabsences.gesabsences.Web.Dto.Response.UtilisateurEleveResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.UtilisateurEleve;
import com.gesabsences.gesabsences.data.Services.UtilisateurEleveService;
import com.gesabsences.gesabsences.Web.Mapper.UtilisateurEleveMapper;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IUtilisateurEleveController implements UtilisateurEleveController {

    @Autowired
    private UtilisateurEleveService utilisateurEleveService;

    @Autowired
    private UtilisateurEleveMapper utilisateurEleveMapper;

    @Override
    public ResponseEntity<Map<String, Object>> Create(UtilisateurEleve objet) {
        try {
            UtilisateurEleve createdUtilisateurEleve = utilisateurEleveService.create(objet);
            UtilisateurEleveResponse response = utilisateurEleveMapper.toDto(createdUtilisateurEleve);
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.CREATED,
                    response,
                    "Association utilisateur-élève créée avec succès"),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Erreur lors de la création de l'association utilisateur-élève: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ... existing code ...
}
