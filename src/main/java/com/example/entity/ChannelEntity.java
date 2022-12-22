package com.example.entity;

import com.example.enums.ChannelStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "channel")
public class ChannelEntity {

    @Id
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column
    private String name;

    @Column(name = "photo_id")
    private String photoId;
    @JoinColumn(name="photo_id", updatable = false, insertable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private AttachEntity photo;
    @Column
    private String description;
    @Column
    private ChannelStatus channelStatus;
    @Column(name = "banner_id")
    private String bannerId;
    @JoinColumn(name="banner_id", updatable = false, insertable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private AttachEntity banner;

    @Column(name = "profile_id")
    private Integer profileId;
    @JoinColumn(name="profile_id", updatable = false, insertable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private ProfileEntity profile;
}
