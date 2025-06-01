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

import com.gesabsences.gesabsences.Web.Controllers.AbsenceController;
import com.gesabsences.gesabsences.Web.Dto.Response.AbsenceResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.Absence;
import com.gesabsences.gesabsences.data.Services.AbsenceService;
import com.gesabsences.gesabsences.Web.Mapper.AbsenceMapper;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IAbsenceController implements AbsenceController {

    @Autowired
    private AbsenceService absenceService;

    @Autowired
    private AbsenceMapper absenceMapper;

    @Override
    public ResponseEntity<Map<String, Object>> Create(Absence objet) {
        try {
            Absence createdAbsence = absenceService.create(objet);
            AbsenceResponse response = absenceMapper.toDto(createdAbsence);
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.CREATED,
                    response,
                    "Absence créée avec succès"),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Erreur lors de la création de l'absence: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ... existing code ...
} 