package com.ognjenlazic.Reddit.repository;

import com.ognjenlazic.Reddit.model.entity.Community;
import com.ognjenlazic.Reddit.model.entity.Rule;
import com.ognjenlazic.Reddit.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    @Query(nativeQuery = true, value = "select reddit.community_users.users_user_id from reddit.community_users where community_community_id = ?")
    public List<Long> findCommunityUsers(Long communityId);

    @Query(nativeQuery = true, value = "select reddit.community_users.community_community_id from reddit.community_users where users_user_id = ?")
    public List<Long> findUserCommunities(Long userId);

    @Query(nativeQuery = true, value = "select user from reddit.banned where community = ?")
    public List<Long> findCommunityBlockedUser(Long communityId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from reddit.community_users where community_community_id = ? and users_user_id = ?")
    public void removeUserFromCommunity(Long communityId, Long userId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into reddit.community_users (community_community_id, users_user_id) values (?, ?);")
    public void addUserToCommunity(Long communityId, Long userId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into reddit.banned (ban_id, timestamp, user, community, banneds, banned) values (?, ?, ?, ?, null, null);")
    public void blockUserinCommunity(Long banId, Date banDate, Long communityId, Long userId);
}