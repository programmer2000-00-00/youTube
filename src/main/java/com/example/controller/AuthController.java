package com.example.controller;

import com.example.dto.RegistrDTO;
import com.example.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    private Logger log= LoggerFactory.getLogger(AuthController.class);
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrDTO registrDTO){
        log.info("Registration:{}"+registrDTO);

        String result=authService.registration(registrDTO);
        return ResponseEntity.ok(result);
    }
}
