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

import com.gesabsences.gesabsences.Web.Controllers.RolePermissionController;
import com.gesabsences.gesabsences.Web.Dto.Response.RolePermissionResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.RolePermission;
import com.gesabsences.gesabsences.data.Services.RolePermissionService;
import com.gesabsences.gesabsences.Web.Mapper.RolePermissionMapper;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IRolePermissionController implements RolePermissionController {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public ResponseEntity<Map<String, Object>> Create(RolePermission objet) {
        try {
            RolePermission createdRolePermission = rolePermissionService.create(objet);
            RolePermissionResponse response = rolePermissionMapper.toDto(createdRolePermission);
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.CREATED,
                    response,
                    "Association rôle-permission créée avec succès"),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Erreur lors de la création de l'association rôle-permission: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ... existing code ...
}
