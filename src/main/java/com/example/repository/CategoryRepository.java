package com.example.repository;

import com.example.entity.CatagoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends CrudRepository<CatagoryEntity,Integer>, PagingAndSortingRepository<CatagoryEntity,Integer> {
    @Transactional
    @Modifying
    @Query("update CatagoryEntity c set c.name=?1 where c.id=?2")
    Integer updateById(String name,Integer id);


}
