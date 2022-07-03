package com.ognjenlazic.Reddit.service.impl;

import com.ognjenlazic.Reddit.model.entity.Rule;
import com.ognjenlazic.Reddit.repository.RuleRepository;
import com.ognjenlazic.Reddit.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RuleServiceImpl implements RuleService {
    @Autowired
    RuleRepository ruleRepository;
    @Override
    public Rule findOne(Long id){
        return ruleRepository.findById(id).orElseGet(null);
    }
    @Override
    public List<Rule> findAll(){
        return ruleRepository.findAll();
    }
    @Override
    public Rule save(Rule rule){
        return ruleRepository.save(rule);
    }
    @Override
    public void remove(Long id){
        ruleRepository.deleteById(id);
    }

    @Override
    public Set<Rule> findAllWithCommunityId(Long communityId) {
        return ruleRepository.findAllWithCommunityId(communityId);
    }
}
