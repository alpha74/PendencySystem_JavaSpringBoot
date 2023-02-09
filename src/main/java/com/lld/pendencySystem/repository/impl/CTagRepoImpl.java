package com.lld.pendencySystem.repository.impl;

import com.lld.pendencySystem.entity.Tag;
import com.lld.pendencySystem.entity.CTag;
import com.lld.pendencySystem.repository.CTagRepo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class CTagRepoImpl implements CTagRepo {

    private HashMap<Tag, CTag> cTagHashMap;

    public CTagRepoImpl() {
        this.cTagHashMap = new HashMap<>();
    }


    @Override
    public CTag findByTag(Tag tag) {
        return cTagHashMap.get(tag);
    }

    @Override
    public void addCTag(Tag tag, CTag ctag) {
        cTagHashMap.put(tag, ctag);
    }
}
