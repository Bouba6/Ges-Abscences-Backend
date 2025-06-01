package com.gesabsences.gesabsences.Web.Controllers.Impl;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.gesabsences.gesabsences.Web.Controllers.NiveauController;
import com.gesabsences.gesabsences.Web.Dto.Response.NiveauResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.Niveau;
import com.gesabsences.gesabsences.data.Services.NiveauService;
import com.gesabsences.gesabsences.Web.Mapper.NiveauMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class INiveauController implements NiveauController {

    @Autowired
    private NiveauService niveauService;

    @Autowired
    private NiveauMapper niveauMapper;

    @Override
    public ResponseEntity<Map<String, Object>> SelectAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Niveau> Niveaux = niveauService.findAll(pageable);
        Page<NiveauResponse> Niveau = Niveaux.map(niveauMapper::toDto);
        return new ResponseEntity<>(
                RestResponse.responsePaginate(HttpStatus.OK, Niveau.getContent(), Niveau.getNumber(),
                        Niveau.getTotalPages(), Niveau.getTotalElements(), Niveau.isFirst(), Niveau.isLast(), "Niveau"),
                HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Map<String, Object>> SelectdById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'SelectdById'");
    }

    @Override
    public ResponseEntity<Map<String, Object>> Update(String id, Niveau objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Update'");
    }

    @Override
    public ResponseEntity<Map<String, Object>> Delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Delete'");
    }

    @Override
    public ResponseEntity<Map<String, Object>> Create(Niveau objet) {
        try {
            Niveau createdNiveau = niveauService.create(objet);
            NiveauResponse response = niveauMapper.toDto(createdNiveau);
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.CREATED,
                    response,
                    "Niveau créé avec succès"),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Erreur lors de la création du niveau: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
