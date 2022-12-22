package com.example.service;

import com.example.dto.RegistrDTO;
import com.example.entity.EmailEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exception.AppBadRequestException;
import com.example.exception.PhoneAlreadyExistsException;
import com.example.repository.EmailRepository;
import com.example.repository.ProfileRepository;
import com.example.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    ProfileRepository profileRepository;

    public String registration(RegistrDTO registrDTO) {
        check(registrDTO);

        ProfileEntity byEmail = profileRepository.findByEmail(registrDTO.getEmail());
        if (byEmail!= null) {
            if (byEmail.getStatus().equals(ProfileStatus.NOT_ACTIVE)) {
                profileRepository.delete(byEmail);
            } else {
                throw new PhoneAlreadyExistsException("this number exists");
            }
        }

        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setName(registrDTO.getName());
        profileEntity.setSurname(registrDTO.getSurname());
        profileEntity.setEmail(registrDTO.getEmail());
        profileEntity.setStatus(ProfileStatus.NOT_ACTIVE);
        profileEntity.setRole(ProfileRole.ROLE_USER);
        profileEntity.setEmail(registrDTO.getEmail());

        profileRepository.save(profileEntity);

        StringBuilder sb = new StringBuilder();
        sb.append("<h1 style=\\\"text-align: center\\\">Salom Qalaysan</h1>");
        String link = String.format("<a href=\"http://192.168.59.235/auth/verification/email/%s\"> Click there</a>", JwtTokenUtil.encode(byEmail.getId()));
        sb.append(link);
        emailService.sendEmailMine(registrDTO.getEmail(), "Complite Registration", sb.toString());
        EmailEntity email=new EmailEntity();
        email.setMessage("Salom Qalaysan");
        email.setEmail(profileEntity.getEmail());
        email.setCreatedDate(LocalDateTime.now());
        emailRepository.save(email);


//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//            }
//        };
//        Thread thread = new Thread(runn\able);
//        thread.start();
        return "Emailga link ketdi aka tekwiring qani";

    }

    public void check(RegistrDTO registrDTO){
        if(registrDTO.getName()==null||registrDTO.getName().trim().length()<=3){
            throw new AppBadRequestException("Name is wrong");
        }
        if(registrDTO.getSurname()==null||registrDTO.getSurname().trim().length()<3){
            throw new AppBadRequestException("surname is wrong");
        }
        if(registrDTO.getEmail()==null||registrDTO.getEmail().trim().length()<3){
            throw new AppBadRequestException("phone is wrong");
        }
        if(registrDTO.getMainPhotoId()==null||registrDTO.getMainPhotoId().trim().length()<3){
            throw new AppBadRequestException("password is wrong");
        }

    }
}
