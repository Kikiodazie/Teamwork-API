package com.odazie.teamworkapi.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.odazie.teamworkapi.business.model.AuditModel;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "comment")
public class Comment extends AuditModel {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(name = "comment", nullable = false, columnDefinition = "TEXT")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Article article;

    @Email
    @Column(name = "commentator_email")
    private String commentatorEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Gif gif;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Gif getGif() {
        return gif;
    }

    public void setGif(Gif gif) {
        this.gif = gif;
    }

    public String getCommentatorEmail() {
        return commentatorEmail;
    }

    public void setCommentatorEmail(String commentatorEmail) {
        this.commentatorEmail = commentatorEmail;
    }
}
