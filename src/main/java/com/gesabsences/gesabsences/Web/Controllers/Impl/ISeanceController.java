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

import com.gesabsences.gesabsences.Web.Controllers.SeanceController;
import com.gesabsences.gesabsences.Web.Dto.Response.SeanceResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.Seance;
import com.gesabsences.gesabsences.data.Services.SeanceService;
import com.gesabsences.gesabsences.Web.Mapper.SeanceMapper;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ISeanceController implements SeanceController {

    @Autowired
    private SeanceService seanceService;

    @Autowired
    private SeanceMapper seanceMapper;

    @Override
    public ResponseEntity<Map<String, Object>> Create(Seance objet) {
        try {
            Seance createdSeance = seanceService.create(objet);
            SeanceResponse response = seanceMapper.toDto(createdSeance);
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.CREATED,
                    response,
                    "Séance créée avec succès"),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Erreur lors de la création de la séance: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ... existing code ...
}
