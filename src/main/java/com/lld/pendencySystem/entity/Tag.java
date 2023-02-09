package com.lld.pendencySystem.entity;

import com.lld.pendencySystem.enums.TagType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tag {
    private String name ;
    private TagType type;

    public Tag(String name, TagType type) {
        this.name = name;
        this.type = type;
    }
}
