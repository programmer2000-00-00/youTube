package com.example.dto.playList;

import com.example.entity.ChannelEntity;
import com.example.enums.PlaylistStatus;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlaylistEntityDTO {
    private Integer id;
    private String channelId;
    private String name;
    private String description;
    private PlaylistStatus status;

    private Integer orderNumber;
}
