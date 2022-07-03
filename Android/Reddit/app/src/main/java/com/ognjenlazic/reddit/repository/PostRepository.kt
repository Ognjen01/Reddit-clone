package com.ognjenlazic.reddit.repository

import com.ognjenlazic.reddit.network.RetrofitInstance
import com.ognjenlazic.reddit.network.model.PostBody

class PostRepository() {
    suspend fun getAllPosts() =
        RetrofitInstance.post.getAllPosts()

    suspend fun createNewPost(post: PostBody) =
        RetrofitInstance.post.createNewPost(post)
}