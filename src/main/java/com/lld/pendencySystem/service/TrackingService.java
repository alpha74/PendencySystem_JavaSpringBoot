package com.lld.pendencySystem.service;

import com.lld.pendencySystem.entity.Tag;

import java.util.List;

public interface TrackingService {
    public void startTracking(String entityId, List<Tag> tagList);

    public void stopTracking(String entityId);

    public Long getCounts(List<Tag> tagList);
}
