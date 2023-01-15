package com.example.dto.playList;

import com.example.entity.PlaylistEntity;
import com.example.enums.PlaylistStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class ChangeStatusDto {
    PlaylistStatus oldStatus;
    PlaylistStatus newStatus;
    Integer playListId;
}
