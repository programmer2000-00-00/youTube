package com.example.repository;

import com.example.dto.ProfileDTO;
import com.example.dto.auth.AuthDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.mapper.UpdateProfileNameAndEmail;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity,Integer> {

    ProfileEntity findByEmail(String email);

    Optional<ProfileEntity> findAllByRole(ProfileRole role);

    ProfileEntity findByEmailAndPassword(String email, String password);
    @Transactional
    @Modifying
    @Query("UPDATE ProfileEntity as p set p.password=?1 where p.email=?2")
    int updateProfilePassword(String password, String email);

    @Query("select new com.example.dto.auth.AuthDTO(p.password,p.email) from ProfileEntity p where  p.id=?1")
    Optional<AuthDTO>findByIdEmailAndPassword(Integer id);

    @Query("select new com.example.dto.ProfileDTO(p.id,p.name,p.surname,p.email,p.password,p.attachId) from ProfileEntity p where  p.id=?1")
    Optional<ProfileDTO> getMyInfo(Integer id);

    @Query("select new com.example.mapper.UpdateProfileNameAndEmail(p.name,p.surname) from ProfileEntity p where  p.id=?1")
    UpdateProfileNameAndEmail getNameAndSurnameById(Integer profileId);
    @Transactional
    @Modifying
    @Query("UPDATE ProfileEntity as p set p.name=?1, p.surname=?2 where p.email=?3")
    int updateProfilePasswordDetail(String name, String surname,String email);
}
