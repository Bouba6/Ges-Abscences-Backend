package com.gesabsences.gesabsences.Mobile.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("api/v1/mobile/pointage")
public interface PointageController {

    @GetMapping("/byVigileId/{id}")
    ResponseEntity<?> findByVigileId(@PathVariable String id);
}
