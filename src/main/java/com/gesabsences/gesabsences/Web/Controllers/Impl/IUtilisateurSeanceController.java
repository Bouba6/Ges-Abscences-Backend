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

import com.gesabsences.gesabsences.Web.Controllers.UtilisateurSeanceController;
import com.gesabsences.gesabsences.Web.Dto.Response.UtilisateurSeanceResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.UtilisateurSeance;
import com.gesabsences.gesabsences.data.Services.UtilisateurSeanceService;
import com.gesabsences.gesabsences.Web.Mapper.UtilisateurSeanceMapper;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IUtilisateurSeanceController implements UtilisateurSeanceController {

    @Autowired
    private UtilisateurSeanceService utilisateurSeanceService;

    @Autowired
    private UtilisateurSeanceMapper utilisateurSeanceMapper;

    @Override
    public ResponseEntity<Map<String, Object>> Create(UtilisateurSeance objet) {
        try {
            UtilisateurSeance createdUtilisateurSeance = utilisateurSeanceService.create(objet);
            UtilisateurSeanceResponse response = utilisateurSeanceMapper.toDto(createdUtilisateurSeance);
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.CREATED,
                    response,
                    "Association utilisateur-séance créée avec succès"),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Erreur lors de la création de l'association utilisateur-séance: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ... existing code ...
}
