package com.gesabsences.gesabsences.data.Services;

import com.gesabsences.gesabsences.config.Service;
import com.gesabsences.gesabsences.data.Entities.Vigile;

public interface VigileService  extends Service<Vigile>   {
    
    Vigile findByLoginAndPassword(String login, String password);


   
}
