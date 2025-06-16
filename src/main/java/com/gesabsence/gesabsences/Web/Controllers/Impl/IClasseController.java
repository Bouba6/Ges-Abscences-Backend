package com.gesabsences.gesabsences.Web.Controllers.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gesabsences.gesabsences.Web.Controllers.ClasseController;
import com.gesabsences.gesabsences.Web.Dto.Response.ClasseDetailResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.ClasseResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.EleveResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.Classe;
import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Entities.Professeur;
import com.gesabsences.gesabsences.data.Services.ClasseService;
import com.gesabsences.gesabsences.data.Services.EleveService;
import com.gesabsences.gesabsences.data.Services.ProfesseurClasseService;
import com.gesabsences.gesabsences.data.Services.ProfesseurService;
import com.gesabsences.gesabsences.Web.Mapper.ClasseMapper;
import com.gesabsences.gesabsences.Web.Mapper.EleveMapper;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IClasseController implements ClasseController {

    private final ClasseService classeService;
    private final ClasseMapper classeMapper;
    private final EleveMapper eleveMapper;
    private final EleveService eleveService;
    private final ProfesseurService professeurService;
    private final ProfesseurClasseService professeurClasseService;

    @Override
    public ResponseEntity<Map<String, Object>> SelectAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Classe> classes = classeService.findAll(pageable);

        Page<ClasseResponse> classesResponse = classes.map(classeMapper::toDto);

        return new ResponseEntity<>(
                RestResponse.responsePaginate(
                        HttpStatus.OK,
                        classesResponse.getContent(),
                        classesResponse.getNumber(),
                        classesResponse.getTotalPages(),
                        classesResponse.getTotalElements(),
                        classesResponse.isFirst(),
                        classesResponse.isLast(),
                        "Classe"),
                HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Map<String, Object>> SelectdById1(String id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        var classe = classeService.findById(id);
        Pageable pageable = PageRequest.of(page, size);
        Page<Eleve> eleves = eleveService.selectByClasse(pageable, classe);
        ClasseResponse classeResponse = classeMapper.toDto(classe);
        Page<EleveResponse> eleveResponses = eleves.map(eleveMapper::toDto);
        ClasseDetailResponse classeDetailResponse = new ClasseDetailResponse();
        classeDetailResponse.setClasse(classeResponse);
        classeDetailResponse.setEtudiants(eleveResponses.getContent());
        Map<String, Object> paginationInfo = new HashMap<>();
        paginationInfo.put("currentPage", eleveResponses.getNumber());
        paginationInfo.put("totalPages", eleveResponses.getTotalPages());
        paginationInfo.put("totalItems", eleveResponses.getTotalElements());
        paginationInfo.put("first", eleveResponses.isFirst());
        paginationInfo.put("last", eleveResponses.isLast());
        paginationInfo.put("type", "Eleve");

        Map<String, Object> response = new HashMap<>();
        response.put("classe", classeDetailResponse);
        response.put("paginationInfo", paginationInfo);

        return new ResponseEntity<>(
                RestResponse.response(HttpStatus.OK,
                        response, "Classe"),
                HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Map<String, Object>> SelectdById(String id) {
        var classe = classeService.findById(id);
        return new ResponseEntity<>(RestResponse.response(HttpStatus.OK, classeMapper.toDto(classe), "Classe"),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> Update(String id, Classe objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Update'");
    }

    @Override
    public ResponseEntity<Map<String, Object>> Delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Delete'");
    }

    @Override
    public ResponseEntity<?> findByProfesseurId(String id) {
        Professeur professeur = professeurService.findById(id);
        if (professeur == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professeur non trouvé");
        }

        List<Classe> classe = professeurClasseService.findClassesByProfesseur(professeur);
        List<ClasseResponse> classes = classe.stream().map(classeMapper::toDto).toList();
        return ResponseEntity.ok(RestResponse.response(HttpStatus.OK, classes, "Classe"));
    }

    @Override
    public ResponseEntity<?> findByNomClasse(String nomClasse, int page, int size) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByNomClasse'");
    }

}
