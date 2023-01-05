package com.example.service;

import com.example.dto.CategoryDTO;
import com.example.dto.ChannelDTO;
import com.example.entity.CatagoryEntity;
import com.example.entity.ChannelEntity;
import com.example.exception.AppBadRequestException;
import com.example.repository.ChannelRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChannelService {
    @Autowired
    public ChannelRepository channelRepository;
    public String save(ChannelDTO channelDTO) {
                check(channelDTO);
        ChannelEntity channelEntity=new ChannelEntity();
        channelEntity.setName(channelDTO.getName());
        channelEntity.setPhotoId(channelDTO.getPhotoId());
        channelEntity.setDescription(channelDTO.getDescription());
        channelEntity.setBannerId(channelDTO.getBannerId());
        channelEntity.setProfileId(channelEntity.getProfileId());
        channelRepository.save(channelEntity);
        return "Successefully saved";
    }
    public void check(ChannelDTO channelDTO) {
        if (channelDTO.getName() == null || channelDTO.getName().trim().length() < 3) {
            throw new AppBadRequestException("name is wrong");
        }
        if (channelDTO.getPhoto()==null || channelDTO.getPhoto().getId().trim().length() < 3){
        throw new AppBadRequestException("photoId is wrong");
        }
        if(channelDTO.getDescription()==null || channelDTO.getDescription().trim().length() < 3){
            throw new AppBadRequestException("channelId is wrong");
        }
        if(channelDTO.getBanner()==null || channelDTO.getBanner().getId().trim().length() < 3){
            throw new AppBadRequestException("bannerId is wrong");
        }
        if(channelDTO.getProfile()==null){
            throw new AppBadRequestException("profileId is wrong");
        }

    }
}
