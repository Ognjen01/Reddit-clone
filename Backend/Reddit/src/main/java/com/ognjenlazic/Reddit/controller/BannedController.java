package com.ognjenlazic.Reddit.controller;

import com.ognjenlazic.Reddit.model.dto.BannedDto;
import com.ognjenlazic.Reddit.model.entity.Banned;
import com.ognjenlazic.Reddit.service.BannedService;
import com.ognjenlazic.Reddit.service.CommunityService;
import com.ognjenlazic.Reddit.service.UserService;
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
@RequestMapping(value = "api/banned")
public class BannedController {

    @Autowired
    private BannedService bannedService;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(CommunityController.class);


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<BannedDto>> getBans(){
        List<Banned> banneds = bannedService.findAll();
        List<BannedDto> communityDtos = new ArrayList<>();

        for (Banned banned: banneds) {
            communityDtos.add(new BannedDto(banned));
        }
        return new ResponseEntity<>(communityDtos, HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<BannedDto> createBan(@RequestBody BannedDto communityDto) {

        Banned ban = new Banned();
        ban.setBanId(communityDto.getBanId());
        ban.setTimestamp(communityDto.getTimestamp());
        ban.setBy(userService.findOne(communityDto.getUserId()));
        ban.setCommunity(communityService.findOne(communityDto.getCommunityId()));

        ban = bannedService.save(ban);
        logger.info("New ban created");
        return new ResponseEntity<>(new BannedDto(ban), HttpStatus.CREATED);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteBan(@PathVariable Long id) {

        Banned ban = bannedService.findOne(id);

        if (ban != null) {
            bannedService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
