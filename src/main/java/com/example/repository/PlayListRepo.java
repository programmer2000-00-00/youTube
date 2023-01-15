package com.example.repository;

import com.example.entity.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlayListRepo extends JpaRepository<PlaylistEntity, Integer> {
    List<PlaylistEntity> findAllByOwnerIdOrderByOrderNumber(Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE PlaylistEntity as p set p.status=?1 where p.ownerId=?2")
    int changeStatus(String status, Integer id);

}
