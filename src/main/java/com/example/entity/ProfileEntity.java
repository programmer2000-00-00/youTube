package com.example.entity;

import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private  String name;

    @Column(length = 50)
    private  String surname;

    @Column(length = 50,unique = true)
    private  String email;
//id,name,surname,email,photo,role,status
    @Column(length = 200)
    private  String password;
    @Column(name = "attach_id")
    String attachId;
    @OneToOne
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity attach;

    @Enumerated(value = EnumType.STRING)
    @Column
    private ProfileStatus status;

    @Enumerated(value = EnumType.STRING)
    @Column
    private ProfileRole role;
    @Column
    private Integer prtId;
}
