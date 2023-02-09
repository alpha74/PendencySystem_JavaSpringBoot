package com.lld.pendencySystem.service.impl;

import com.lld.pendencySystem.entity.CTag;
import com.lld.pendencySystem.entity.Entity;
import com.lld.pendencySystem.entity.Tag;
import com.lld.pendencySystem.enums.TagType;
import com.lld.pendencySystem.repository.EntityRepo;
import com.lld.pendencySystem.repository.CTagRepo;
import com.lld.pendencySystem.service.TrackingService;
import lombok.NonNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TrackingServiceImpl implements TrackingService {

    private EntityRepo entityRepository;
    private CTagRepo cTagRepository;

    public TrackingServiceImpl(EntityRepo entityRepository, CTagRepo CTagRepository) {
        this.entityRepository = entityRepository;
        this.cTagRepository = CTagRepository;
    }

    @Override
    public void startTracking(String entityId, List<Tag> tagList) {
        if( tagList.isEmpty() )
            return ;

        // For new entity creation. Ignore if entity already exists
        if( entityRepository.findEntityById(entityId) == null ) {

            // Create CTag linked list from tagList
            CTag firstCTag = cTagRepository.findByTag(tagList.get(0));
            HashMap<Tag,CTag> linkedTags = null;

            // If firstCTag is not already present
            if(firstCTag == null) {
                firstCTag = new CTag(tagList.get(0).getName(), tagList.get(0).getType());
                linkedTags = new HashMap<>();
                cTagRepository.addCTag(tagList.get(0), firstCTag);
            } else {
                linkedTags = firstCTag.getLinkedTags();
            }

            firstCTag.addTracking();

            CTag prevCTag = firstCTag ;

            for( int i = 1 ; i < tagList.size() ; i++ ) {
                CTag nextCTag = null ;

                // If CTag is present
                if(linkedTags.containsKey(tagList.get(i))) {
                    nextCTag = linkedTags.get(tagList.get(i));

                } else {
                    nextCTag = new CTag(tagList.get(i).getName(), tagList.get(i).getType());
                    linkedTags.put(tagList.get(i), nextCTag);
                    prevCTag.setLinkedTags(linkedTags);
                }

                nextCTag.addTracking();

                linkedTags = nextCTag.getLinkedTags();
                prevCTag = nextCTag;
            }

            // Create new entity
            Entity entity = new Entity(entityId, firstCTag, tagList);

            entityRepository.addEntity(entity);
        }

    }

    @Override
    public void stopTracking(@NonNull String entityId) {
        Entity entity = entityRepository.findEntityById(entityId);
        List<Tag> tagList = entity.getTagList();

        if(entity != null) {
            CTag prevCTag = entity.getFirstCTag();
            prevCTag.stopTracking();

            HashMap<Tag, CTag> linkedTags = prevCTag.getLinkedTags();

            for( int i = 1 ; i < tagList.size() ; i++ ) {
                CTag currentCTag = linkedTags.get(tagList.get(i));

                currentCTag.stopTracking();
                linkedTags = currentCTag.getLinkedTags();

                prevCTag = currentCTag;
            }
        }
    }

    private List<TagType> getHierarchyType1() {
        // Can also call another API to get hierarchy list
        return Arrays.asList(TagType.INSTRUMENT, TagType.STATE, TagType.CITY);
    }

    @Override
    public Long getCounts(List<Tag> tagList) {
        if(tagList.isEmpty())
            return Long.valueOf(0);

        CTag prevCTag = cTagRepository.findByTag(tagList.get(0));

        if(prevCTag == null)
            return Long.valueOf(0);

        HashMap<Tag, CTag> linkedTags = prevCTag.getLinkedTags();

        Long resultCount = prevCTag.getCount();

        for( int i = 1 ; i < tagList.size() ; i++) {
            CTag currentCTag = linkedTags.get(tagList.get(i));

            resultCount = Long.min(currentCTag.getCount(), resultCount);

            linkedTags = currentCTag.getLinkedTags();
        }

        return resultCount;
    }
}
