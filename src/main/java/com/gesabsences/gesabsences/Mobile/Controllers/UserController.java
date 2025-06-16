package com.gesabsences.gesabsences.Mobile.Controllers;

import com.gesabsences.gesabsences.config.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gesabsences.gesabsences.data.Entities.User;

@RequestMapping("api/v1/mobile/user")
public interface UserController extends Controller<User> {


    @GetMapping("/SelectAll1")
    ResponseEntity<?> SelectAll1();

}
