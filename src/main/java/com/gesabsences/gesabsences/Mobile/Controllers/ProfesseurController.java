package com.gesabsences.gesabsences.Mobile.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;

import com.gesabsences.gesabsences.config.Controller;
import com.gesabsences.gesabsences.data.Entities.Professeur;



@RequestMapping("api/v1/mobile/professeurs")
public interface ProfesseurController extends Controller<Professeur> {

}
