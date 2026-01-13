package com.devPath.forumComment.model;

import com.devPath.demo.user.domain.User;
import com.devPath.forumPost.model.ForumPost;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "forum_comment")
@Getter
@Setter
public class ForumComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private ForumPost post;

//    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Attachment> attachments = new ArrayList<>();

    protected ForumComment() {}

    //TODO: Add author parameter when User entity is ready
    public ForumComment(String content, ForumPost post, User author) {
        this.content = content;
        this.author = author;
        this.post = post;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }
}
