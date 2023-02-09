package com.lld.pendencySystem.entity;

import com.lld.pendencySystem.enums.TrackingStatus;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Entity {
    private String id;
    private CTag firstCTag;
    private TrackingStatus isTracked ;

    private List<Tag> tagList ;

    public Entity(String id, CTag ctag, List<Tag> tagList) {
        this.id = id ;
        this.firstCTag = ctag;
        this.tagList = tagList;
        this.isTracked = TrackingStatus.ACTIVE;
    }

    public void startTracking() {
        this.isTracked = TrackingStatus.ACTIVE;
    }

    public void stopTracking() {
        this.isTracked = TrackingStatus.INACTIVE;
    }
}
