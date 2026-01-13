package com.devPath.forumPost.controller.dto;

import com.devPath.forumPost.ressources.EForumCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForumPostDTO {
    private String title;
    private String content;
    private EForumCategory category;

}
