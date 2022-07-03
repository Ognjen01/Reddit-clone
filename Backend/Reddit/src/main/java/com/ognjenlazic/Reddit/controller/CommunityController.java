package com.ognjenlazic.Reddit.controller;

import com.ognjenlazic.Reddit.helper.service.CommunityConversionService;
import com.ognjenlazic.Reddit.model.dto.CommunityDto;
import com.ognjenlazic.Reddit.model.dto.UserDto;
import com.ognjenlazic.Reddit.model.entity.Community;
import com.ognjenlazic.Reddit.model.entity.User;
import com.ognjenlazic.Reddit.service.CommunityService;
import com.ognjenlazic.Reddit.service.PostService;
import com.ognjenlazic.Reddit.service.RuleService;
import com.ognjenlazic.Reddit.service.impl.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private PostService postService;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private CommunityConversionService communityConversionService;

    @Autowired
    EmailSenderService emailSenderService;

    Logger logger = LoggerFactory.getLogger(CommunityController.class);

    @GetMapping
    public ResponseEntity<List<CommunityDto>> getCommunities(){
        List<Community> communities = communityService.findAll();
        List<CommunityDto> communityDtos = new ArrayList<>();

        for (Community community: communities) {
            community.setRules(ruleService.findAllWithCommunityId(community.getCommunityId()));
            communityDtos.add(new CommunityDto(community));
        }
        return new ResponseEntity<>(communityDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommunityDto> getOneById(@PathVariable Long id){

        Community community = communityService.findOne(id);
        //System.out.println(community);
        community.setRules(ruleService.findAllWithCommunityId(community.getCommunityId()));
        CommunityDto communityDto = new CommunityDto(community);

        return new ResponseEntity<>(communityDto, HttpStatus.OK);
    }

    @GetMapping("community-users/{id}")
    public ResponseEntity<List<Long>> getAllCommunityUsers(@PathVariable Long id){
        List<Long> users = communityService.getAllCommunityUsers(id);
//        List<UserDto> userDtos = new ArrayList<>();
//
//        for (User user: users) {
//            userDtos.add(new UserDto(user));
//        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<CommunityDto> createCommunity(@RequestBody CommunityDto communityDto) {

        Community community = communityConversionService.communityDtoToCommunity(communityDto);

        community = communityService.save(community);
        logger.info("New community added");
        return new ResponseEntity<>(new CommunityDto(community), HttpStatus.CREATED);
    }

    @PostMapping("community/{communityId}/user/{userId}")
    public void addUserToCommunity(@PathVariable Long communityId, @PathVariable Long userId) {
        communityService.addUserToCommunity(communityId, userId);
        logger.info("User joined community");

    }

    @PostMapping("block/community/{communityId}/user/{userId}")
    public void blockUserInCommunity(@PathVariable Long communityId, @PathVariable Long userId) {
        communityService.blockUserInCommunity(communityId, userId);
        emailSenderService.sendEmail("lazicognjen2001@gmail.com", "You have been blocked", "Moderator blocked you in one of the commuinty, now you can not react on posts, comment them or report."); // Test email
//        emailSenderService.sendEmail(currentUser.get().getEmail(), "New login", "New login in your account");
        logger.info("User blocked");

    }

    @GetMapping("community-blocked-users/{communityId}")
    public ResponseEntity<List<Long>> getAllCommunityBlockedUsers(@PathVariable Long communityId) {
        List<Long> users = communityService.getAllCommunityBlockedUsers(communityId);
        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @DeleteMapping("community/{communityId}/user/{userId}")
    public void removeUserFromCommunity(@PathVariable Long communityId, @PathVariable Long userId) {
        communityService.removeUserFromCommunity(communityId, userId);
        logger.info("User left community");

    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<CommunityDto> updateCommunity(@RequestBody CommunityDto communityDto) {
        System.out.println(communityDto);
        Community community = communityService.findOne(communityDto.getCommunityId());

        if (community == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        community.setName(communityDto.getName());
        community.setDescription(communityDto.getDescription());
        community.setSuspended(communityDto.isSuspended());
        community.setSuspendedReason(communityDto.getSuspendedReason());

        community = communityService.save(community);
        logger.info("Community updated");

        return new ResponseEntity<>(new CommunityDto(community), HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCommunity(@PathVariable Long id) {

        Community community = communityService.findOne(id);

        if (community != null) {
            communityService.remove(id);
            postService.deleteAllCommunityPosts(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}