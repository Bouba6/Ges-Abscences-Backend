
ackage com.gesabsences.gesabsences.Web.Controllers.Impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gesabsences.gesabsences.Web.Controllers.ProfesseurClasseController;
import com.gesabsences.gesabsences.Web.Dto.Response.ProfesseurClasseResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.RestResponse;
import com.gesabsences.gesabsences.data.Entities.ProfesseurClasse;
import com.gesabsences.gesabsences.data.Services.ProfesseurClasseService;

import com.gesabsences.gesabsences.Web.Mapper.ProfesseurClasseMapper;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IProfesseurClasseController implements ProfesseurClasseController {

    @Autowired
    private ProfesseurClasseService professeurClasseService;

    @Autowired
    private ProfesseurClasseMapper professeurClasseMapper;

    @Override
    public ResponseEntity<Map<String, Object>> Create(ProfesseurClasse objet) {
        try {
            ProfesseurClasse createdProfesseurClasse = professeurClasseService.create(objet);
            ProfesseurClasseResponse response = professeurClasseMapper.toDto(createdProfesseurClasse);
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.CREATED,
                    response,
                    "Association professeur-classe créée avec succès"),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(RestResponse.response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "Erreur lors de la création de l'association professeur-classe: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ... existing code ...
}
