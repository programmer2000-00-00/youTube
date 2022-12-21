package com.example.entity;

import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20)
    private  String name;

    @Column(length = 20)
    private  String surname;

    @Column(length = 20,unique = true)
    private  String email;
//id,name,surname,email,photo,role,status
    @Column
    private  String password;

    @Enumerated(value = EnumType.STRING)
    @Column
    private ProfileStatus status;

    @Enumerated(value = EnumType.STRING)
    @Column
    private ProfileRole role;
}
