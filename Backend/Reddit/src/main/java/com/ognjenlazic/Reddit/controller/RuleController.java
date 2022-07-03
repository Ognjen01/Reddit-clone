package com.ognjenlazic.Reddit.controller;

import com.ognjenlazic.Reddit.helper.dto.FlairCommunityRelationDto;
import com.ognjenlazic.Reddit.helper.dto.RuleCommunityRelationDto;
import com.ognjenlazic.Reddit.model.dto.FlairDto;
import com.ognjenlazic.Reddit.model.dto.PostDto;
import com.ognjenlazic.Reddit.model.dto.ReportDto;
import com.ognjenlazic.Reddit.model.dto.RuleDto;
import com.ognjenlazic.Reddit.model.entity.Post;
import com.ognjenlazic.Reddit.model.entity.Report;
import com.ognjenlazic.Reddit.model.entity.Rule;
import com.ognjenlazic.Reddit.service.CommunityService;
import com.ognjenlazic.Reddit.service.RuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/rule")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @Autowired
    private CommunityService communityService;

    Logger logger = LoggerFactory.getLogger(CommunityController.class);

    @GetMapping
    public ResponseEntity<List<RuleDto>> getRule(){
        List<Rule> ruleList = ruleService.findAll();
        List<RuleDto> ruleDtos = new ArrayList<>();

        for (Rule rule: ruleList) {
            ruleDtos.add(new RuleDto(rule));
        }
        return new ResponseEntity<>(ruleDtos, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<RuleDto> saveRule(@RequestBody RuleDto ruleDto) {
        System.out.println(ruleDto.toString());
        Rule rule = new Rule();

        rule.setRuleId(ruleDto.getRuleId());
        rule.setDescription(ruleDto.getDescription());
        rule.setCommunity(communityService.findOne(ruleDto.getCommunityId()));

        rule = ruleService.save(rule);
        logger.info("New rule created");
        return new ResponseEntity<>(new RuleDto(rule), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<RuleDto> updateRule(@RequestBody RuleDto ruleDto) {

        Rule rule = ruleService.findOne(ruleDto.getRuleId());

        if (rule == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        rule.setDescription(ruleDto.getDescription());
        // Add all parameters

        rule = ruleService.save(rule);
        return new ResponseEntity<>(new RuleDto(rule), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {

        Rule rule = ruleService.findOne(id);

        if (rule != null) {
            ruleService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
