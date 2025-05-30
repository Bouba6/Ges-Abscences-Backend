package com.gesabsences.gesabsences.Mobile.Controllers.Impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.gesabsences.gesabsences.Mobile.Controllers.CoursController;
import com.gesabsences.gesabsences.Mobile.Dto.Response.CoursResponse;
import com.gesabsences.gesabsences.Mobile.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Entities.Professeur;
import com.gesabsences.gesabsences.data.Services.ClasseService;
import com.gesabsences.gesabsences.data.Services.CoursService;
import com.gesabsences.gesabsences.data.Services.EleveService;
import com.gesabsences.gesabsences.data.Services.ProfesseurService;
import com.gesabsences.gesabsences.Mobile.Dto.Mapper.MobCoursMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class WebICoursController implements CoursController {

    private final CoursService coursService;
    private final ClasseService classeService;
    private final EleveService eleveService;
    private final MobCoursMapper coursMapper;
    private final ProfesseurService professeurService;

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
    public ResponseEntity<?> findCoursForEleve(String id) {
        Eleve eleve = eleveService.findById(id);
        // List<Cours> cours = coursService.findCoursForEleve(eleve);
        // var response = cours.stream().map(coursMapper::toDto).toList();

        List<Cours> cours = coursService.findCoursForEleve(eleve);

        List<CoursResponse> Courses = cours.stream().map(coursMapper::toDto).toList();
        return new ResponseEntity<>(RestResponse.response(HttpStatus.OK, Courses, "Cours"), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> findCoursInDate(String date) {
        Instant instant = Instant.parse(date); // Si c’est bien ISO 8601
        Date parsedDate = Date.from(instant);
        List<Cours> cours = coursService.findByDate(parsedDate);
        var response = cours.stream().map(coursMapper::toDto).toList();
        return new ResponseEntity<>(RestResponse.response(HttpStatus.OK, response, "Cours"), HttpStatus.OK);
    }

}
