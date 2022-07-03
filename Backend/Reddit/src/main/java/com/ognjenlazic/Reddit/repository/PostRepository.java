package com.ognjenlazic.Reddit.repository;

import com.ognjenlazic.Reddit.model.entity.Post;
import com.ognjenlazic.Reddit.model.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(nativeQuery = true, value = "select * from reddit.post order by creation_date asc")
    public Set<Post> findAllPostSortedByDateAsc();

    @Query(nativeQuery = true, value = "select * from reddit.post order by creation_date desc")
    public Set<Post> findAllPostSortedByDateDesc();

    @Query(nativeQuery = true, value = "select * from reddit.post where user = ?")
    public Set<Post> findAllUserPosts(Long userId);

    @Query(nativeQuery = true, value = "select * from reddit.post where community = ?")
    public Set<Post> findAllCommunityPosts(Long communityId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from reddit.post where community = ?")
    public void deleteAllCommunityPosts(Long communityId);
}