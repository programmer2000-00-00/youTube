package com.example.controller;

import com.example.dto.ProfileDTO;
import com.example.dto.auth.AuthDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.mapper.UpdateProfileNameAndEmail;
import com.example.service.ProfileService;
import com.example.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PutMapping("/example")
    public String simple() {
        return "ok";
    }

    @GetMapping("/update/verification/email/{jtwToken}")
    public ResponseEntity<AuthDTO> emailVerification(@PathVariable("jtwToken") String jwt) {
        AuthDTO edit = profileService.edit(jwt);
        return ResponseEntity.ok(edit);
    }
    @PutMapping("/update/verification/email/success")
    public String emailVerificationEdit(@RequestBody AuthDTO authDTO){
        return  profileService.updateProfilePassword(authDTO.getEmail(), authDTO.getPassword());
    }
    @PostMapping("/admin/create")
    public HttpStatus createProfileByAdmin(HttpServletRequest request,
                                           @RequestBody ProfileDTO dto){
        String email = JwtTokenUtil.getIdFromHeader(request, ProfileRole.ROLE_ADMIN);
        int result =  profileService.createProfile(email,dto);
        return result==1?HttpStatus.CREATED:HttpStatus.I_AM_A_TEAPOT;
    }
    @GetMapping("/info/{id}")
    public ResponseEntity<ProfileDTO> myInfo(@PathVariable Integer id){
        return ResponseEntity.ok(profileService.getMyInfo(id));
    }

    @GetMapping("/update/password/{profileId}")
    public ResponseEntity<AuthDTO> updatePassword(@PathVariable Integer profileId) {
        AuthDTO profile = profileService.forUpdateDTO(profileId);
        return ResponseEntity.ok(profile);
    }
    @PutMapping("/update/password/result")
    public String updateProfilePassword(@RequestBody AuthDTO profileDTO){
        return  profileService.updateProfilePassword(profileDTO.getEmail(), profileDTO.getPassword());
    }
    @GetMapping("/update/password/detail/{profileId}")
    public ResponseEntity<UpdateProfileNameAndEmail> updateDetail(@PathVariable Integer profileId) {
        UpdateProfileNameAndEmail profile = profileService.updateNameSurname(profileId);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/update/password/detail/result")
    public String updateProfilePasswordDetail(HttpServletRequest request,
            @RequestBody UpdateProfileNameAndEmail profileDTO){
        return  profileService.updateProfilePasswordDetail(profileDTO.getName(), profileDTO.getSurname(),JwtTokenUtil.getIdFromHeader(request));
    }



}
