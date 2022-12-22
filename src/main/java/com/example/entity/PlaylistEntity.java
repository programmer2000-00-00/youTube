package com.example.entity;

import com.example.enums.ChannelStatus;
import com.example.enums.PlaylistStatus;
import com.example.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "playlist")
public class PlaylistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "channel_id")
    private String channelId;
    @JoinColumn(name="channel_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ChannelEntity channelEntity;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private PlaylistStatus status;
    @Column
    private Integer orderNumber;

}
