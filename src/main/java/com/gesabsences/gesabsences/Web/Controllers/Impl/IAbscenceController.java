package com.gesabsences.gesabsences.Web.Controllers.Impl;

import com.gesabsences.gesabsences.data.Entities.Abscence;
import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Services.AbscenceService;
import com.gesabsences.gesabsences.data.Services.CoursService;
import com.gesabsences.gesabsences.data.Services.EleveService;
import com.gesabsences.gesabsences.data.Enum.StatutAbscence;
import com.gesabsences.gesabsences.Web.Dto.Request.AbscenceRequest;
import com.gesabsences.gesabsences.Web.Mapper.AbscenceMapper;
// import com.gesabsences.gesabsences.Web.Response.RestResponse; // Removed because the class does not exist

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/absences")
public class IAbscenceController {

    @Autowired
    private AbscenceService abscenceService;

    @Autowired
    private CoursService coursService;

    @Autowired
    private EleveService eleveService;

    @Autowired
    private AbscenceMapper abscenceMapper;

    @GetMapping("/group-by")
    public ResponseEntity<Map<String, Object>> groupAbsencesByDayAndMatiere(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        List<Abscence> absences = abscenceService.findAbsencesBetweenDates(start, end);

        Map<LocalDate, Map<String, List<Abscence>>> grouped = absences.stream()
            .collect(Collectors.groupingBy(
                a -> a.getCours().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                Collectors.groupingBy(a -> a.getCours().getModule().getNom())
            ));

        return new ResponseEntity<>(Map.of("data", grouped, "message", "Absences groupées"), HttpStatus.OK);
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getAbsenceStats(@RequestParam String date) {
        LocalDate targetDate = LocalDate.parse(date);
        List<Abscence> absences = abscenceService.findAbsencesByDate(targetDate);

        long total = absences.size();
        long justifies = absences.stream().filter(a -> a.getStatutAbscence() == StatutAbscence.JUSTIFIE).count();
        long nonJustifies = absences.stream().filter(a -> a.getStatutAbscence() == StatutAbscence.NON_JUSTIFIE).count();

        Map<String, Object> stats = Map.of(
            "total", total,
            "justifies", justifies,
            "nonJustifies", nonJustifies
        );
        return new ResponseEntity<>(Map.of("data", stats, "message", "Statistiques absences"), HttpStatus.OK);
    }

    // Les méthodes suivantes supposent que tu implémentes une interface, sinon retire les @Override

    public ResponseEntity<Map<String, Object>> SelectAll(int page, int size) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'SelectAll'");
    }

    public ResponseEntity<Map<String, Object>> SelectdById(String id) {
        return new ResponseEntity<>(
                Map.of(
                    "status", HttpStatus.OK.value(),
                    "data", AbscenceMapper.toDto(abscenceService.findById(id)),
                    "message", "Abscence"
                ),
                HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> Update(String id, Abscence objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Update'");
    }

    public ResponseEntity<Map<String, Object>> Delete(String id) {
        abscenceService.delete(id);
        return ResponseEntity.ok().build();
    }

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
}
