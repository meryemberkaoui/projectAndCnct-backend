package com.devPath.forumPost.model;

import com.devPath.demo.user.domain.User;
import com.devPath.forumComment.model.ForumComment;
import com.devPath.forumPost.ressources.EForumCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "forum_post")
@Getter@Setter
public class ForumPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    private EForumCategory category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ForumComment> comments = new ArrayList<>();

//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Attachment> attachments = new ArrayList<>();

    protected ForumPost() {}

    //TODO: Add author parameter when User entity is ready
    public ForumPost(String title, String content, EForumCategory category, User author) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.author = author;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }

}
