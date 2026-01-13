package com.devPath.forumPost.repository;

import com.devPath.forumPost.model.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {
}
