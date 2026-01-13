package com.devPath.forumComment.controller;

import com.devPath.forumComment.controller.dto.ForumCommentDTO;
import com.devPath.forumComment.controller.dto.ForumCommentResponseDTO;
import com.devPath.forumComment.mapper.ForumCommentMapper;
import com.devPath.forumComment.model.ForumComment;
import com.devPath.forumComment.service.ForumCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/forum/comments")
@CrossOrigin
public class ForumCommentController {

    private final ForumCommentService forumCommentService;
    private final ForumCommentMapper forumCommentMapper;

    public ForumCommentController(ForumCommentService forumCommentService,
                                  ForumCommentMapper forumCommentMapper) {
        this.forumCommentService = forumCommentService;
        this.forumCommentMapper = forumCommentMapper;
    }

    /**
     * Ajouter un commentaire à un post
     */
    @PostMapping("/post/{postId}")
    public ForumCommentResponseDTO addComment(
            @PathVariable Long postId,
            @RequestParam UUID authorId,
            @RequestBody ForumCommentDTO dto
    ) {
        return forumCommentMapper.toDto(
                forumCommentService.addCommentToPost(
                        postId,
                        authorId,
                        dto.getContent()
                )
        );
    }

    /**
     * Récupérer tous les commentaires d’un post
     */
    @GetMapping("/post/{postId}")
    public List<ForumCommentResponseDTO> getCommentsByPost(
            @PathVariable Long postId
    ) {
        return forumCommentMapper.toDtoList(
                forumCommentService.getCommentsByPost(postId)
        );
    }

    /**
     * Supprimer un commentaire
     */
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        forumCommentService.deleteComment(commentId);
    }
}