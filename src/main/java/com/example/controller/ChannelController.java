package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.dto.ChannelDTO;
import com.example.enums.Language;
import com.example.service.CatagoryService;
import com.example.service.ChannelService;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/channel")
public class ChannelController {
    @Autowired
    private ChannelService  channelService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ChannelDTO channelDTO){
        String save = channelService.save(channelDTO);
        return ResponseEntity.ok(save);
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ChannelDTO channelDTO,@RequestParam("id") Integer id,
                                    @RequestHeader(name = "Accept-Language", defaultValue = "ENG") Language language){
        String update = channelService.update(channelDTO,id,language);
        return ResponseEntity.ok(update);
    }
    @PutMapping("/update/photo")
    public ResponseEntity<?> update1(@RequestBody ChannelDTO channelDTO,@RequestParam("id") Integer id,
                                    @RequestHeader(name = "Accept-Language", defaultValue = "ENG") Language language){
        String update = channelService.update1(channelDTO,id,language);
        return ResponseEntity.ok(update);
    }
    @PutMapping("/update/banner")
    public ResponseEntity<?> update2(@RequestBody ChannelDTO channelDTO,@RequestParam("id") Integer id,
                                     @RequestHeader(name = "Accept-Language", defaultValue = "ENG") Language language){
        String update = channelService.update2(channelDTO,id,language);
        return ResponseEntity.ok(update);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get/page")
    public ResponseEntity<?>getByPage(@RequestParam("page") Integer page, @RequestParam("size") Integer size){
        return ResponseEntity.ok(channelService.getByPage(page,size));
    }
    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam("id") Integer id,
                                 @RequestHeader(name = "Accept-Language", defaultValue = "ENG") Language language) {
        return ResponseEntity.ok(channelService.getById(id,language));
    }

    @PutMapping("/update/status")
    public ResponseEntity<?>changeStatus(@RequestBody ChannelDTO channelDTO,
                                         @RequestHeader(name = "Accept-Language", defaultValue = "ENG") Language language){
       return ResponseEntity.ok(channelService.update3(channelDTO,SpringSecurityUtil.getCurrentEntity(),language));
    }
    @GetMapping("/get/channel")
    public ResponseEntity<?>getChannel(@RequestHeader(name = "Accept-Language", defaultValue = "ENG") Language language){
        List<ChannelDTO> channel = channelService.getChannel(SpringSecurityUtil.getCurrentEntity(),language);
        return ResponseEntity.ok(channel);

    }

}
