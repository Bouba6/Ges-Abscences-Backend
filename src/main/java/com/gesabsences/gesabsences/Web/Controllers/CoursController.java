package com.gesabsences.gesabsences.Web.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gesabsences.gesabsences.config.Controller;
import com.gesabsences.gesabsences.data.Entities.Cours;

import jakarta.websocket.server.PathParam;

@RequestMapping("api/v1/cours")
public interface CoursController extends Controller<Cours> {

        @GetMapping("/classe/{id}")
        ResponseEntity<?> findCours(@PathVariable String id,
                        @RequestParam String startDate,
                        @RequestParam String endDate);

        @GetMapping("/professeur/{id}")
        ResponseEntity<?> findByProfesseurId(@PathVariable String id,
                        @RequestParam String startDate,
                        @RequestParam String endDate);
}
