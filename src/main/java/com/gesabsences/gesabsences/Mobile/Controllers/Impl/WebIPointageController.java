package com.gesabsences.gesabsences.Mobile.Controllers.Impl;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gesabsences.gesabsences.Mobile.Controllers.PointageController;
import com.gesabsences.gesabsences.Mobile.Dto.Mapper.MobPointageMapper;
import com.gesabsences.gesabsences.Mobile.Dto.Response.EleveAvecCoursResponse;
import com.gesabsences.gesabsences.Mobile.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.data.Entities.Pointage;
import com.gesabsences.gesabsences.data.Enum.TypeAbscence;
import com.gesabsences.gesabsences.data.Services.Impl.PointageService;
import com.gesabsences.gesabsences.Mobile.Dto.Response.PointageRespoonse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class WebIPointageController implements PointageController {

    private final PointageService pointageService;
    private final MobPointageMapper pointageMapper;

    @GetMapping("/elevesjour")
    public ResponseEntity<Map<String, Object>> getElevesDuJour() {
        List<EleveAvecCoursResponse> eleves = pointageService.getElevesAvecCoursAujourdhui();
        return new ResponseEntity<>(RestResponse.response(HttpStatus.OK, eleves, "Eleves"), HttpStatus.OK);
    }

    @GetMapping("/eleves/{date}")
    public ResponseEntity<List<EleveAvecCoursResponse>> getElevesParDate(@PathVariable String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            List<EleveAvecCoursResponse> eleves = pointageService.getElevesAvecCoursParDate(localDate);
            return ResponseEntity.ok(eleves);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/cours-proche/{eleveId}")
    public ResponseEntity<CoursProche> getCoursProche(
            @PathVariable String eleveId,
            @RequestParam(required = false) String heureArrivee) {

        Date heure;

        if (heureArrivee != null) {
            // Parse la string en ZonedDateTime
            ZonedDateTime zdt = ZonedDateTime.parse(heureArrivee, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            // Convertis en Date
            heure = Date.from(zdt.toInstant());
        } else {
            heure = new Date();
        }
        Optional<Cours> cours = pointageService.getCoursLePlusProche(eleveId, heure);

        if (cours.isPresent()) {
            TypeAbscence statut = pointageService.determinerStatutPresence(cours.get(), heure);
            CoursProche response = new CoursProche(cours.get(), statut, heure);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/valider-presence")
    public ResponseEntity<String> validerPresence(@RequestBody PointageRequest request) {
        try {
            System.out.println("===== Début de la validation de présence =====");
            System.out.println("Élève ID : " + request.getEleveId());
            System.out.println("Heure d'arrivée (UTC) : " + request.getHeureArrivee());

            // Récupérer le cours le plus proche
            Optional<Cours> cours = pointageService.getCoursLePlusProche(
                    request.getEleveId(),
                    request.getHeureArrivee());

            if (cours.isPresent()) {
                System.out.println("Cours trouvé : " + cours.get().getId());
                System.out.println("Heure début cours : " + cours.get().getHeureDebut());
                System.out.println("Heure fin cours : " + cours.get().getHeureFin());

                TypeAbscence statut = pointageService.determinerStatutPresence(
                        cours.get(),
                        request.getHeureArrivee());

                System.out.println("Statut déterminé : " + statut);

                // Sauvegarder la présence / absence
                pointageService.validerPresenceEleve(request.getEleveId(), request.getHeureArrivee());

                com.gesabsences.gesabsences.Mobile.Dto.Request.PointageRequest pointageRequest = new com.gesabsences.gesabsences.Mobile.Dto.Request.PointageRequest();
                pointageRequest.setIdEleve(request.getEleveId());
                DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneOffset.UTC);
                String heureFormatee = formatter.format(request.getHeureArrivee().toInstant());
                pointageRequest.setHeurePointage(heureFormatee);
                pointageRequest.setIdCours(cours.get().getId());
                pointageRequest.setIdVigile(request.idVigile);

                pointageService.save(pointageRequest);
                // pointageService.
                System.out.println("Présence validée pour l'élève.");

                return ResponseEntity.ok("Présence validée avec statut : " + statut);
            }

            System.out.println("Aucun cours trouvé pour l'élève.");
            return ResponseEntity.badRequest().body("Aucun cours trouvé pour cet élève");

        } catch (Exception e) {
            System.out.println("Erreur lors de la validation de présence : " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erreur lors de la validation : " + e.getMessage());
        }
    }

    public static class CoursProche {
        private Cours cours;
        private TypeAbscence statut;
        private Date heureArrivee;

        public CoursProche(Cours cours, TypeAbscence statut, Date heureArrivee) {
            this.cours = cours;
            this.statut = statut;
            this.heureArrivee = heureArrivee;
        }

        // Getters
        public Cours getCours() {
            return cours;
        }

        public TypeAbscence getStatut() {
            return statut;
        }

        public Date getHeureArrivee() {
            return heureArrivee;
        }
    }

    public static class PointageRequest {
        private String eleveId;
        private Date heureArrivee;
        private String idVigile;

        // Constructors
        public PointageRequest() {
        }

        public PointageRequest(String eleveId, Date heureArrivee, String idVigile) {
            this.eleveId = eleveId;
            this.heureArrivee = heureArrivee;
            this.idVigile = idVigile;
        }

        // Getters et Setters
        public String getEleveId() {
            return eleveId;
        }

        public String getIdVigile() {
            return idVigile;
        }

        public void setEleveId(String eleveId) {
            this.eleveId = eleveId;
        }

        public Date getHeureArrivee() {
            return heureArrivee;
        }

        public void setIdVigile(String idVigile) {
            this.idVigile = idVigile;
        }

        public void setHeureArrivee(Date heureArrivee) {
            this.heureArrivee = heureArrivee;
        }
    }

    @Override
    public ResponseEntity<?> findByVigileId(String id) {
        List<Pointage> pointages = pointageService.getPointagesParVigile(id);
        List<PointageRespoonse> pointageRespoonse = pointages.stream().map(pointageMapper::toDto).toList();
        return new ResponseEntity<>(RestResponse.response(HttpStatus.OK, pointageRespoonse, "Pointages"), HttpStatus.OK);
    }

}
