package com.gesabsences.gesabsences.Mobile.Controllers.Impl;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gesabsences.gesabsences.Mobile.Controllers.NiveauController;
import com.gesabsences.gesabsences.Mobile.Dto.Response.NiveauResponse;
import com.gesabsences.gesabsences.Mobile.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.Niveau;
import com.gesabsences.gesabsences.data.Services.NiveauService;
import com.gesabsences.gesabsences.Mobile.Dto.Mapper.MobNiveauMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor

public class WebINiveauController implements NiveauController {

    private final NiveauService niveauService;
    private final MobNiveauMapper niveauMapper;

    @Override
    public ResponseEntity<Map<String, Object>> SelectAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Niveau> Niveaux = niveauService.findAll(pageable);
        Page<NiveauResponse> Niveau = Niveaux.map(niveauMapper::toDto);
        return new ResponseEntity<>(
                RestResponse.responsePaginate(HttpStatus.OK, Niveau.getContent(), Niveau.getNumber(),
                        Niveau.getTotalPages(), Niveau.getTotalElements(), Niveau.isFirst(), Niveau.isLast(), "Niveau"),
                HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Map<String, Object>> SelectdById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'SelectdById'");
    }

    @Override
    public ResponseEntity<Map<String, Object>> Update(String id, Niveau objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Update'");
    }

    @Override
    public ResponseEntity<Map<String, Object>> Delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Delete'");
    }

}
