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

import com.gesabsences.gesabsences.Web.Controllers.EleveController;
import com.gesabsences.gesabsences.Web.Dto.Response.EleveResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Services.EleveService;
import com.gesabsences.gesabsences.Web.Mapper.EleveMapper;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IEleveController implements EleveController {

    @Autowired
    private EleveService eleveService;

    @Autowired
    private EleveMapper eleveMapper;

    @Override
    public ResponseEntity<Map<String, Object>> SelectAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Eleve> Eleves = eleveService.findAll(pageable);
        Page<EleveResponse> Eleve = Eleves.map(eleveMapper::toDto);
        return new ResponseEntity<>(RestResponse.responsePaginate(
                HttpStatus.OK,
                Eleve.getContent(),
                Eleve.getNumber(),
                Eleve.getTotalPages(),
                Eleve.getTotalElements(),
                Eleve.isFirst(),
                Eleve.isLast(),
                "Eleve"),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> SelectdById(String id) {
        try {
            Eleve eleve = eleveService.findById(id);
            if (eleve != null) {
                EleveResponse response = eleveMapper.toDto(eleve);
                return new ResponseEntity<>(RestResponse.response(
                        HttpStatus.OK,
                        response,
                        "Étudiant trouvé avec succès"),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(RestResponse.response(
                        HttpStatus.NOT_FOUND,
                        null,
                        "Étudiant non trouvé"),
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Erreur lors de la récupération de l'étudiant: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> Update(String id, Eleve objet) {
        try {
            Eleve existingEleve = eleveService.findById(id);
            if (existingEleve != null) {
                // Mise à jour des champs
                if (objet.getNom() != null) {
                    existingEleve.setNom(objet.getNom());
                }
                if (objet.getPrenom() != null) {
                    existingEleve.setPrenom(objet.getPrenom());
                }
                if (objet.getEmail() != null) {
                    existingEleve.setEmail(objet.getEmail());
                }
                if (objet.getDateNaissance() != null) {
                    existingEleve.setDateNaissance(objet.getDateNaissance());
                }
                if (objet.getSexe() != null) {
                    existingEleve.setSexe(objet.getSexe());
                }
                if (objet.getAdresse() != null) {
                    existingEleve.setAdresse(objet.getAdresse());
                }
                if (objet.getVille() != null) {
                    existingEleve.setVille(objet.getVille());
                }
                if (objet.getClasse() != null) {
                    existingEleve.setClasse(objet.getClasse());
                }

                Eleve updatedEleve = eleveService.create(existingEleve);
                EleveResponse response = eleveMapper.toDto(updatedEleve);

                return new ResponseEntity<>(RestResponse.response(
                        HttpStatus.OK,
                        response,
                        "Étudiant mis à jour avec succès"),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(RestResponse.response(
                        HttpStatus.NOT_FOUND,
                        null,
                        "Étudiant non trouvé"),
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Erreur lors de la mise à jour de l'étudiant: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> Delete(String id) {
        try {
            Eleve existingEleve = eleveService.findById(id);
            if (existingEleve != null) {
                boolean deleted = eleveService.delete(id);
                if (deleted) {
                    return new ResponseEntity<>(RestResponse.response(
                            HttpStatus.OK,
                            null,
                            "Étudiant supprimé avec succès"),
                            HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(RestResponse.response(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            null,
                            "Erreur lors de la suppression de l'étudiant"),
                            HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>(RestResponse.response(
                        HttpStatus.NOT_FOUND,
                        null,
                        "Étudiant non trouvé"),
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Erreur lors de la suppression de l'étudiant: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> Create(Eleve objet) {
        try {
            Eleve createdEleve = eleveService.create(objet);
            EleveResponse response = eleveMapper.toDto(createdEleve);
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.CREATED,
                    response,
                    "Étudiant créé avec succès"),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Erreur lors de la création de l'étudiant: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
