package com.example.service;

import com.example.dto.AttachDTO;
import com.example.entity.AttachEntity;
import com.example.exception.ItemNotFoundException;
import com.example.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttachService {
    @Value("${attach.upload.folder}")
    private String attachFolder;
    @Value("${attach.open.url}")
    private String attachOpenUrl;
    @Autowired
    private AttachRepository attachRepository;
     public AttachDTO createAttach(MultipartFile file){
         try {

             String attachPath = getYmDString();
             String extension = getExtension(file.getOriginalFilename());
             String uuid = UUID.randomUUID().toString();
             String fileName = uuid + "." + extension; // UUID.png

             File folder = new File(attachFolder + attachPath);
             if (!folder.exists()) {
                 folder.mkdirs();
             }

             byte[] bytes = file.getBytes();

             Path path = Paths.get(attachFolder + attachPath + "/" + fileName);
             Files.write(path, bytes);

             AttachEntity entity = new AttachEntity();
             entity.setId(uuid);
             entity.setOriginalName(file.getOriginalFilename());
             entity.setPath(attachPath);
             entity.setSize(file.getSize());
             entity.setExtension(extension);
             entity.setCreatedData(LocalDateTime.now());
             attachRepository.save(entity);

             AttachDTO attachDTO = toDTO(entity);
             attachDTO.setUrl(attachOpenUrl + fileName);
              return attachDTO;
         } catch (IOException e) {
             e.printStackTrace();
             System.out.println(e.getMessage());
         }
         return null;
     }
    public Boolean delete(String id) {
        AttachEntity attach = attachRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Not found");
        });

        File file = new File(attachFolder + attach.getPath() + "/" + id + "." + attach.getExtension());
        if (file.delete()) {
            attachRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public byte[] loadImage(String id) {
        byte[] imageInByte;
        BufferedImage originalImage;

        try {
            Optional<AttachEntity> byId = attachRepository.findById(id);
            if (byId.isEmpty()) {
                throw new ItemNotFoundException("attach");
            }
            File file = new File("attaches/" + byId.get().getPath() + "/" + byId.get().getOriginalName() + "." + byId.get().getExtension());
            if (!file.exists()) {
                throw new ItemNotFoundException("File");
            }
            originalImage = ImageIO.read(file);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "png", baos);


            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private AttachDTO toDTO(AttachEntity entity) {
        AttachDTO attachDTO = new AttachDTO();
        attachDTO.setId(entity.getId());
        attachDTO.setOriginalName(entity.getOriginalName());
        attachDTO.setPath(entity.getPath());
        attachDTO.setSize(entity.getSize());
        attachDTO.setCreatedData(entity.getCreatedData());
        attachDTO.setDuration(entity.getDuration());
        attachDTO.setExtension(entity.getExtension());
        return attachDTO;
    }

    public String getYmDString() {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        int day = LocalDate.now().getDayOfMonth();

        return year + "/" + month + "/" + day; // 2022/04/23
    }
    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }
}
