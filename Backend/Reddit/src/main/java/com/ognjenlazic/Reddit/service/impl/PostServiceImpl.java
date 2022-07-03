package com.ognjenlazic.Reddit.service.impl;

import com.ognjenlazic.Reddit.model.entity.Post;
import com.ognjenlazic.Reddit.repository.PostRepository;
import com.ognjenlazic.Reddit.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;
    @Override
    public Post findOne(Long id){
        return  postRepository.findById(id).orElseGet(null);
    }
    @Override
    public List<Post> findAll(){
        return  postRepository.findAll();
    }
    @Override
    public Post save(Post post){
        return  postRepository.save(post);
    }
    @Override
    public void remove(Long id){
        postRepository.deleteById(id);
    }

    @Override
    public void deleteAllCommunityPosts(Long communityId) {
        postRepository.deleteAllCommunityPosts(communityId);
    }

    @Override
    public Set<Post> findAllUserPosts(Long userId) {
        return postRepository.findAllUserPosts(userId);
    }

    @Override
    public Set<Post> findAllCommunityPosts(Long communityId) {
        return postRepository.findAllCommunityPosts(communityId);
    }

    @Override
    public Set<Post> findAllPostSortedByDateAsc() {
        return postRepository.findAllPostSortedByDateAsc();
    }

    @Override
    public Set<Post> findAllPostSortedByDateDesc() {
        return postRepository.findAllPostSortedByDateDesc();
    }
}
