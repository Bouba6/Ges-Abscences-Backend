package com.gesabsences.gesabsences.Mobile.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;

import com.gesabsences.gesabsences.config.Controller;

import com.gesabsences.gesabsences.data.Entities.Cours;
import com.gesabsences.gesabsences.data.Entities.Justitfication;

@RequestMapping("api/v1/mobile/justifier")
public interface JustificatifController extends Controller<Justitfication> {

}
