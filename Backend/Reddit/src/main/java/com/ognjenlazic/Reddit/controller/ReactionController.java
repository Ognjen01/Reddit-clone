package com.ognjenlazic.Reddit.controller;

import com.ognjenlazic.Reddit.helper.dto.KarmaDto;
import com.ognjenlazic.Reddit.model.dto.ReactionDto;
import com.ognjenlazic.Reddit.model.entity.Post;
import com.ognjenlazic.Reddit.model.entity.Reaction;
import com.ognjenlazic.Reddit.model.enumeration.ReactionType;
import com.ognjenlazic.Reddit.service.CommentService;
import com.ognjenlazic.Reddit.service.PostService;
import com.ognjenlazic.Reddit.service.ReactionService;
import com.ognjenlazic.Reddit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "api/reaction")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<List<ReactionDto>> getReactions(){
        List<Reaction> reactions = reactionService.findAll();
        List<ReactionDto> reactionDtos = new ArrayList<>();
        System.out.println("BROJ REAKCIJA "+reactions.size());
        for (Reaction reaction: reactions) {
            reactionDtos.add(new ReactionDto(reaction));
        }
        return new ResponseEntity<>(reactionDtos, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ReactionDto> saveReaction(@RequestBody ReactionDto reactionDto) {

        System.out.println(reactionDto.toString());

        Reaction reaction = new Reaction();

        if (reactionDto.getPostId() == null) {
            reaction.setPost(null);
        } else {
            reaction.setPost(postService.findOne(reactionDto.getPostId()));
        }

        if (reactionDto.getCommentId() == null) {
            reaction.setComment(null);
        } else {
            reaction.setComment(commentService.findOne(reactionDto.getCommentId()));
        }

        reaction.setReactionId(reactionDto.getReactionId());
        reaction.setReactionType(reactionDto.getReactionType());
        reaction.setTimestamp(reactionDto.getTimestamp());
        reaction.setUser(userService.findOne(reactionDto.getUserId()));
        reaction = reactionService.save(reaction);
        return new ResponseEntity<>(new ReactionDto(reaction), HttpStatus.CREATED);
    }

    @GetMapping(value = "/post/{postId}")
    public ResponseEntity<List<ReactionDto>> getAllReactionsFromPost(@PathVariable Long postId) {

        Set<Reaction> reactions = reactionService.findAllReactionsFromPost(postId);
        List<ReactionDto> reactionDtos = new ArrayList<>();

        for (Reaction reaction: reactions) {
            reactionDtos.add(new ReactionDto(reaction));
        }
        return new ResponseEntity<>(reactionDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/comment/{commentId}")
    public ResponseEntity<List<ReactionDto>> getAllReactionsFromComment(@PathVariable Long commentId) {

        Set<Reaction> reactions = reactionService.findAllReactionsFromComment(commentId);
        List<ReactionDto> reactionDtos = new ArrayList<>();

        for (Reaction reaction: reactions) {
            reactionDtos.add(new ReactionDto(reaction));
        }
        return new ResponseEntity<>(reactionDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/comment-karma/{commentId}")
    public ResponseEntity<KarmaDto> getCommentKarma(@PathVariable Long commentId) {

        Set<Reaction> reactions = reactionService.findAllReactionsFromComment(commentId);
        List<Reaction> upvoteReactions = new ArrayList<>();
        List<Reaction> downvoteReactions = new ArrayList<>();

        for (Reaction reaction: reactions) {
            if(reaction.getReactionType() == ReactionType.UPVOTE){
                upvoteReactions.add(reaction);
            } else {
                downvoteReactions.add(reaction);
            }
        }

        KarmaDto karmaDto = new KarmaDto();
        karmaDto.setEntityId(commentId);
        karmaDto.setNumber(upvoteReactions.size() - downvoteReactions.size());

        return new ResponseEntity<>(karmaDto, HttpStatus.OK);
    }

    @GetMapping(value = "/post-karma/{postId}")
    public ResponseEntity<KarmaDto> getPostKarma(@PathVariable Long postId) {

        Set<Reaction> reactions = reactionService.findAllReactionsFromPost(postId);
        List<Reaction> upvoteReactions = new ArrayList<>();
        List<Reaction> downvoteReactions = new ArrayList<>();

        for (Reaction reaction: reactions) {
            if(reaction.getReactionType() == ReactionType.UPVOTE){
                upvoteReactions.add(reaction);
            } else {
                downvoteReactions.add(reaction);
            }
        }

        KarmaDto karmaDto = new KarmaDto();
        karmaDto.setEntityId(postId);
        karmaDto.setNumber(upvoteReactions.size() - downvoteReactions.size());

        return new ResponseEntity<>(karmaDto, HttpStatus.OK);
    }

    @GetMapping(value = "/user-karma/{userId}")
    public ResponseEntity<KarmaDto> getUserKarma(@PathVariable Long userId) {

        Set<Reaction> reactions = reactionService.findAllReactionsFromUser(userId);
        List<Reaction> upvoteReactions = new ArrayList<>();
        List<Reaction> downvoteReactions = new ArrayList<>();

        for (Reaction reaction: reactions) {
            if(reaction.getReactionType() == ReactionType.UPVOTE){
                upvoteReactions.add(reaction);
            } else {
                downvoteReactions.add(reaction);
            }
        }

        KarmaDto karmaDto = new KarmaDto();
        karmaDto.setEntityId(userId);
        karmaDto.setNumber(upvoteReactions.size() - downvoteReactions.size());

        return new ResponseEntity<>(karmaDto, HttpStatus.OK);
    }
}
