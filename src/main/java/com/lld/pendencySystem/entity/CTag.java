package com.lld.pendencySystem.entity;

import com.lld.pendencySystem.enums.TagType;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class CTag extends Tag {
    private Long count ;

    // Contains all next connected tags
    private HashMap<Tag, CTag> linkedTags;

    public CTag(String name, TagType type) {
        super(name, type);
        this.count = Long.valueOf(0);
        this.linkedTags = new HashMap<>();
    }

    public void addTracking() {
        this.count = this.count +1 ;
    }

    public void stopTracking() {

        if( this.count > 0)
            this.count = this.count -1 ;
    }
}
