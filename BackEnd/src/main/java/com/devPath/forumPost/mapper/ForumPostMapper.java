package com.devPath.forumPost.mapper;

import com.devPath.forumPost.controller.dto.ForumPostDTO;
import com.devPath.forumPost.controller.dto.ForumPostResponseDTO;
import com.devPath.forumPost.model.ForumPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ForumPostMapper {

    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "author.username", target = "authorUsername")
    ForumPostResponseDTO toDTO(ForumPost post);

    List<ForumPostResponseDTO> toDTO(List<ForumPost> forumPosts);

}
