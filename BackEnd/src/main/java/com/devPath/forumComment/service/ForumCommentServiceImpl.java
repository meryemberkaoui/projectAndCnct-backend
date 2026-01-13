package com.devPath.forumComment.service;

import com.devPath.demo.user.domain.User;
import com.devPath.demo.user.repository.UserRepository;
import com.devPath.forumComment.model.ForumComment;
import com.devPath.forumComment.repository.ForumCommentRepository;
import com.devPath.forumPost.model.ForumPost;
import com.devPath.forumPost.repository.ForumPostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ForumCommentServiceImpl implements ForumCommentService {

    private final ForumCommentRepository forumCommentRepository;
    private final ForumPostRepository forumPostRepository;
    private final UserRepository userRepository;

    public ForumCommentServiceImpl(ForumCommentRepository forumCommentRepository,
                                   ForumPostRepository forumPostRepository, UserRepository userRepository) {
        this.forumCommentRepository = forumCommentRepository;
        this.forumPostRepository = forumPostRepository;
        this.userRepository = userRepository;
    }

    public ForumComment addCommentToPost(Long postId, UUID keycloakUserId, String content) {

            ForumPost post = forumPostRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("Post not found"));

            User author = userRepository.findByKeycloakId(String.valueOf(keycloakUserId))
                    .orElseThrow(() -> new RuntimeException("User not found"));

            ForumComment comment = new ForumComment(content, post, author);
            return forumCommentRepository.save(comment);
    }


    public List<ForumComment> getCommentsByPost(Long postId) {
        return forumCommentRepository.findByPostId(postId);
    }

    public void deleteComment(Long commentId) {
        forumCommentRepository.deleteById(commentId);
    }
}
