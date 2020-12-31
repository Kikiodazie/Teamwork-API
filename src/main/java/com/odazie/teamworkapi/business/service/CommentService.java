package com.odazie.teamworkapi.business.service;

import com.odazie.teamworkapi.data.entity.Article;
import com.odazie.teamworkapi.data.entity.Comment;
import com.odazie.teamworkapi.data.entity.Gif;
import com.odazie.teamworkapi.data.entity.User;
import com.odazie.teamworkapi.data.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void saveArticleComment(Comment comment, Article article, User currentUser){
        comment.setArticle(article);
        comment.setCommentatorEmail(currentUser.getEmail());
        article.addComment(comment);
        getCommentRepository().save(comment);
    }

    public void saveGifComment(Comment comment, Gif gif, User currentUser){
        comment.setGif(gif);
        comment.setCommentatorEmail(currentUser.getEmail());
        gif.addComment(comment);
        getCommentRepository().save(comment);
    }



    public CommentRepository getCommentRepository() {
        return commentRepository;
    }


    public LinkedHashMap<String, Object> modifyJsonResponse(String requestType,Article article, Gif gif, Comment comment){
        LinkedHashMap<String, Object> jsonResponse = new LinkedHashMap<>();


        if(requestType.equals("commentOnArticle")){
            jsonResponse.put("status", "success");
            LinkedHashMap<String, String > data = new LinkedHashMap<>();
            data.put("message","Comment successfully created");
            data.put("createdOn", comment.getCreatedOn().toString());
            data.put("articleTitle", article.getArticleId().toString());
            data.put("article", article.getArticle());
            data.put("comment", comment.getComment());
            data.put("employeeEmail", comment.getCommentatorEmail());
            jsonResponse.put("data", data);
        }

        if(requestType.equals("commentOnGif")){
            jsonResponse.put("status", "success");
            LinkedHashMap<String, String > data = new LinkedHashMap<>();
            data.put("message","Comment successfully created");
            data.put("createdOn", comment.getCreatedOn().toString());
            data.put("gifTitle", gif.getGifId().toString());
            data.put("comment", comment.getComment());
            data.put("employeeEmail", comment.getCommentatorEmail());
            jsonResponse.put("data", data);
        }

        //look at the else condition agian if need be. for better error handling.


        return jsonResponse;
    }

}
