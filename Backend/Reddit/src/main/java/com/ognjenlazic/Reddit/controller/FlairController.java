package com.ognjenlazic.Reddit.controller;

import com.ognjenlazic.Reddit.helper.dto.FlairCommunityRelationDto;
import com.ognjenlazic.Reddit.model.dto.CommentDto;
import com.ognjenlazic.Reddit.model.dto.FlairDto;
import com.ognjenlazic.Reddit.model.dto.PostDto;
import com.ognjenlazic.Reddit.model.entity.Comment;
import com.ognjenlazic.Reddit.model.entity.Flair;
import com.ognjenlazic.Reddit.model.entity.Post;
import com.ognjenlazic.Reddit.service.FlairService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/flair")
public class FlairController {

    @Autowired
    private FlairService flairService;

    Logger logger = LoggerFactory.getLogger(CommunityController.class);

    @GetMapping
    public ResponseEntity<List<FlairDto>> getFlairs(){
        List<Flair> flairs = flairService.findAll();
        List<FlairDto> flairDtos = new ArrayList<>();

        for (Flair flair: flairs) {
            flairDtos.add(new FlairDto(flair));
        }
        return new ResponseEntity<>(flairDtos, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<FlairDto> createFlair (@RequestBody FlairDto flairDto) {

        System.out.println(flairDto.toString());

        Flair flair = new Flair();
        flair.setFlairId(flairDto.getFlairId());
        flair.setName(flairDto.getName());

        flair = flairService.save(flair);
        logger.info("New flair created");
        return new ResponseEntity<>(new FlairDto(flair), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<FlairDto> updatePost(@RequestBody FlairDto flairDto) {

        Flair flair = flairService.findOne(flairDto.getFlairId());

        if (flair == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        flair.setName(flairDto.getName());

        flair = flairService.save(flair);
        logger.info("Flair updated");

        return new ResponseEntity<>(new FlairDto(flair), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteFlair(@PathVariable Long id) {

        Flair flair = flairService.findOne(id);

        if (flair != null) {
            flairService.deleteFlairRelations(id);
            flairService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = "application/json", value = "/flair-community")
    public ResponseEntity<Void> createFlairCommunityRealtion (@RequestBody FlairCommunityRelationDto relationDto) {
        flairService.addFlairCommunityRelation(relationDto.getCommunityId(), relationDto.getFlairId());
        logger.info("Flair added in community");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FlairDto> getFlairById(@PathVariable Long id) {

        Flair flair = flairService.findOne(id);
        FlairDto flairDto = new FlairDto(flair);

        return new ResponseEntity<>(flairDto, HttpStatus.OK);
    }


    @GetMapping(value = "community/{id}")
    public ResponseEntity<List<FlairDto>> getCommunityFlairs(@PathVariable Long id) {
        List<Flair> flairs = flairService.findCommunityFlairs(id);
        List<FlairDto> flairDtos = new ArrayList<>();

        for (Flair flair: flairs) {
            flairDtos.add(new FlairDto(flair));
        }
        return new ResponseEntity<>(flairDtos, HttpStatus.OK);
    }
}
