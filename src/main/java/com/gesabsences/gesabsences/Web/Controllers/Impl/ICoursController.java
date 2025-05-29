package com.gesabsences.gesabsences.Web.Controllers.Impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.gesabsences.gesabsences.Web.Controllers.CoursController;
import com.gesabsences.gesabsences.Web.Dto.Response.CoursResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Entities.Professeur;
import com.gesabsences.gesabsences.data.Repositories.EleveRepository;
import com.gesabsences.gesabsences.data.Services.ClasseService;
import com.gesabsences.gesabsences.data.Services.CoursService;
import com.gesabsences.gesabsences.data.Services.EleveService;
import com.gesabsences.gesabsences.data.Services.ProfesseurService;
import com.gesabsences.gesabsences.Web.Mapper.CoursMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ICoursController implements CoursController {

    private final CoursService coursService;
    private final ClasseService classeService;
    private final CoursMapper coursMapper;
    private final ProfesseurService professeurService;
    private final EleveRepository eleveRepository;

    @Override
    public ResponseEntity<Map<String, Object>> SelectAll(int page, int size) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'SelectAll'");
    }

    @Override
    public ResponseEntity<Map<String, Object>> SelectdById(String id) {

        return new ResponseEntity<>(RestResponse.response(HttpStatus.OK,
                coursMapper.toDto(coursService.findById(id)), "Cours"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> Update(String id, Cours objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Update'");
    }

    @Override
    public ResponseEntity<Map<String, Object>> Delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Delete'");
    }

    @Override
    public ResponseEntity<?> findCours(String id, String startDate, String endDate) {
        var classes = classeService.findById(id);
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        ZoneId zoneId = ZoneId.systemDefault();

        Date startDateConverted = Date.from(start.atStartOfDay(zoneId).toInstant());
        Date endDateConverted = Date.from(end.plusDays(1).atStartOfDay(zoneId).toInstant()); // pour inclure tout le
                                                                                             // jour de fin

        List<Cours> cours = coursService.findByClasseAndDateBetween(classes, startDateConverted, endDateConverted);

        // List<Cours> cours = coursService.findByClasseAndDateBetween(classes,
        // LocalDate.parse(startDate),
        // LocalDate.parse(endDate));
        var response = cours.stream().map(coursMapper::toDto).toList();
        return new ResponseEntity<>(RestResponse.response(HttpStatus.OK, response, "Cours"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findByProfesseurId(String id, String startDate, String endDate) {
        Professeur professeur = professeurService.findById(id);
        if (professeur == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professeur non rencontré");
        }

        List<Cours> cours = coursService.findByProfesseurAndDateBetween(professeur, LocalDate.parse(startDate),
                LocalDate.parse(endDate));
        var response = cours.stream().map(coursMapper::toDto).toList();
        return new ResponseEntity<>(RestResponse.response(HttpStatus.OK, response, "Cours"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getCoursForEleveToday(String eleveId) {
        try {
            Eleve eleve = eleveRepository.findById(eleveId).orElse(null);
            if (eleve == null) {
                return ResponseEntity.notFound().build();
            }

            List<Cours> cours = coursService.getCoursForEleveToday(eleve);

            List<CoursResponse> Courses = cours.stream().map(coursMapper::toDto).toList();
            return new ResponseEntity<>(RestResponse.response(HttpStatus.OK, Courses, "Cours"), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
