package com.gesabsences.gesabsences.Web.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gesabsences.gesabsences.Web.Dto.Request.JustificatifRequest;
import com.gesabsences.gesabsences.config.Controller;

import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.data.Entities.Justification;


@RequestMapping("api/v1/justificatif")
public interface JustificatifController extends Controller<Justification> {

    @PutMapping("/abscence/{id}")
    ResponseEntity<?> Update1(@PathVariable String id, @RequestBody JustificatifRequest objet);

    
}
