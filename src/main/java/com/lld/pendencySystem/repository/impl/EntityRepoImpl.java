package com.lld.pendencySystem.repository.impl;

import com.lld.pendencySystem.entity.Entity;
import com.lld.pendencySystem.repository.EntityRepo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class EntityRepoImpl implements EntityRepo {

    private HashMap<String,Entity> entityHashMap;

    public EntityRepoImpl() {
        this.entityHashMap = new HashMap<>();
    }

    @Override
    public Entity findEntityById(String entityId) {
        return entityHashMap.get(entityId);
    }

    @Override
    public void addEntity(Entity entity) {
        // Both adds and updates entity
        entityHashMap.put(entity.getId(), entity);
    }

    @Override
    public void stopTracking(String entityId) {
        entityHashMap.get(entityId).stopTracking();
    }
}
