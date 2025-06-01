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

import com.gesabsences.gesabsences.Web.Controllers.UtilisateurRoleController;
import com.gesabsences.gesabsences.Web.Dto.Response.UtilisateurRoleResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.UtilisateurRole;
import com.gesabsences.gesabsences.data.Services.UtilisateurRoleService;
import com.gesabsences.gesabsences.Web.Mapper.UtilisateurRoleMapper;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IUtilisateurRoleController implements UtilisateurRoleController {

    @Autowired
    private UtilisateurRoleService utilisateurRoleService;

    @Autowired
    private UtilisateurRoleMapper utilisateurRoleMapper;

    @Override
    public ResponseEntity<Map<String, Object>> Create(UtilisateurRole objet) {
        try {
            UtilisateurRole createdUtilisateurRole = utilisateurRoleService.create(objet);
            UtilisateurRoleResponse response = utilisateurRoleMapper.toDto(createdUtilisateurRole);
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.CREATED,
                    response,
                    "Association utilisateur-rôle créée avec succès"),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Erreur lors de la création de l'association utilisateur-rôle: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ... existing code ...
}
