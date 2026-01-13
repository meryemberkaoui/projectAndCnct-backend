package com.devPath.forumPost.service;

import com.devPath.demo.user.domain.User;
import com.devPath.demo.user.repository.UserRepository;
import com.devPath.forumPost.model.ForumPost;
import com.devPath.forumPost.repository.ForumPostRepository;
import com.devPath.forumPost.ressources.EForumCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumPostServiceImpl implements ForumPostService {

    private final ForumPostRepository forumPostRepository;
    private final UserRepository userRepository;

    public ForumPostServiceImpl(ForumPostRepository forumPostRepository, UserRepository userRepository) {
        this.forumPostRepository = forumPostRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ForumPost createPost(String title, String content, EForumCategory category, String keycloakUserId) {

        User author = userRepository.findByKeycloakId(keycloakUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ForumPost post = new ForumPost(title, content, category, author);
        return forumPostRepository.save(post);
    }

    public List<ForumPost> getAllPosts() {
        return forumPostRepository.findAll();
    }

    public ForumPost getPostById(Long id) {
        return forumPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ForumPost not found"));
    }

    public List<ForumPost> getPostsByCategory(EForumCategory category) {
        return forumPostRepository.findAll()
                .stream()
                .filter(post -> post.getCategory() == category)
                .toList();
    }

    public void deletePost(Long id) {
        forumPostRepository.deleteById(id);
    }
}
