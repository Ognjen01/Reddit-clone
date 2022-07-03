package com.ognjenlazic.Reddit.service;

import com.ognjenlazic.Reddit.helper.dto.KarmaDto;
import com.ognjenlazic.Reddit.model.entity.Reaction;
import com.ognjenlazic.Reddit.repository.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

public interface ReactionService {

    Reaction findOne(Long id);

    List<Reaction> findAll();

    Set<Reaction> findAllReactionsFromPost(Long postId);

    Set<Reaction> findAllReactionsFromComment(Long commentId);

    Set<Reaction> findAllReactionsFromUser(Long userId);

    Reaction save(Reaction reaction);

    void remove(Long id);

    KarmaDto getPostKarma(Long postId);

    KarmaDto getCommentKarma(Long postId);
}
