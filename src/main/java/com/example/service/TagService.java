package com.example.service;
import com.example.dto.TagDTO;
import com.example.entity.TagEntity;
import com.example.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;


    public String save(TagDTO dto) {
        TagEntity entity = new TagEntity();

        entity.setName(dto.getName());
        entity.setCreatedDate(dto.getCreatedDate());
        tagRepository.save(entity);
        return "succes create tag!";
    }
}
