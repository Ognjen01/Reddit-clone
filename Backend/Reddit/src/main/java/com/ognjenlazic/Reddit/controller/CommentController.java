package com.ognjenlazic.Reddit.controller;

import com.ognjenlazic.Reddit.helper.dto.KarmaDto;
import com.ognjenlazic.Reddit.model.dto.BannedDto;
import com.ognjenlazic.Reddit.model.dto.CommentDto;
import com.ognjenlazic.Reddit.model.dto.PostDto;
import com.ognjenlazic.Reddit.model.entity.Banned;
import com.ognjenlazic.Reddit.model.entity.Comment;
import com.ognjenlazic.Reddit.model.entity.Post;
import com.ognjenlazic.Reddit.service.CommentService;
import com.ognjenlazic.Reddit.service.PostService;
import com.ognjenlazic.Reddit.service.ReactionService;
import com.ognjenlazic.Reddit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private ReactionService reactionService;

    Logger logger = LoggerFactory.getLogger(CommunityController.class);


    @GetMapping
    public ResponseEntity<List<CommentDto>> getComments(){
        List<Comment> comments = commentService.findAll();
        List<CommentDto> commentDtos = new ArrayList<>();

        for (Comment comment: comments) {
            commentDtos.add(new CommentDto(comment));
        }
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<CommentDto> saveComment(@RequestBody CommentDto commentDto) {

        System.out.println(commentDto.toString());

        Comment comment = new Comment();

        comment.setCommentId(commentDto.getCommentId());
        comment.setText(commentDto.getText());
        comment.setTimestamp(commentDto.getTimestamp());
        comment.setUser(userService.findOne(commentDto.getUserId()));
        comment.setDeleted(false);

        if (commentDto.getRepliesToCommentId() == null){
            comment.setComment(null);
        } else {
            comment.setComment(commentService.findOne(commentDto.getRepliesToCommentId()));
        }

        if (commentDto.getPostId() == null) {
            comment.setPost(null);
        } else {
            comment.setPost(postService.findOne(commentDto.getPostId()));
        }

        comment = commentService.save(comment);
        logger.info("New comment created");
        return new ResponseEntity<>(new CommentDto(comment), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto) {

        Comment comment = commentService.findOne(commentDto.getCommentId());

        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //post.setTitle(postDto.getTitle());
        comment.setText(commentDto.getText());
        comment.setTimestamp(commentDto.getTimestamp());

        comment = commentService.save(comment);
        logger.info("Comment updated");
        return new ResponseEntity<>(new CommentDto(comment), HttpStatus.OK);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<List<CommentDto>> getPostComments(@PathVariable Long id){
        Set<Comment> comments = commentService.findAllPostComments(id);
        List<CommentDto> commentDtos = new ArrayList<>();

        for (Comment comment: comments) {
            commentDtos.add(new CommentDto(comment));
        }
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id){
        Comment comment = commentService.findOne(id);
        CommentDto commentDto = new CommentDto(comment);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<List<CommentDto>> getCommentComments(@PathVariable Long id){
        Set<Comment> comments = commentService.findAllCommentComments(id);
        List<CommentDto> commentDtos = new ArrayList<>();

        for (Comment comment: comments) {
            commentDtos.add(new CommentDto(comment));
        }
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {

        Comment comment = commentService.findOne(id);
        if (comment != null) {
            commentService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "post/{postId}/sorted-by-date-asc")
    public ResponseEntity<List<CommentDto>> getPostCommentSortedByDateAsc(@PathVariable Long postId) {
        Set<Comment> comments = commentService.findAllPostCommentsSortedByDateAsc(postId);
        List<CommentDto> commentDtos = new ArrayList<>();

        for (Comment comment: comments) {
            commentDtos.add(new CommentDto(comment));
        }
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @GetMapping(value = "post/{postId}/sorted-by-date-desc")
    public ResponseEntity<List<CommentDto>> getPostCommentSortedByDateDesc(@PathVariable Long postId) {
        Set<Comment> comments = commentService.findAllPostCommentsSortedByDateDesc(postId);
        List<CommentDto> commentDtos = new ArrayList<>();

        for (Comment comment: comments) {
            commentDtos.add(new CommentDto(comment));
        }
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @GetMapping(value = "comment/{commentId}/sorted-by-date-asc")
    public ResponseEntity<List<CommentDto>> getCommentCommentsSortedByDateAsc(@PathVariable Long commentId) {
        Set<Comment> comments = commentService.findAllCommentCommentsSortedByDateAsc(commentId);
        List<CommentDto> commentDtos = new ArrayList<>();

        for (Comment comment: comments) {
            commentDtos.add(new CommentDto(comment));
        }
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @GetMapping(value = "comment/{commentId}/sorted-by-date-desc")
    public ResponseEntity<List<CommentDto>> getCommentCommentsSortedByDateDesc(@PathVariable Long commentId) {
        Set<Comment> comments = commentService.findAllCommentCommentsSortedByDateDesc(commentId);
        List<CommentDto> commentDtos = new ArrayList<>();

        for (Comment comment: comments) {
            commentDtos.add(new CommentDto(comment));
        }
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @GetMapping(value = "post/{postId}/sorted-by-unpopular")
    public ResponseEntity<List<CommentDto>> getPostCommentsSortedByUnpopular(@PathVariable Long postId) {
        Set<Comment> comments = commentService.findAllPostComments(postId);
        List<KarmaDto> allCommentsKarma = new ArrayList<>();

        List<CommentDto> sortedComments = new ArrayList<>();

        for (Comment comment: comments) {
            allCommentsKarma.add(reactionService.getCommentKarma(comment.getCommentId()));
        }

        allCommentsKarma.sort(Comparator.comparing(KarmaDto::getNumber).reversed());

        for (KarmaDto karma: allCommentsKarma) {
            Comment comment = commentService.findOne(karma.getEntityId());
            sortedComments.add(new CommentDto(comment));
        }
        return new ResponseEntity<>(sortedComments, HttpStatus.OK);
    }

    @GetMapping(value = "post/{postId}/sorted-by-popular")
    public ResponseEntity<List<CommentDto>> getPostCommentsSortedByPopular(@PathVariable Long postId) {
        Set<Comment> comments = commentService.findAllPostComments(postId);
        List<KarmaDto> allCommentsKarma = new ArrayList<>();

        List<CommentDto> sortedComments = new ArrayList<>();

        for (Comment comment: comments) {
            allCommentsKarma.add(reactionService.getCommentKarma(comment.getCommentId()));
        }

        allCommentsKarma.sort(Comparator.comparing(KarmaDto::getNumber));

        for (KarmaDto karma: allCommentsKarma) {
            Comment comment = commentService.findOne(karma.getEntityId());
            sortedComments.add(new CommentDto(comment));
        }
        return new ResponseEntity<>(sortedComments, HttpStatus.OK);
    }

    @GetMapping(value = "comment/{commentId}/sorted-by-unpopular")
    public ResponseEntity<List<CommentDto>> getCommentCommentsSortedByUnpopular(@PathVariable Long commentId) {
        Set<Comment> comments = commentService.findAllCommentComments(commentId);
        List<KarmaDto> allCommentsKarma = new ArrayList<>();

        List<CommentDto> sortedComments = new ArrayList<>();

        for (Comment comment: comments) {
            allCommentsKarma.add(reactionService.getCommentKarma(comment.getCommentId()));
        }

        allCommentsKarma.sort(Comparator.comparing(KarmaDto::getNumber).reversed());

        for (KarmaDto karma: allCommentsKarma) {
            Comment comment = commentService.findOne(karma.getEntityId());
            sortedComments.add(new CommentDto(comment));
        }
        return new ResponseEntity<>(sortedComments, HttpStatus.OK);
    }

    @GetMapping(value = "comment/{commentId}/sorted-by-popular")
    public ResponseEntity<List<CommentDto>> getCommentCommentsSortedByPopular(@PathVariable Long commentId) {
        Set<Comment> comments = commentService.findAllCommentComments(commentId);
        List<KarmaDto> allCommentsKarma = new ArrayList<>();

        List<CommentDto> sortedComments = new ArrayList<>();

        for (Comment comment: comments) {
            allCommentsKarma.add(reactionService.getCommentKarma(comment.getCommentId()));
        }

        allCommentsKarma.sort(Comparator.comparing(KarmaDto::getNumber));

        for (KarmaDto karma: allCommentsKarma) {
            Comment comment = commentService.findOne(karma.getEntityId());
            sortedComments.add(new CommentDto(comment));
        }
        return new ResponseEntity<>(sortedComments, HttpStatus.OK);
    }

    @GetMapping(value = "/sorted-by-popular")
    public ResponseEntity<List<CommentDto>> getPostsSortedByPopular() {
        List<Comment> comments = commentService.findAll();
        List<KarmaDto> allPostsKarma = new ArrayList<>();

        List<CommentDto> sortedPosts = new ArrayList<>();

        for (Comment comment: comments) {
            allPostsKarma.add(reactionService.getPostKarma(comment.getCommentId()));
        }

        allPostsKarma.sort(Comparator.comparing(KarmaDto::getNumber));

        for (KarmaDto karma: allPostsKarma) {
            Comment comment = commentService.findOne(karma.getEntityId());
            sortedPosts.add(new CommentDto(comment));
        }
        return new ResponseEntity<>(sortedPosts, HttpStatus.OK);
    }

    @GetMapping(value = "/sorted-by-unpopular")
    public ResponseEntity<List<CommentDto>> getPostsSortedByUnpopular() {
        List<Comment> comments = commentService.findAll();
        List<KarmaDto> allPostsKarma = new ArrayList<>();

        List<CommentDto> sortedPosts = new ArrayList<>();

        for (Comment comment: comments) {
            allPostsKarma.add(reactionService.getPostKarma(comment.getCommentId()));
        }

        allPostsKarma.sort(Comparator.comparing(KarmaDto::getNumber).reversed());

        for (KarmaDto karma: allPostsKarma) {
            Comment comment = commentService.findOne(karma.getEntityId());
            sortedPosts.add(new CommentDto(comment));
        }
        return new ResponseEntity<>(sortedPosts, HttpStatus.OK);
    }
}
