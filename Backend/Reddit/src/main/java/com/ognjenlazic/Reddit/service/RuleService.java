package com.ognjenlazic.Reddit.service;

import com.ognjenlazic.Reddit.model.entity.Rule;
import com.ognjenlazic.Reddit.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

public interface RuleService {

    Rule findOne(Long id);

    List<Rule> findAll();

    Rule save(Rule rule);

    void remove(Long id);

    Set<Rule> findAllWithCommunityId(Long communityId);
}
