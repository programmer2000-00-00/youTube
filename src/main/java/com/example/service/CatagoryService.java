package com.example.service;

import com.example.dto.CategoryDTO;
import com.example.entity.CatagoryEntity;
import com.example.exception.AppBadRequestException;
import com.example.exception.ItemNotFoundException;
import com.example.repository.CategoryRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CatagoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public CategoryDTO save(CategoryDTO catagoryDTO) {

        check(catagoryDTO);
        CatagoryEntity catagoryEntity=new CatagoryEntity();
        catagoryEntity.setName(catagoryDTO.getName());
        catagoryEntity.setPrtId(SpringSecurityUtil.getCurrentUserId());
        catagoryEntity.setCreatedDate(LocalDateTime.now());
        categoryRepository.save(catagoryEntity);
        catagoryDTO.setPrtId(SpringSecurityUtil.getCurrentUserId());
        return catagoryDTO;

    }

    public void check(CategoryDTO catagoryDTO) {
        if (catagoryDTO.getName() == null || catagoryDTO.getName().trim().length() < 3) {
            throw new AppBadRequestException("name is wrong");
        }
}

    public Boolean update(Integer id, CategoryDTO catagoryDTO) {
        Optional<CatagoryEntity> byId = categoryRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ItemNotFoundException("this id catagory not found");
        }
        Integer integer = categoryRepository.updateById(catagoryDTO.getName(),id);
        if (integer == 0) {
            throw new AppBadRequestException("not updated");
        }
        return true;
    }

    public String delete(Integer id) {
        Optional<CatagoryEntity> byId = categoryRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ItemNotFoundException("this id not found");
        }
          categoryRepository.deleteById(id);


        return "successefully deleted";

    }

    public List<CategoryDTO> getList() {
        Iterable<CatagoryEntity> all = categoryRepository.findAll();
        return ToDTO(all);

    }
    public List<CategoryDTO>ToDTO(Iterable<CatagoryEntity> list){
        List<CategoryDTO>dtoList=new ArrayList<>();
        for (CatagoryEntity entity : list) {
            CategoryDTO dto=new CategoryDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setCreatedDate(entity.getCreatedDate());
            dtoList.add(dto);
        }
        return dtoList;
    }
}
