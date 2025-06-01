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

import com.gesabsences.gesabsences.Web.Controllers.UtilisateurPermissionController;
import com.gesabsences.gesabsences.Web.Dto.Response.UtilisateurPermissionResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.UtilisateurPermission;
import com.gesabsences.gesabsences.data.Services.UtilisateurPermissionService;
import com.gesabsences.gesabsences.Web.Mapper.UtilisateurPermissionMapper;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IUtilisateurPermissionController implements UtilisateurPermissionController {

    @Autowired
    private UtilisateurPermissionService utilisateurPermissionService;

    @Autowired
    private UtilisateurPermissionMapper utilisateurPermissionMapper;

    @Override
    public ResponseEntity<Map<String, Object>> Create(UtilisateurPermission objet) {
        try {
            UtilisateurPermission createdUtilisateurPermission = utilisateurPermissionService.create(objet);
            UtilisateurPermissionResponse response = utilisateurPermissionMapper.toDto(createdUtilisateurPermission);
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.CREATED,
                    response,
                    "Association utilisateur-permission créée avec succès"),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Erreur lors de la création de l'association utilisateur-permission: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ... existing code ...
}
