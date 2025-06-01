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

import com.gesabsences.gesabsences.Web.Controllers.JustificationController;
import com.gesabsences.gesabsences.Web.Dto.Response.JustificationResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.Justification;
import com.gesabsences.gesabsences.data.Services.JustificationService;
import com.gesabsences.gesabsences.Web.Mapper.JustificationMapper;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IJustificationController implements JustificationController {

    @Autowired
    private JustificationService justificationService;

    @Autowired
    private JustificationMapper justificationMapper;

    @Override
    public ResponseEntity<Map<String, Object>> Create(Justification objet) {
        try {
            Justification createdJustification = justificationService.create(objet);
            JustificationResponse response = justificationMapper.toDto(createdJustification);
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.CREATED,
                    response,
                    "Justification créée avec succès"),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Erreur lors de la création de la justification: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ... existing code ...
}
