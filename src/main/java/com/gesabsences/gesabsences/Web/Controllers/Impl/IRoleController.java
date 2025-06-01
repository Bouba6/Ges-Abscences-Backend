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

import com.gesabsences.gesabsences.Web.Controllers.RoleController;
import com.gesabsences.gesabsences.Web.Dto.Response.RoleResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.Role;
import com.gesabsences.gesabsences.data.Services.RoleService;
import com.gesabsences.gesabsences.Web.Mapper.RoleMapper;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IRoleController implements RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public ResponseEntity<Map<String, Object>> Create(Role objet) {
        try {
            Role createdRole = roleService.create(objet);
            RoleResponse response = roleMapper.toDto(createdRole);
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.CREATED,
                    response,
                    "Rôle créé avec succès"),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Erreur lors de la création du rôle: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ... existing code ...
}
