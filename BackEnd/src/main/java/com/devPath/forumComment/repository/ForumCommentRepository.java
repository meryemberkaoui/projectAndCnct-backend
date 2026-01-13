package com.devPath.forumComment.repository;

import com.devPath.forumComment.model.ForumComment;
import com.devPath.forumPost.model.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumCommentRepository extends JpaRepository<ForumComment, Long> {

    List<ForumComment> findByPost(ForumPost post);

    List<ForumComment> findByPostId(Long postId);
}
