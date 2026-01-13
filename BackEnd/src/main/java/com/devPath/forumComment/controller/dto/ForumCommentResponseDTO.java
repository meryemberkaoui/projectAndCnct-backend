package com.devPath.forumComment.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
public class ForumCommentResponseDTO {

    private Long id;
    private String content;
    private Instant createdAt;

    private UUID authorId;
    private String authorUsername;

    private Long postId;


}
