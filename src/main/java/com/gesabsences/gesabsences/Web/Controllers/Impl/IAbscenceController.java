package com.gesabsences.gesabsences.Web.Controllers.Impl;

import java.util.HashMap;
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

import com.gesabsences.gesabsences.Web.Controllers.AbscenceContoller;
import com.gesabsences.gesabsences.Web.Dto.Request.AbscenceRequest;
import com.gesabsences.gesabsences.Web.Dto.Response.AbsenceResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.Abscence;
import com.gesabsences.gesabsences.data.Entities.Classe;
import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Entities.Justification;
import com.gesabsences.gesabsences.data.Services.AbscenceService;
import com.gesabsences.gesabsences.data.Services.CoursService;
import com.gesabsences.gesabsences.data.Services.EleveService;
import com.gesabsences.gesabsences.Web.Mapper.AbscenceMapper;
import com.gesabsences.gesabsences.Web.Mapper.JusticatifMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IAbscenceController implements AbscenceContoller {

    private final AbscenceService abscenceService;
    private final CoursService coursService;
    private final EleveService eleveService;
    private final AbscenceMapper abscenceMapper;
    private final JusticatifMapper justicatifMapper;

    @Override
    public ResponseEntity<Map<String, Object>> SelectAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Abscence> abscences = abscenceService.findAll(pageable);

        Page<AbsenceResponse> abscenceResponse = abscences.map(abscenceMapper::toDto);

        return new ResponseEntity<>(RestResponse.responsePaginate(HttpStatus.OK, abscenceResponse.getContent(),
                abscenceResponse.getNumber(), abscenceResponse.getTotalPages(), abscenceResponse.getTotalElements(),
                abscenceResponse.isFirst(), abscenceResponse.isLast(), "Abscence"), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Map<String, Object>> SelectdById(String id) {
        return new ResponseEntity<>(
                RestResponse.response(HttpStatus.OK, abscenceMapper.toDto(abscenceService.findById(id)), "Abscence"),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> Update(String id, Abscence objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Update'");
    }

    @Override
    public ResponseEntity<Map<String, Object>> Delete(String id) {

        abscenceService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Map<String, Object>> AjouterAbscence(AbscenceRequest abscence) {

        Cours cours = coursService.findById(abscence.getCoursId());
        Eleve eleve = eleveService.findById(abscence.getEleveId());
        abscence.setCoursId(cours.getId());
        abscence.setEleveId(eleve.getId());

        Abscence abscence1 = abscenceMapper.toEntity(abscence);
        abscence1.setEleve(eleve);
        abscence1.setCours(cours);
        abscenceService.create(abscence1);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAbsenceInCours(String id) {

        Cours cours = coursService.findById(id);
        List<Abscence> abscences = abscenceService.findByCours(cours);
        return new ResponseEntity<>(
                RestResponse.response(HttpStatus.OK, abscences.stream().map(abscenceMapper::toDto), "Abscence"),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAbsenceDetails(String id, String coursId) {

        Abscence abscence = abscenceService.getAbsenceDetails(id, coursId);
        return new ResponseEntity<>(
                RestResponse.response(HttpStatus.OK, abscenceMapper.toDto(abscence), "Abscence"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> Update(String id, AbscenceRequest objet) {

        Cours cours = coursService.findById(objet.getCoursId());
        Eleve eleve = eleveService.findById(objet.getEleveId());
        objet.setCoursId(cours.getId());
        objet.setEleveId(eleve.getId());

        Abscence abscence1 = abscenceMapper.toEntity(objet);
        abscence1.setEleve(eleve);
        abscence1.setCours(cours);
        abscenceService.updateAbsence(id, abscence1);
        return new ResponseEntity<>(RestResponse.response(HttpStatus.OK, abscenceMapper.toDto(abscence1), "Abscence"),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getJustificatifInAbscence(String id) {

        Abscence abscence = abscenceService.findById(id);
        Justification justification=abscence.getJustificatif();
        ;
        return new ResponseEntity<>(
                RestResponse.response(HttpStatus.OK, justicatifMapper.toDto(justification), "Justificatif"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> SelectAll(int page, int size, String statutAbscence) {
        Pageable pageable = PageRequest.of(page, size);

    Page<Abscence> abscences;

    if (statutAbscence != null && !statutAbscence.isEmpty()) {
        abscences = abscenceService.findByStatutAbscence(statutAbscence, pageable);
    } else {
        abscences = abscenceService.findAll(pageable);
    }

    Page<AbsenceResponse> abscenceResponse = abscences.map(abscenceMapper::toDto);

    return new ResponseEntity<>(RestResponse.responsePaginate(
            HttpStatus.OK,
            abscenceResponse.getContent(),
            abscenceResponse.getNumber(),
            abscenceResponse.getTotalPages(),
            abscenceResponse.getTotalElements(),
            abscenceResponse.isFirst(),
            abscenceResponse.isLast(),
            "Abscence"), HttpStatus.OK);
    }

}
