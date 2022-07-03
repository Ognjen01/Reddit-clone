package com.ognjenlazic.Reddit.repository;

import com.ognjenlazic.Reddit.model.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface RuleRepository extends JpaRepository<Rule, Long> {

    @Query(nativeQuery = true, value = "select * from reddit.rule where community = ?")
    public Set<Rule> findAllWithCommunityId(Long communityId);
}