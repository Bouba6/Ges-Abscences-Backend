package com.gesabsences.gesabsences.Web.Controllers.Impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gesabsences.gesabsences.Web.Controllers.JustificatifController;
import com.gesabsences.gesabsences.Web.Dto.Request.JustificatifRequest;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.justificatifResponse;
import com.gesabsences.gesabsences.data.Entities.Justification;
import com.gesabsences.gesabsences.data.Enum.StatutJustification;
import com.gesabsences.gesabsences.data.Repositories.JustificatifRepository;
import com.gesabsences.gesabsences.data.Services.JustificatifService;
import com.gesabsences.gesabsences.Web.Mapper.JusticatifMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/justificatifs")
public class IJustificatifController implements JustificatifController {
    private final JustificatifService justificatifService;
    private final JusticatifMapper justificatifMapper;
    private final JustificatifRepository justificatifRepository;

    /************* ✨ Windsurf Command ⭐ *************/
    /**
     * @param page the page number to request (default is 0)
     * @param size the number of records per page (default is 3)
     * @return a ResponseEntity containing a Map with the following keys:
     *         totalElements: the total number of records
     *         totalPages: the total number of pages
     *         numberOfElements: the number of records in the page
     *         content: the list of Justificatif objects
     */
    /******* 0699d96c-7279-41c3-81fb-567e3ff7bc61 *******/

    @Override
    public ResponseEntity<Map<String, Object>> SelectAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Justification> justificatifs = justificatifService.findAll(pageable);
        Page<justificatifResponse> justificatif = justificatifs.map(justificatifMapper::toDto);

        return new ResponseEntity<>(RestResponse.responsePaginate(HttpStatus.OK, justificatif.getContent(),
                justificatif.getNumber(), justificatif.getTotalPages(), justificatif.getTotalElements(),
                justificatif.isFirst(), justificatif.isLast(), "Justificatif"), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Map<String, Object>> SelectdById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'SelectdById'");
    }

    @Override
    public ResponseEntity<Map<String, Object>> Update1(String id, JustificatifRequest objet) {
        Map<String, Object> response = new HashMap<>();

        try {
            Justification justification = justificatifService.findById(id);
            if (justification != null) {
                String statut = objet.getStatutJustificatif();

                // Vérification si le statut est null ou vide
                if (statut == null || statut.trim().isEmpty()) {
                    response.put("message", "Le statut de justification est requis");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }

                try {
                    // Convertit proprement la string en valeur de l'enum
                    StatutJustification statutEnum = StatutJustification.valueOf(statut.toUpperCase().trim());
                    justification.setStatutJustification(statutEnum);
                } catch (IllegalArgumentException e) {
                    response.put("message", "Statut de justification invalide : " + statut);
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }

                // Sauvegarde avec la bonne méthode
                Justification updatedJustification = justificatifService.create(justification); // ✅ save() au lieu de
                                                                                              // create()

                // Réponse
                response.put("data", updatedJustification);
                response.put("message", "Justificatif mis à jour avec succès");
                return new ResponseEntity<>(response, HttpStatus.OK);

            } else {
                response.put("message", "Justificatif introuvable");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("message", "Erreur lors de la mise à jour: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> Delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Delete'");
    }

    @Override
    public ResponseEntity<Map<String, Object>> Update(String id, Justification objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Update'");
    }

}
