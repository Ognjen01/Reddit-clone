package com.ognjenlazic.Reddit.service;

import com.ognjenlazic.Reddit.model.entity.Post;
import com.ognjenlazic.Reddit.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


public interface PostService {

    Post findOne(Long id);

    List<Post> findAll();

    Post save(Post post);

    void remove(Long id);

    void deleteAllCommunityPosts(Long communityId);

    Set<Post> findAllUserPosts(Long userId);

    Set<Post> findAllCommunityPosts(Long communityId);

    Set<Post> findAllPostSortedByDateAsc();

    Set<Post> findAllPostSortedByDateDesc();
}
