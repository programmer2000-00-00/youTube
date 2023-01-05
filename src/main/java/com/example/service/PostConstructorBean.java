package com.example.service;

import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exception.ItemAlreadyExistsException;
import com.example.repository.ProfileRepository;
import com.example.util.MD5Util1;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PostConstructorBean {
    @Autowired
    private ProfileRepository profileRepository;
    @PostConstruct
    public void init(){
        Optional<ProfileEntity> optional = profileRepository.findAllByRole(ProfileRole.ROLE_ADMIN);
        if(optional.isPresent()) {
            System.out.println("Admin already created");
            return;
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName("Javlonbek");
        entity.setSurname("Shobo'tayev");
        entity.setEmail("jshobotayev@gmail.com");
        entity.setPassword(MD5Util1.encode("3433"));
        entity.setRole(ProfileRole.ROLE_ADMIN);
        entity.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(entity);
        System.out.println(entity.getName());
    }
}