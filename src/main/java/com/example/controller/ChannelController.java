package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.dto.ChannelDTO;
import com.example.service.CatagoryService;
import com.example.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/channel")
public class ChannelController {
    @Autowired
    private ChannelService  channelService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("save")
    public ResponseEntity<?> save(@RequestBody ChannelDTO channelDTO){
        String save = channelService.save(channelDTO);
        return ResponseEntity.ok(save);
    }

}
