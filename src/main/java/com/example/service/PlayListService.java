package com.example.service;

import com.example.dto.playList.ChangeStatusDto;
import com.example.dto.playList.PlaylistEntityDTO;
import com.example.entity.PlaylistEntity;
import com.example.exception.ItemNotFoundException;
import com.example.repository.PlayListRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.management.BadAttributeValueExpException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayListService {
    private final PlayListRepo playListRepo;

    public Page<PlaylistEntityDTO> getList(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PlaylistEntity> entities = playListRepo.findAll(pageable);

        List<PlaylistEntity> content = entities.getContent();
        List<PlaylistEntityDTO> dto = new LinkedList<>();
        for (PlaylistEntity PlaylistEntity : content) {
            PlaylistEntityDTO Playlist = toDTO(PlaylistEntity);
            dto.add(Playlist);
        }
        return new PageImpl<>(dto, pageable, entities.getTotalElements());
    }


    private PlaylistEntityDTO toDTO(PlaylistEntity playlist) {
        PlaylistEntityDTO dto = new PlaylistEntityDTO();
        dto.setName(playlist.getName());
        dto.setDescription(playlist.getDescription());
        dto.setChannelId(playlist.getChannelId());
        dto.setStatus(playlist.getStatus());
        dto.setOrderNumber(playlist.getOrderNumber());

        return dto;
    }

    private PlaylistEntity toEntity(PlaylistEntityDTO playlist) {
        PlaylistEntity entity = new PlaylistEntity();
        entity.setName(playlist.getName());
        entity.setDescription(playlist.getDescription());
        entity.setChannelId(playlist.getChannelId());
        entity.setStatus(playlist.getStatus());
        entity.setOrderNumber(playlist.getOrderNumber());
        return entity;
    }

    public String create(PlaylistEntityDTO dto) throws BadAttributeValueExpException {
        PlaylistEntity save = playListRepo.save(toEntity(dto));
        if (save == null) {
            return "dont saved!";
        }
        return "saved";
    }

    public String delete(Integer id) {
        if (playListRepo.findById(id).isPresent()) {
            playListRepo.deleteById(id);
            return "done!";
        } else {
            throw new ItemNotFoundException("PlayList not Found!");
        }
    }

    public List<PlaylistEntityDTO> getListByUserId(Integer id) {
        List<PlaylistEntityDTO> dtos = new LinkedList<>();
        List<PlaylistEntity> result = playListRepo.findAllByOwnerIdOrderByOrderNumber(id);
        if (!result.isEmpty()){
        for (PlaylistEntity entity : result) {
            dtos.add(toDTO(entity));
        }
        return dtos;}
        throw new ItemNotFoundException("this Id is invalid!");
    }

    public String changeStatus(ChangeStatusDto dto) {
        Optional<PlaylistEntity> byId = playListRepo.findById(dto.getPlayListId());
        if (byId.isPresent()){
            if (byId.get().getStatus().equals(dto.getOldStatus())) {
                return "none changed!";
            }
            if (playListRepo.changeStatus(dto.getNewStatus().name(), dto.getPlayListId())==1){
                return "done!";
            }
        }
        return "undone!";
    }
    public void update(PlaylistEntityDTO dto) {
        PlaylistEntity entity = toEntity(dto);
        PlaylistEntity save = playListRepo.save(entity);
    }
}
