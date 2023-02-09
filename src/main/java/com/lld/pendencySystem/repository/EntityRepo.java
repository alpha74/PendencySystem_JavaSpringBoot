package com.lld.pendencySystem.repository;

import com.lld.pendencySystem.entity.Entity;

public interface EntityRepo {
    public Entity findEntityById(String entityId);

    public void addEntity(Entity entity);

    public void stopTracking(String entityId);
}
