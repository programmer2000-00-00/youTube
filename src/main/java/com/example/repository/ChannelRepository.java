package com.example.repository;

import com.example.entity.ChannelEntity;
import com.example.enums.ChannelStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ChannelRepository extends CrudRepository<ChannelEntity,Integer>, PagingAndSortingRepository<ChannelEntity,Integer> {
    @Transactional
    @Modifying
    @Query("update ChannelEntity c set c.name=?1,c.photoId=?2,c.description=?3,c.bannerId=?4,c.profileId=?5 where c.id=?6")
    Integer updateByid(String name, String photoId, String description, String bannerId, Integer profileId, Integer id);
    @Transactional
    @Modifying
    @Query("update ChannelEntity c set c.name=?1 where c.id=?2")
    Integer updateByPhotoId(String photoId, Integer id);
    @Transactional
    @Modifying
    @Query("update ChannelEntity c set c.name=?1 where c.id=?2")
    Integer updateByBannerId(String bannerId, Integer id);
    List<ChannelEntity> findByProfileId(Integer id);
    @Transactional
    @Modifying
    @Query("update ChannelEntity c set c.channelStatus=?1 where c.name=?2")
    Integer update(ChannelStatus channelStatus, String name);

    ChannelEntity findByName(String name);
}
