package com.gesabsences.gesabsences.Mobile.Controllers.Impl;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.gesabsences.gesabsences.Mobile.Controllers.VigileController;
import com.gesabsences.gesabsences.Mobile.Dto.Request.VigileRequest;
import com.gesabsences.gesabsences.data.Entities.Vigile;
import com.gesabsences.gesabsences.data.Services.VigileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class WebIVigileController implements VigileController {

    private final VigileService vigileService;

    @Override
    public ResponseEntity<Map<String, Object>> SelectAll(int page, int size) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'SelectAll'");
    }

    @Override
    public ResponseEntity<Map<String, Object>> SelectdById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'SelectdById'");
    }

    @Override
    public ResponseEntity<Map<String, Object>> Update(String id, Vigile objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Update'");
    }

    @Override
    public ResponseEntity<Map<String, Object>> Delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Delete'");
    }

    @Override
    public ResponseEntity<?> findByLogin(VigileRequest vigile) {

        String Login = vigile.getLogin();
        String Password = vigile.getPassword();

        Vigile Vigile = vigileService.findByLoginAndPassword(Login, Password);

        if (Vigile != null) {
            return ResponseEntity.ok(Vigile);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
