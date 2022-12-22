package com.example.dto;

import com.example.entity.ChannelEntity;
import com.example.enums.PlaylistStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistDTO {
    private Integer id;
    private String channelId;
    private ChannelEntity channelEntity;
    private String name;
    private String description;
    private PlaylistStatus status;
    private Integer orderNumber;
}
