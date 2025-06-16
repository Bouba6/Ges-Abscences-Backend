package com.gesabsences.gesabsences.Mobile.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gesabsences.gesabsences.config.Controller;
import com.gesabsences.gesabsences.data.Entities.Eleve;

@RequestMapping("api/v1/mobile/eleves")
public interface EleveController extends Controller<Eleve> {

    @GetMapping("/user/{id}")
    ResponseEntity<?> findByUserId(@PathVariable String id);

}
