package com.devPath.forumPost.service;

import com.devPath.forumPost.model.ForumPost;
import com.devPath.forumPost.ressources.EForumCategory;

import java.util.List;

public interface ForumPostService {

    public ForumPost createPost(String title, String content, EForumCategory category, String authorId);

    public List<ForumPost> getAllPosts();

    public ForumPost getPostById(Long id);

    public List<ForumPost> getPostsByCategory(EForumCategory category);

    public void deletePost(Long id);
}
