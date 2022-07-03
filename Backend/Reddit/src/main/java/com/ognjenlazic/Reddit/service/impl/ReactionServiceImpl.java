package com.ognjenlazic.Reddit.service.impl;

import com.ognjenlazic.Reddit.helper.dto.KarmaDto;
import com.ognjenlazic.Reddit.model.entity.Reaction;
import com.ognjenlazic.Reddit.model.enumeration.ReactionType;
import com.ognjenlazic.Reddit.repository.ReactionRepository;
import com.ognjenlazic.Reddit.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ReactionServiceImpl implements ReactionService {

    @Autowired
    ReactionRepository reactionRepository;
    @Autowired
    ReactionService reactionService;

    @Override
    public Reaction findOne(Long id){
        return reactionRepository.findById(id).orElseGet(null);
    }
    @Override
    public List<Reaction> findAll(){
        return reactionRepository.findAll();
    }

    @Override
    public Set<Reaction> findAllReactionsFromPost(Long postId) {
        return reactionRepository.findAlReactionsFromPost(postId);
    }

    @Override
    public Set<Reaction> findAllReactionsFromComment(Long commentId) {
        return reactionRepository.findAlReactionsFromComment(commentId);
    }

    @Override
    public Set<Reaction> findAllReactionsFromUser(Long userId) {
        return reactionRepository.findAlReactionsFromUser(userId);
    }

    @Override
    public Reaction save(Reaction reaction){
        return reactionRepository.save(reaction);
    }
    @Override
    public void remove(Long id){
        reactionRepository.deleteById(id);
    }

    @Override
    public KarmaDto getPostKarma(Long postId) {

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

        return karmaDto;
    }

    @Override
    public KarmaDto getCommentKarma(Long commentId) {
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

        return karmaDto;
    }
}
