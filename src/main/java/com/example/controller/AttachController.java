package com.example.controller;


import com.example.dto.AttachDTO;
import com.example.entity.AttachEntity;
import com.example.enums.ProfileRole;
import com.example.exception.ItemNotFoundException;
import com.example.service.AttachService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;

    @PostMapping("/upload")
    public ResponseEntity<AttachDTO> upload(@RequestParam("file") MultipartFile file) {
        AttachDTO dto = attachService.createAttach(file);
        return ResponseEntity.ok().body(dto);
    }
    @GetMapping(value = "/open/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open(@PathVariable("id") String id) {
        if (id != null && id.length() > 0) {
            try {
                return this.attachService.loadImage(id);
            } catch (Exception e) {
                e.printStackTrace();
                return new byte[0];
            }
        }
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(HttpServletRequest request,
                                        @PathVariable String id){
        Boolean response = attachService.delete(id);
        return ResponseEntity.ok(response);
    }
}
