package com.example.repository;

import com.example.dto.ProfileFilterDTO;
import com.example.entity.ProfileEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProfileCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public List<ProfileEntity> filter(ProfileFilterDTO filter, int page, int size) {
        StringBuilder builder = new StringBuilder("SELECT a FROM ProfileEntity a ");
        Map<String,Object> params = new HashMap();

        if (filter.getName() != null) {
            builder.append(" And a.name =:name ");
            params.put("name",filter.getName());
        }
        if (filter.getSurname()!=null){
            builder.append( " and a.surname =:surname ");
            params.put("surname",filter.getSurname());
        }
        if (filter.getEmail()!=null){
            builder.append( " and a.email =:email ");
            params.put("email", filter.getEmail());
        }
        if (filter.getStatus()!=null){
            builder.append( " and a.status =:status ");
            params.put("status", filter.getStatus());
        }
        if (filter.getProfileRole()!=null){
            builder.append( " and a.role =:role ");
            params.put("role", filter.getProfileRole());
        }
        Query query = entityManager.createQuery(builder.toString());


        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        for (Map.Entry<String,Object> entry: params.entrySet()){
            query.setParameter(entry.getKey(),entry.getValue());
        }
        List<ProfileEntity> profileEntityList = query.getResultList();
        return profileEntityList;

    }
    public List<ProfileEntity> getAll() {
        Query query = this.entityManager.createQuery("SELECT p From ProfileEntity as p");
        List profileEntities = query.getResultList();
        return profileEntities;
    }



    public List<ProfileEntity> getAllNative() {
        Query query = entityManager.createNativeQuery("SELECT * FROM profile ", ProfileEntity.class);
        List profileEntities = query.getResultList();
        return profileEntities;
    }
}
