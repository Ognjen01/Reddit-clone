package com.ognjenlazic.Reddit.service.impl;

import com.ognjenlazic.Reddit.model.entity.Community;
import com.ognjenlazic.Reddit.model.entity.User;
import com.ognjenlazic.Reddit.repository.CommunityRepository;
import com.ognjenlazic.Reddit.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    CommunityRepository communityRepository;

    @Override
    public Community findOne(Long id){
        return communityRepository.findById(id).orElseGet(null);
    }

    @Override
    public List<Community> findAll(){
        return communityRepository.findAll();
    }

    @Override
    public List<Long> getAllCommunityUsers(Long id) {
        return communityRepository.findCommunityUsers(id);
    }

    @Override
    public List<Long> getAllCommunityBlockedUsers(Long id) {
        return communityRepository.findCommunityBlockedUser(id);
    }

    @Override
    public Community save(Community community){
        return communityRepository.save(community);
    }

    @Override
    public void addUserToCommunity(Long communityId, Long userId) {
        communityRepository.addUserToCommunity(communityId, userId);
    }

    @Override
    public void removeUserFromCommunity(Long communityId, Long userId) {
        communityRepository.removeUserFromCommunity(communityId, userId);
    }

    @Override
    public void blockUserInCommunity(Long communityId, Long userId) {
        communityRepository.blockUserinCommunity(10L, new Date(), communityId, userId); // Hardcoded value
    }

    @Override
    public void remove(Long id){
        communityRepository.deleteById(id);
    }
}
