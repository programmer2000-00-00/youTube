package com.example.service;

import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.dto.auth.AuthDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileStatus;
import com.example.exception.ItemNotFoundException;
import com.example.mapper.UpdateProfileNameAndEmail;
import com.example.repository.ProfileCustomRepository;
import com.example.repository.ProfileRepository;
import com.example.util.JwtTokenUtil;
import com.example.util.MD5Util1;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileCustomRepository profileCustomRepository;
    public void filter(ProfileFilterDTO filterDTO, int page, int size) {
        List<ProfileEntity> profileEntityList = profileCustomRepository.filter(filterDTO, page, size);
        profileEntityList.forEach(entity -> {
            System.out.println(entity.getId());
            System.out.println(entity.getName());
            System.out.println(entity.getEmail());
            System.out.println(entity.getRole());
        });
    }

    public String updateProfilePassword(String email, String newPassword) {
        int result = profileRepository.updateProfilePassword(MD5Util1.encode(newPassword),email);
        if (result==1)
            return "Done";
        return "Wrong";
    }

    public AuthDTO forUpdateDTO(Integer profileId) {

        Optional<AuthDTO> byIdEmailAndPassword = profileRepository.findByIdEmailAndPassword(profileId);
        String password = byIdEmailAndPassword.get().getPassword();
        byIdEmailAndPassword.get().setPassword((password));
        return byIdEmailAndPassword.get();
    }

    public ProfileDTO getMyInfo(Integer id) {
        return profileRepository.getMyInfo(id).get();
    }

    public UpdateProfileNameAndEmail updateNameSurname(Integer profileId) {
        return profileRepository.getNameAndSurnameById(profileId);
    }

    public String updateProfilePasswordDetail(String name, String surname,String email) {
        int result = profileRepository.updateProfilePasswordDetail(name,surname, email);
        if (result==1)
            return "Done";
        return "Wrong";
    }
    public int createProfile( ProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setPrtId(SpringSecurityUtil.getCurrentUserId());
        ProfileEntity save = profileRepository.save(entity);
        return 1;
    }

    public AuthDTO edit(String jwt) {
        String email = JwtTokenUtil.decodeForEmailVerification(jwt);
        ProfileEntity exists = profileRepository.findByEmail(email);
        AuthDTO dto = new AuthDTO();
        if (exists.getStatus().equals(ProfileStatus.ACTIVE)) {
            dto.setEmail(exists.getEmail());
            dto.setPassword(exists.getPassword());
            return dto;
        }
        throw new ItemNotFoundException("bro dabdala qildiz");
    }
}
