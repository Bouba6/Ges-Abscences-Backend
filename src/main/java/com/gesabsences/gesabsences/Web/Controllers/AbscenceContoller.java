package com.gesabsences.gesabsences.Web.Controllers;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gesabsences.gesabsences.data.Entities.Abscence;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

import com.gesabsences.gesabsences.Web.Dto.Request.AbscenceRequest;
import com.gesabsences.gesabsences.config.Controller;

@RequestMapping("api/v1/abscences")
public interface AbscenceContoller extends Controller<Abscence> {

    @PostMapping("")
    ResponseEntity<Map<String, Object>> AjouterAbscence(@RequestBody AbscenceRequest abscence);

    @GetMapping("/cours/{id}")
    ResponseEntity<Map<String, Object>> findAbsenceInCours(@PathVariable String id);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Map<String, Object>> Delete(@PathVariable String id);

    @GetMapping("/details/{id}/{coursId}")
    ResponseEntity<Map<String, Object>> getAbsenceDetails(@PathVariable String id, @PathVariable String coursId);

    @PutMapping("/update/{id}")
    ResponseEntity<Map<String, Object>> Update(@PathVariable String id, @RequestBody AbscenceRequest objet);

    @GetMapping("/update/{id}")
    ResponseEntity<Map<String, Object>> getJustificatifInAbscence(@PathVariable String id);

     @GetMapping("/filter")
        @ApiResponse(responseCode = "200")
        ResponseEntity<Map<String, Object>> SelectAll(
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "3") int size,
                        @RequestParam(required = false) String StatutAbscence );


    

}
