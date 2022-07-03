package com.ognjenlazic.Reddit.helper.service;

import com.ognjenlazic.Reddit.model.dto.CommunityDto;
import com.ognjenlazic.Reddit.model.entity.Community;
import com.ognjenlazic.Reddit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommunityConversionService {

    @Autowired
    private UserService userService;

    public Community communityDtoToCommunity(CommunityDto communityDto){
        Community community = new Community();
        community.setName(communityDto.getName());
        community.setDescription(communityDto.getDescription());
        community.setModerator(userService.findOne(communityDto.getModeratorId()));
        community.setCreationDate(new Date());
        //community.setRules(); Add list of ruler CRUD for this one
        // I have to add rules in DTO and parse it in List

        return community;
    }
}
