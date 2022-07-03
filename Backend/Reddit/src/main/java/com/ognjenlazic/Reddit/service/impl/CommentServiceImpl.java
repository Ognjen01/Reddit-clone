package com.ognjenlazic.Reddit.service.impl;

import com.ognjenlazic.Reddit.model.entity.Comment;
import com.ognjenlazic.Reddit.repository.CommentRepository;
import com.ognjenlazic.Reddit.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Comment findOne(Long id){
        return commentRepository.findById(id).orElseGet(null);
    }

    @Override
    public List<Comment> findAll(){
        return commentRepository.findAll();
    }

    @Override
    public Set<Comment> findAllPostCommentsSortedByDateAsc(Long postId) {
        return commentRepository.findAllPostCommentsSortedByDateAsc(postId);
    }

    @Override
    public Set<Comment> findAllPostCommentsSortedByDateDesc(Long postId) {
        return commentRepository.findAllPostCommentsSortedByDateDesc(postId);
    }

    @Override
    public Set<Comment> findAllCommentCommentsSortedByDateAsc(Long parentCommentId) {
        return commentRepository.findAllCommentCommentsSortedByDateAsc(parentCommentId);
    }

    @Override
    public Set<Comment> findAllCommentCommentsSortedByDateDesc(Long parentCommentId) {
        return commentRepository.findAllCommentCommentsSortedByDateDesc(parentCommentId);
    }


//    @Override
//    public Set<Comment> findAllCommentsSortedByDateAsc() {
//        return commentRepository.findAllCommentsSortedByDateAsc();
//    }
//
//    @Override
//    public Set<Comment> findAllCommentsSortedByDateDesc() {
//        return commentRepository.findAllCommentsSortedByDateDesc();
//    }

    @Override
    public Set<Comment> findAllPostComments(Long postId) {
        return commentRepository.findAllPostComments(postId);
    }

    @Override
    public Set<Comment> findAllCommentComments(Long commentId) {
        return commentRepository.findAllCommentComments(commentId);
    }

    @Override
    public Comment save(Comment comment){
        return commentRepository.save(comment);
    }

    @Override
    public void remove(Long id){
        commentRepository.deleteById(id);
    }
}
