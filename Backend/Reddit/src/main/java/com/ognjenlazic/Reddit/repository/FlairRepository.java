package com.ognjenlazic.Reddit.repository;

import com.ognjenlazic.Reddit.model.entity.Comment;
import com.ognjenlazic.Reddit.model.entity.Flair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface FlairRepository extends JpaRepository<Flair, Long> {

    @Query(nativeQuery = true, value = "select * from reddit.flair where flair_id in (select flairs_flair_id from reddit.community_flairs where community_community_id = ?)")
    public List<Flair> findCommunityFlairs(Long communityId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into reddit.community_flairs (community_community_id, flairs_flair_id) values (?, ?)")
    public void addCommunityFlairRelation(Long communityId, Long flairId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from reddit.community_flairs where flairs_flair_id = ?")
    public void deleteFlairRelations(Long flairId);
}