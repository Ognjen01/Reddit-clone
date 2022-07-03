package com.ognjenlazic.Reddit.repository;

import com.ognjenlazic.Reddit.model.entity.Comment;
import com.ognjenlazic.Reddit.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(nativeQuery = true, value = "select * from reddit.comment where post = ? order by timestamp asc")
    public Set<Comment> findAllPostCommentsSortedByDateAsc(Long postId);

    @Query(nativeQuery = true, value = "select * from reddit.comment where post = ? order by timestamp desc")
    public Set<Comment> findAllPostCommentsSortedByDateDesc(Long postId);

    @Query(nativeQuery = true, value = "select * from reddit.comment where comment = ? order by timestamp asc")
    public Set<Comment> findAllCommentCommentsSortedByDateAsc(Long parentCommentId);

    @Query(nativeQuery = true, value = "select * from reddit.comment where comment = ? order by timestamp desc")
    public Set<Comment> findAllCommentCommentsSortedByDateDesc(Long parentCommentId);

    @Query(nativeQuery = true, value = "select * from reddit.comment where comment = ?")
    public Set<Comment> findAllCommentComments(Long commentId);

    @Query(nativeQuery = true, value = "select * from reddit.comment where post = ?")
    public Set<Comment> findAllPostComments(Long postId);
}