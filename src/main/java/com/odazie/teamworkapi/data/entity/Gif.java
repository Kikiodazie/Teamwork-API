package com.odazie.teamworkapi.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.odazie.teamworkapi.business.model.AuditModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gif")
public class Gif extends AuditModel {

    @Id
    @Column(name = "gif_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gifId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "image_url", nullable = false, columnDefinition = "TEXT")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @OneToMany(
            mappedBy = "gif",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private final List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment){
        comments.add(comment);
        comment.setGif(this);
    }

    public void deleteComment(Comment comment){
        comments.remove(comment);
        comment.setGif(null);
    }


    public Long getGifId() {
        return gifId;
    }

    public void setGifId(Long gifId) {
        this.gifId = gifId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
