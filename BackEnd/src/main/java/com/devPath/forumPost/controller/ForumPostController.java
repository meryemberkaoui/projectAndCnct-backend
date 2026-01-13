package com.devPath.forumPost.controller;


import com.devPath.forumPost.controller.dto.ForumPostDTO;
import com.devPath.forumPost.controller.dto.ForumPostResponseDTO;
import com.devPath.forumPost.mapper.ForumPostMapper;
import com.devPath.forumPost.service.ForumPostService;
import com.devPath.forumPost.ressources.EForumCategory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/forum/posts")
@CrossOrigin
public class ForumPostController {

    private final ForumPostService forumPostService;
    private final ForumPostMapper forumPostMapper;

    public ForumPostController(
            ForumPostService forumPostService,
            ForumPostMapper forumPostMapper
    ) {
        this.forumPostService = forumPostService;
        this.forumPostMapper = forumPostMapper;
    }

    // CREATE
    @PostMapping
    public ForumPostResponseDTO createPost(@RequestBody ForumPostDTO dto, @AuthenticationPrincipal Jwt jwt) {
        return forumPostMapper.toDTO(
                forumPostService.createPost(
                        dto.getTitle(),
                        dto.getContent(),
                        dto.getCategory(),
                        jwt.getSubject()
                        // author récupéré côté service (JWT / contexte)
                )
        );
    }

    // GET ALL
    @GetMapping
    public List<ForumPostResponseDTO> getAllPosts() {
        return forumPostMapper.toDTO(
                forumPostService.getAllPosts()
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ForumPostResponseDTO getPostById(@PathVariable Long id) {
        return forumPostMapper.toDTO(
                forumPostService.getPostById(id)
        );
    }

    // GET BY CATEGORY
    @GetMapping("/category/{category}")
    public List<ForumPostResponseDTO> getByCategory(
            @PathVariable EForumCategory category
    ) {
        return forumPostMapper.toDTO(
                forumPostService.getPostsByCategory(category)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        forumPostService.deletePost(id);
    }
}

