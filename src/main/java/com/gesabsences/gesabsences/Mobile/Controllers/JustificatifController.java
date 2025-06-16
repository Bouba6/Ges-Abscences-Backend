package com.gesabsences.gesabsences.Mobile.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gesabsences.gesabsences.Mobile.Dto.Request.JustifierRequest;
import com.gesabsences.gesabsences.config.Controller;
import com.gesabsences.gesabsences.data.Entities.Justification;

@RequestMapping("api/v1/mobile/justifier")
public interface JustificatifController extends Controller<Justification> {

    @GetMapping("/eleve/{id}")
    ResponseEntity<?> findByIdEleve(@PathVariable String id);

    @GetMapping("/cours/{id}")
    ResponseEntity<?> findByIdCours(@RequestParam String idCours);

    @PostMapping("")
    ResponseEntity<?> create(@RequestBody JustifierRequest objet);

}
