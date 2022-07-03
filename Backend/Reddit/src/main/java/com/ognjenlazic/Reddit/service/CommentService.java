package com.ognjenlazic.Reddit.service;

import com.ognjenlazic.Reddit.model.entity.Comment;
import com.ognjenlazic.Reddit.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


public interface CommentService {


    Comment findOne(Long id);

    List<Comment> findAll();

    Set<Comment> findAllPostCommentsSortedByDateAsc(Long postId);
    Set<Comment> findAllPostCommentsSortedByDateDesc(Long postId);

    Set<Comment> findAllCommentCommentsSortedByDateAsc(Long parentCommentId);
    Set<Comment> findAllCommentCommentsSortedByDateDesc(Long parentCommentId);

    Set<Comment> findAllPostComments(Long postId);

    Set<Comment> findAllCommentComments(Long postId);

    Comment save(Comment comment);

    void remove(Long id);
}
