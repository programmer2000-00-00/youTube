package com.example.entity;

import com.example.enums.ChannelStatus;
import com.example.enums.PlaylistStatus;
import com.example.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.repository.core.support.IncompleteRepositoryCompositionException;

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

    @Column(name = "owner_id")
    private Integer ownerId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", updatable = false, insertable = false)
    private ProfileEntity owner;
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
