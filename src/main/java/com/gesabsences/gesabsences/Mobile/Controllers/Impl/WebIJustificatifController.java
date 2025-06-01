package com.gesabsences.gesabsences.Mobile.Controllers.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gesabsences.gesabsences.Mobile.Controllers.JustificatifController;
import com.gesabsences.gesabsences.Mobile.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.Mobile.Dto.Response.justificatifResponse;
import com.gesabsences.gesabsences.data.Entities.Abscence;
import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Entities.Justification;
import com.gesabsences.gesabsences.data.Enum.StatutJustification;
import com.gesabsences.gesabsences.data.Services.CoursService;
import com.gesabsences.gesabsences.data.Services.EleveService;
import com.gesabsences.gesabsences.data.Services.JustificatifService;
import com.gesabsences.gesabsences.Mobile.Dto.Mapper.MobJusticatifMapper;
import com.gesabsences.gesabsences.Mobile.Dto.Request.JustifierRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class WebIJustificatifController implements JustificatifController {
    private final JustificatifService justificatifService;
    private final MobJusticatifMapper justificatifMapper;
    private final EleveService eleveService;
    private final CoursService coursService;

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
    public ResponseEntity<Map<String, Object>> Update(String id, Justification objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Update'");
    }

    @Override
    public ResponseEntity<Map<String, Object>> Delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Delete'");
    }

    @Override
    public ResponseEntity<Map<String, Object>> findByIdEleve(@PathVariable String id) {
        List<justificatifResponse> response = justificatifMapper.toDto(justificatifService.findByEleve(id));
        return new ResponseEntity<>(RestResponse.response(HttpStatus.OK, response, "Justificatif"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findByIdCours(String idCours) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByIdCours'");
    }

    @Override
    public ResponseEntity<?> create(JustifierRequest justifierRequest) {
        Justification justification = justificatifMapper.toEntity(justifierRequest);
        return new ResponseEntity<>(justificatifService.create(justification), HttpStatus.OK);
    }

    // private String justificatif;

    // @DBRef
    // private Abscence abscence;

    // private StatutJustification statutJustification;

}
