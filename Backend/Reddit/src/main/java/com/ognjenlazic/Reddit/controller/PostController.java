package com.ognjenlazic.Reddit.controller;

import com.ognjenlazic.Reddit.helper.dto.KarmaDto;
import com.ognjenlazic.Reddit.model.dto.PostDto;
import com.ognjenlazic.Reddit.model.dto.UserDto;
import com.ognjenlazic.Reddit.model.entity.Community;
import com.ognjenlazic.Reddit.model.entity.Post;
import com.ognjenlazic.Reddit.model.entity.Reaction;
import com.ognjenlazic.Reddit.model.entity.User;
import com.ognjenlazic.Reddit.model.enumeration.ReactionType;
import com.ognjenlazic.Reddit.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private UserService userService;

    @Autowired
    private FlairService flairService;

    @Autowired
    private ReactionService reactionService;

    Logger logger = LoggerFactory.getLogger(CommunityController.class);

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts(){
        List<Post> posts = postService.findAll();
        List<PostDto> postDtos = new ArrayList<>();

        for (Post post: posts) {
           Community postCommuntiy = communityService.findOne(post.getCommunity().getCommunityId());
           if (!postCommuntiy.isSuspended()){
               postDtos.add(new PostDto(post));
           }
        }
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<PostDto> savePost(@RequestBody PostDto postDto) {
        System.out.println(postDto.toString());

        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setText(postDto.getText());
        post.setCreationDate(postDto.getCreationDate());
        post.setImagePath(postDto.getImagePath());
        post.setCommunity(communityService.findOne(postDto.getCommunityId()));
        post.setUser(userService.findOne(postDto.getUserId()));

        if(postDto.getFlairId() == null || postDto.getFlairId() == 0){
            post.setFlairs(null);
        } else {
            post.setFlairs(flairService.findOne(postDto.getFlairId()));
        }
        post.setComments(null);
        post.setReaction(null);

        post = postService.save(post);

        // By default first upvote on post creation
        Reaction reaction = new Reaction();
        reaction.setReactionId(1L); // Hardcoded info
        reaction.setPost(post);
        reaction.setTimestamp(LocalDateTime.now());
        reaction.setReactionType(ReactionType.UPVOTE);
        reaction.setUser(post.getUser());
        reaction.setComment(null);

        reactionService.save(reaction);
        logger.info("New post created");
        return new ResponseEntity<>(new PostDto(post), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto) {

        Post post = postService.findOne(postDto.getId());

        if (post == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        post.setTitle(postDto.getTitle());
        post.setText(postDto.getText());

        post = postService.save(post);
        logger.info("Post updated");
        return new ResponseEntity<>(new PostDto(post), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        logger.info("BRISNJE OBJAVE: " + id);
        Post post = postService.findOne(id);

        if (post != null) {
            postService.remove(post.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "user/{id}")
    public ResponseEntity<List<PostDto>> getAllUserPosts(@PathVariable Long id) {

        Set<Post> posts = postService.findAllUserPosts(id);
        List<PostDto> postDtos = new ArrayList<>();

        for (Post post: posts) {
            postDtos.add(new PostDto(post));
        }
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @GetMapping(value = "community/{id}")
    public ResponseEntity<List<PostDto>> getAllCommunityPosts(@PathVariable Long id) {

        Set<Post> posts = postService.findAllCommunityPosts(id);

        List<PostDto> postDtos = new ArrayList<>();

        for (Post post: posts) {
            postDtos.add(new PostDto(post));
        }
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getOneById(@PathVariable Long id){
        Post post = postService.findOne(id);
        PostDto postDto = new PostDto(post);

        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping(value = "/sorted-by-popular")
    public ResponseEntity<List<PostDto>> getPostsSortedByPopular() {
        List<Post> posts = postService.findAll();
        List<KarmaDto> allPostsKarma = new ArrayList<>();

        List<PostDto> sortedPosts = new ArrayList<>();

        for (Post post: posts) {
            allPostsKarma.add(reactionService.getPostKarma(post.getId()));
        }

        allPostsKarma.sort(Comparator.comparing(KarmaDto::getNumber));

        for (KarmaDto karma: allPostsKarma) {
            Post post = postService.findOne(karma.getEntityId());
            sortedPosts.add(new PostDto(post));
        }
        return new ResponseEntity<>(sortedPosts, HttpStatus.OK);
    }

    @GetMapping(value = "/sorted-by-unpopular")
    public ResponseEntity<List<PostDto>> getPostsSortedByUnpopular() {
        List<Post> posts = postService.findAll();
        List<KarmaDto> allPostsKarma = new ArrayList<>();

        List<PostDto> sortedPosts = new ArrayList<>();

        for (Post post: posts) {
            allPostsKarma.add(reactionService.getPostKarma(post.getId()));
        }

        allPostsKarma.sort(Comparator.comparing(KarmaDto::getNumber).reversed());

        for (KarmaDto karma: allPostsKarma) {
            Post post = postService.findOne(karma.getEntityId());
            sortedPosts.add(new PostDto(post));
        }
        return new ResponseEntity<>(sortedPosts, HttpStatus.OK);
    }

    @GetMapping(value = "/sorted-by-date-asc")
    public ResponseEntity<List<PostDto>> getPostsSortedByDateAsc() {
        Set<Post> posts = postService.findAllPostSortedByDateAsc();
        List<PostDto> postsDtos = new ArrayList<>();

        for (Post post: posts) {
            postsDtos.add(new PostDto(post));
        }
        return new ResponseEntity<>(postsDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/sorted-by-date-desc")
    public ResponseEntity<List<PostDto>> getPostsSortedByDateDesc() {
        Set<Post> posts = postService.findAllPostSortedByDateDesc();
        List<PostDto> postsDtos = new ArrayList<>();

        for (Post post: posts) {
            postsDtos.add(new PostDto(post));
        }
        return new ResponseEntity<>(postsDtos, HttpStatus.OK);
    }

}
