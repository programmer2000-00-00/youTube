package com.example.controller;

import com.example.dto.playList.ChangeStatusDto;
import com.example.dto.playList.PlaylistEntityDTO;
import com.example.service.PlayListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.management.BadAttributeValueExpException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/play-list")
public class PlayListController {

    private final PlayListService playListService;


    @PostMapping("/add")
    public String create(PlaylistEntityDTO dto) throws BadAttributeValueExpException {
        return playListService.create(dto);
    }

    @GetMapping("/all")
    public Page<PlaylistEntityDTO> playListInfo(@RequestParam Integer page, @RequestParam Integer size) {
        return playListService.getList(page, size);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        return playListService.delete(id);
    }

    @GetMapping("/{id}")
    public List<PlaylistEntityDTO> getByUserId(@PathVariable Integer id){
        return playListService.getListByUserId(id);
    }
    @PutMapping("/change-status")
    public String changePlayListStatus(ChangeStatusDto dto){
        return playListService.changeStatus(dto);
    }
    @PutMapping("/update")
    public String updatePlayList(PlaylistEntityDTO dto){
        playListService.update(dto);
        return "done";
    }
}
