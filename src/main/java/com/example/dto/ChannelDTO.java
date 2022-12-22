package com.example.dto;

import com.example.entity.AttachEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.ChannelStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelDTO {
    private String id;
    private String name;
    private String photoId;
    private AttachDTO photo;
    private String description;
    private ChannelStatus channelStatus;
    private String bannerId;
    private AttachDTO banner;
    private Integer profileId;
    private ProfileDTO profile;
}
