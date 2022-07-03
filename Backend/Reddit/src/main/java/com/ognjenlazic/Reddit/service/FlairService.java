package com.ognjenlazic.Reddit.service;

import com.ognjenlazic.Reddit.model.entity.Flair;
import com.ognjenlazic.Reddit.repository.FlairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FlairService {

    Flair findOne(Long id);

    List<Flair> findAll();

    List<Flair> findCommunityFlairs(Long communityId);

    Flair save(Flair flair);

    void remove(Long id);

    void deleteFlairRelations(Long id);

    void addFlairCommunityRelation(Long communityId, Long flairId);

}
