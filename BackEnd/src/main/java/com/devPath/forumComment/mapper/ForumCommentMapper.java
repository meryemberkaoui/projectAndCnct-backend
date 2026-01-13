package com.devPath.forumComment.mapper;

import com.devPath.forumComment.controller.dto.ForumCommentDTO;
import com.devPath.forumComment.controller.dto.ForumCommentResponseDTO;
import com.devPath.forumComment.model.ForumComment;
import  org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ForumCommentMapper {


    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "author.username", target = "authorUsername")
    @Mapping(source = "post.id", target = "postId")
    ForumCommentResponseDTO toDto(ForumComment comment);

    List<ForumCommentResponseDTO> toDtoList(List<ForumComment> comments);
}