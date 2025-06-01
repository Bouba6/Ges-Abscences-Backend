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

import com.gesabsences.gesabsences.Web.Controllers.UtilisateurAbsenceController;
import com.gesabsences.gesabsences.Web.Dto.Response.UtilisateurAbsenceResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.UtilisateurAbsence;
import com.gesabsences.gesabsences.data.Services.UtilisateurAbsenceService;
import com.gesabsences.gesabsences.Web.Mapper.UtilisateurAbsenceMapper;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IUtilisateurAbsenceController implements UtilisateurAbsenceController {

    @Autowired
    private UtilisateurAbsenceService utilisateurAbsenceService;

    @Autowired
    private UtilisateurAbsenceMapper utilisateurAbsenceMapper;

    @Override
    public ResponseEntity<Map<String, Object>> Create(UtilisateurAbsence objet) {
        try {
            UtilisateurAbsence createdUtilisateurAbsence = utilisateurAbsenceService.create(objet);
            UtilisateurAbsenceResponse response = utilisateurAbsenceMapper.toDto(createdUtilisateurAbsence);
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.CREATED,
                    response,
                    "Association utilisateur-absence créée avec succès"),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Erreur lors de la création de l'association utilisateur-absence: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ... existing code ...
}
