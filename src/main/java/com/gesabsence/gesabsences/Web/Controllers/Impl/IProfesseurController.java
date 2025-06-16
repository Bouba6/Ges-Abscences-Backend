package com.gesabsences.gesabsences.Web.Controllers.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gesabsences.gesabsences.Web.Controllers.ProfesseurController;
import com.gesabsences.gesabsences.Web.Dto.Response.ProfesseurResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.Professeur;
import com.gesabsences.gesabsences.data.Services.ProfesseurService;
import com.gesabsences.gesabsences.Web.Mapper.ProfesseurMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IProfesseurController implements ProfesseurController {

    private final ProfesseurService professeurService;
    private final ProfesseurMapper professeurMapper;

    @Override
    public ResponseEntity<Map<String, Object>> SelectAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Professeur> Professeur = professeurService.findAll(pageable);
        Page<ProfesseurResponse> Professeurs = Professeur.map(professeurMapper::toDto);
        return new ResponseEntity<>(RestResponse.responsePaginate(
                HttpStatus.OK,
                Professeurs.getContent(),
                Professeurs.getNumber(),
                Professeurs.getTotalPages(),
                Professeurs.getTotalElements(),
                Professeurs.isFirst(),
                Professeurs.isLast(),
                "Professeur"),
                HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Map<String, Object>> SelectdById(String id) {

        return new ResponseEntity<>(RestResponse.response(HttpStatus.OK,
                professeurMapper.toDto(professeurService.findById(id)), "Professeur"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> Update(String id, Professeur objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Update'");
    }

    @Override
    public ResponseEntity<Map<String, Object>> Delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Delete'");
    }

}
