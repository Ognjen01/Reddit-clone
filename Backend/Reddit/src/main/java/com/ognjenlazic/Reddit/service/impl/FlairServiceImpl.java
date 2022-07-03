package com.ognjenlazic.Reddit.service.impl;

import com.ognjenlazic.Reddit.model.entity.Flair;
import com.ognjenlazic.Reddit.repository.FlairRepository;
import com.ognjenlazic.Reddit.service.FlairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlairServiceImpl implements FlairService {
    @Autowired
    FlairRepository flairRepository;

    @Override
    public Flair findOne(Long id){
        return flairRepository.findById(id).orElseGet(null);
    }
    @Override
    public List<Flair> findAll(){
        return flairRepository.findAll();
    }

    @Override
    public List<Flair> findCommunityFlairs(Long comunityId) {
        return flairRepository.findCommunityFlairs(comunityId);
    }

    @Override
    public Flair save(Flair flair){
        return flairRepository.save(flair);
    }
    @Override
    public void remove(Long id){
        flairRepository.deleteById(id);
    }

    @Override
    public void deleteFlairRelations(Long id) {
        flairRepository.deleteFlairRelations(id);
    }

    @Override
    public void addFlairCommunityRelation(Long communityId, Long flairId) {
        flairRepository.addCommunityFlairRelation(communityId, flairId);
    }
}
