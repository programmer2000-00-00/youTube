package com.example.controller;

import com.example.dto.auth.AuthDTO;
import com.example.dto.RegistrDTO;
import com.example.enums.Language;
import com.example.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    private Logger log= LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO dto,
                                   @RequestHeader(name = "Accept-Language",defaultValue = "ENG") Language language){
        log.info("Authorization: {} " +dto);
        log.debug("Debug: {} " +dto);
        log.warn("Warning: {} " +dto);
        log.error("Error: {} " +dto);
        log.trace("trace: {} " +dto);
        AuthResponseDTO authResponseDTO = authService.login(dto,language);
        return ResponseEntity.ok(authResponseDTO);
    }
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrDTO registrDTO){
        log.info("Registration:{}"+registrDTO);

        String result=authService.registration(registrDTO);
        return ResponseEntity.ok(result);
    }
}
