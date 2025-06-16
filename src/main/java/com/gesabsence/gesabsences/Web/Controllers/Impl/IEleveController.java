package com.gesabsences.gesabsences.Web.Controllers.Impl;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gesabsences.gesabsences.Web.Controllers.EleveController;
import com.gesabsences.gesabsences.Web.Dto.Response.EleveResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.Eleve;
import com.gesabsences.gesabsences.data.Services.EleveService;
import com.gesabsences.gesabsences.Web.Mapper.EleveMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IEleveController implements EleveController {

    private final EleveService eleveService;
    private final EleveMapper eleveMapper;

    @Override
    public ResponseEntity<Map<String, Object>> SelectAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Eleve> Eleves = eleveService.findAll(pageable);
        Page<EleveResponse> Eleve = Eleves.map(eleveMapper::toDto);
        return new ResponseEntity<>(RestResponse.responsePaginate(
                HttpStatus.OK,
                Eleve.getContent(),
                Eleve.getNumber(),
                Eleve.getTotalPages(),
                Eleve.getTotalElements(),
                Eleve.isFirst(),
                Eleve.isLast(),
                "Eleve"),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> SelectdById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'SelectdById'");
    }

    @Override
    public ResponseEntity<Map<String, Object>> Update(String id, Eleve objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Update'");
    }

    @Override
    public ResponseEntity<Map<String, Object>> Delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Delete'");
    }

}
