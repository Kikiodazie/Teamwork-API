package com.odazie.teamworkapi.data.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.odazie.teamworkapi.business.model.AuditModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "article")
public class Article extends AuditModel {

    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "article", columnDefinition = "TEXT")
    private String article;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @OneToMany(
            mappedBy = "article",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private final List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment){
        comments.add(comment);
        comment.setArticle(this);
    }

    public void deleteComment(Comment comment){
        comments.remove(comment);
        comment.setArticle(null);
    }


    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
