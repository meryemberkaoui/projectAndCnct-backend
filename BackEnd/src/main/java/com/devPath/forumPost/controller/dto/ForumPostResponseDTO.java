package com.devPath.forumPost.controller.dto;

import com.devPath.forumPost.ressources.EForumCategory;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class ForumPostResponseDTO {

    private Long id;
    private String title;
    private String content;
    private EForumCategory category;

    private Instant createdAt;

    private UUID authorId;
    private String authorUsername;
}
