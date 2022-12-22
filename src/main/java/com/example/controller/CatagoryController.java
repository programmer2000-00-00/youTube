package com.example.controller;

//import com.example.dto.CatagoryDTO;
import com.example.dto.CategoryDTO;
import com.example.service.CatagoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/catagory")
public class CatagoryController {
    @Autowired
    private CatagoryService catagoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("save")
    public ResponseEntity<?>save(@RequestBody CategoryDTO catagoryDTO){
        CategoryDTO save = catagoryService.save(catagoryDTO);
        return ResponseEntity.ok(save);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update/{id}")
    public ResponseEntity<?>update(@PathVariable Integer id,@RequestBody CategoryDTO catagoryDTO)
    {
        Boolean update = catagoryService.update(id, catagoryDTO);
        return ResponseEntity.ok(update);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
       return  ResponseEntity.ok(catagoryService.delete(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("sec/get")
    public ResponseEntity<?>get(){
        return ResponseEntity.ok(catagoryService.getList());
    }

}
