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

import com.gesabsences.gesabsences.Web.Controllers.MatiereController;
import com.gesabsences.gesabsences.Web.Dto.Response.MatiereResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.Matiere;
import com.gesabsences.gesabsences.data.Services.MatiereService;
import com.gesabsences.gesabsences.Web.Mapper.MatiereMapper;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IMatiereController implements MatiereController {

    @Autowired
    private MatiereService matiereService;

    @Autowired
    private MatiereMapper matiereMapper;

    @Override
    public ResponseEntity<Map<String, Object>> Create(Matiere objet) {
        try {
            Matiere createdMatiere = matiereService.create(objet);
            MatiereResponse response = matiereMapper.toDto(createdMatiere);
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.CREATED,
                    response,
                    "Matière créée avec succès"),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Erreur lors de la création de la matière: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ... existing code ...
}
