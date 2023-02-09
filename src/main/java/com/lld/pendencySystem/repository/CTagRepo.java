package com.lld.pendencySystem.repository;

import com.lld.pendencySystem.entity.CTag;
import com.lld.pendencySystem.entity.Tag;

public interface CTagRepo {
    public CTag findByTag(Tag tag);

    public void addCTag(Tag tag, CTag ctag);
}
