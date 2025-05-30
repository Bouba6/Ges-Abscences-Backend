package com.gesabsences.gesabsences.Web.Controllers.Impl;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gesabsences.gesabsences.Web.Controllers.JustificatifController;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.justificatifResponse;
import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Entities.Justitfication;
import com.gesabsences.gesabsences.data.Services.JustificatifService;
import com.gesabsences.gesabsences.utils.Mapper.JusticatifMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IJustificatifController implements JustificatifController {
    private final JustificatifService justificatifService;
    private final JusticatifMapper justificatifMapper;

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
        Page<Justitfication> justificatifs = justificatifService.findAll(pageable);
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
    public ResponseEntity<Map<String, Object>> Update(String id, Justitfication objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Update'");
    }

    @Override
    public ResponseEntity<Map<String, Object>> Delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Delete'");
    }

}
