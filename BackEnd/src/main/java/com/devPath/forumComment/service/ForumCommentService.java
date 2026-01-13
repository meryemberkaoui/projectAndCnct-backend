package com.devPath.forumComment.service;

import com.devPath.forumComment.model.ForumComment;
import com.devPath.forumPost.model.ForumPost;

import java.util.List;
import java.util.UUID;

public interface ForumCommentService {

    public ForumComment addCommentToPost(Long postId, UUID keycloakUserId, String content);

    public List<ForumComment> getCommentsByPost(Long postId);

    public void deleteComment(Long commentId);
}
