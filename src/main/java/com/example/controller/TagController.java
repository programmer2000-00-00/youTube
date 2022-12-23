package com.example.controller;


import com.example.dto.TagDTO;
import com.example.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    private Logger log= LoggerFactory.getLogger(TagController.class);

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody TagDTO dto){

        log.info("Create:{}"+dto);
        return ResponseEntity.ok(tagService.save(dto));
    }

}