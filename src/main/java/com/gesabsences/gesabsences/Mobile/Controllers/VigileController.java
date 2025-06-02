package com.gesabsences.gesabsences.Mobile.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gesabsences.gesabsences.Mobile.Dto.Request.VigileRequest;
import com.gesabsences.gesabsences.config.Controller;
import com.gesabsences.gesabsences.data.Entities.Vigile;

@RequestMapping("api/v1/mobile/vigiles")
public interface VigileController extends Controller<Vigile> {

    @GetMapping("/findByLogin")
    ResponseEntity<?> findByLogin(@RequestBody VigileRequest vigile);

    @GetMapping("/user/{id}")
    ResponseEntity<?> findByUserId(@PathVariable String id);

}
