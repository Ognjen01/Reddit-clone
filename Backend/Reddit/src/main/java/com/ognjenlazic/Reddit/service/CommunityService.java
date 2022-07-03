package com.ognjenlazic.Reddit.service;

import com.ognjenlazic.Reddit.model.entity.Community;
import com.ognjenlazic.Reddit.model.entity.User;
import com.ognjenlazic.Reddit.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CommunityService {

    Community findOne(Long id);

    List<Community> findAll();

    List<Long> getAllCommunityUsers(Long id);

    List<Long> getAllCommunityBlockedUsers(Long id);

    Community save(Community community);

    void addUserToCommunity(Long communityId, Long userId);

    void removeUserFromCommunity(Long communityId, Long userId);

    void blockUserInCommunity(Long communityId, Long userId);

    void remove(Long id);
}
