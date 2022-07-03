package com.ognjenlazic.Reddit.repository;

import com.ognjenlazic.Reddit.model.entity.Reaction;
import com.ognjenlazic.Reddit.model.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    @Query(nativeQuery = true, value = "select * from reddit.reaction where post = ?")
    public Set<Reaction> findAlReactionsFromPost(Long postId);

    @Query(nativeQuery = true, value = "select * from reddit.reaction where comment = ?")
    public Set<Reaction> findAlReactionsFromComment(Long commentId);

    @Query(nativeQuery = true, value = "select * from reddit.reaction where user = ?")
    public Set<Reaction> findAlReactionsFromUser(Long userId);
}