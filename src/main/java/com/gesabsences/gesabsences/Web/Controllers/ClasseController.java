package com.gesabsences.gesabsences.Web.Controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gesabsences.gesabsences.config.Controller;
import com.gesabsences.gesabsences.data.Entities.Classe;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequestMapping("api/v1/classes")
public interface ClasseController extends Controller<Classe> {

        @GetMapping("/detail/{id}")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Donnee trouvé"),
                        @ApiResponse(responseCode = "404", description = "Donnee non trouvé")

        })
        ResponseEntity<Map<String, Object>> SelectdById1(@PathVariable String id,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "3") int size);

        @GetMapping("/professeur/{id}")
        ResponseEntity<?> findByProfesseurId(@PathVariable String id);

        @GetMapping("/classe/{nomClasse}")
        ResponseEntity<?> findByNomClasse(@PathVariable String nomClasse, @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "3") int size);

}
