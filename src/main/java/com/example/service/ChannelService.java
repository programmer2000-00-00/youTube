package com.example.service;

import com.example.dto.ChannelDTO;
import com.example.entity.ChannelEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.exception.AppBadRequestException;
import com.example.exception.ItemNotFoundException;
import com.example.repository.ChannelRepository;
import com.example.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChannelService {
    @Autowired
    public ChannelRepository channelRepository;
    @Autowired
    private ResourceBundleService resourceService;
    @Autowired
    private AttachService attachService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ProfileRepository profileRepository;

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
        if (channelDTO.getPhotoId()==null || channelDTO.getPhoto().getId().trim().length() < 3){
        throw new AppBadRequestException("photoId is wrong");
        }
        if(channelDTO.getDescription()==null || channelDTO.getDescription().trim().length() < 3){
            throw new AppBadRequestException("channelId is wrong");
        }
        if(channelDTO.getBannerId()==null || channelDTO.getBanner().getId().trim().length() < 3){
            throw new AppBadRequestException("bannerId is wrong");
        }
        if(channelDTO.getProfileId()==null){
            throw new AppBadRequestException("profileId is wrong");
        }

    }

    public String update(ChannelDTO channelDTO,Integer id, Language language) {

        Optional<ChannelEntity> byId = channelRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ItemNotFoundException(resourceService.getMessage("credential.wrong", language.name()));
        }

        attachService.loadImage(channelDTO.getPhotoId());
        profileService.forUpdateDTO(channelDTO.getProfileId());
        Integer integer = channelRepository.updateByid(channelDTO.getName(),channelDTO.getPhotoId(),
                channelDTO.getDescription(),channelDTO.getBannerId(),channelDTO.getProfileId(),id);
        if(integer!=0){
            return "Successefully updated";
        }
        return  null;

    }

    public String update1(ChannelDTO channelDTO, Integer id, Language language) {
        Optional<ChannelEntity> byId = channelRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ItemNotFoundException(resourceService.getMessage("credential.wrong", language.name()));
        }
        attachService.loadImage(channelDTO.getPhotoId());
        Integer integer = channelRepository.updateByPhotoId(channelDTO.getPhotoId(),id);
        if(integer!=0){
            return "Successefully updated";
        }
        return  null;
    }

    public String update2(ChannelDTO channelDTO, Integer id, Language language) {
        Optional<ChannelEntity> byId = channelRepository.findById(id);
        if(byId.isEmpty()){
            throw new ItemNotFoundException((resourceService.getMessage("credential.wrong",language.name())));
        }
        attachService.loadImage(channelDTO.getPhotoId());

        Integer integer = channelRepository.updateByBannerId(channelDTO.getBannerId(),id);
        if(integer!=0){
            return "Successefully updated";
        }
        return null;
    }

    public Page<ChannelDTO> getByPage(Integer page, Integer size) {
        Pageable pageable= PageRequest.of(page,size);
        Page<ChannelEntity> list = channelRepository.findAll(pageable);
        List<ChannelEntity> content=list.getContent();
        Long totalElement= list.getTotalElements();
        List<ChannelDTO> dtoList=new ArrayList<>();
        for (ChannelEntity channel : content) {
            ChannelDTO channelDTO=new ChannelDTO();
            channelDTO.setName(channel.getName());
            channelDTO.setBannerId(channel.getBannerId());
            channelDTO.setPhotoId(channel.getPhotoId());
            channelDTO.setDescription(channel.getDescription());
            channelDTO.setProfileId(channelDTO.getProfileId());
            dtoList.add(channelDTO);
        }
        return new PageImpl<>(dtoList,pageable,totalElement);
    }
    public List<ChannelDTO> toDTO(List<ChannelEntity> entityList){
        List<ChannelDTO> dtoList=new ArrayList<>();
        for (ChannelEntity channel : entityList) {
            ChannelDTO channelDTO=new ChannelDTO();
            channelDTO.setName(channel.getName());
            channelDTO.setBannerId(channel.getBannerId());
            channelDTO.setPhotoId(channel.getPhotoId());
            channelDTO.setDescription(channel.getDescription());
            channelDTO.setProfileId(channelDTO.getProfileId());
            dtoList.add(channelDTO);
        }
        return dtoList;
    }

    public ChannelDTO getById(Integer id, Language language) {
        Optional<ChannelEntity> channel = channelRepository.findById(id);
        if(channel.isEmpty()){
            throw new ItemNotFoundException((resourceService.getMessage("credential.wrong",language.name())));
        }
        ChannelDTO channelDTO=new ChannelDTO();
        channelDTO.setName(channel.get().getName());
        channelDTO.setBannerId(channel.get().getBannerId());
        channelDTO.setPhotoId(channel.get().getPhotoId());
        channelDTO.setDescription(channel.get().getDescription());
        channelDTO.setProfileId(channelDTO.getProfileId());
        return channelDTO;

    }

    public String update3(ChannelDTO channelDTO, ProfileEntity currentUser, Language language) {
           if(currentUser.getRole().equals(ProfileRole.ROLE_ADMIN)){
               channelRepository.update(channelDTO.getChannelStatus(),channelDTO.getName());
           }
           else if(currentUser.getRole().equals(ProfileRole.ROLE_USER)){
               ChannelEntity byName = channelRepository.findByName(channelDTO.getName());
               if(byName.getProfileId()==currentUser.getId()){
                   channelRepository.update(channelDTO.getChannelStatus(), channelDTO.getName());
               }
           }
               return null;
    }

    public List<ChannelDTO> getChannel(ProfileEntity currentUser, Language language) {
        List<ChannelEntity> channelEntityList=channelRepository.findByProfileId(currentUser.getId());
             return toDTO(channelEntityList);
    }
}
