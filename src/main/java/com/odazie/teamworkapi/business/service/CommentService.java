package com.odazie.teamworkapi.business.service;

import com.odazie.teamworkapi.data.entity.Article;
import com.odazie.teamworkapi.data.entity.Comment;
import com.odazie.teamworkapi.data.entity.Gif;
import com.odazie.teamworkapi.data.entity.User;
import com.odazie.teamworkapi.data.repository.CommentRepository;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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

        LinkedHashMap<String, Object> response = new LinkedHashMap<>();


        if(requestType.equals("commentOnArticle")){
            response.put("status", "success");
            LinkedHashMap<String, String > data = new LinkedHashMap<>();
            data.put("message","Comment successfully created");
            data.put("createdOn", comment.getCreatedOn().toString());
            data.put("articleTitle", article.getArticleId().toString());
            data.put("article", article.getArticle());
            data.put("comment", comment.getComment());
            data.put("employeeEmail", comment.getCommentatorEmail());
            response.put("data", data);
        }

        if(requestType.equals("commentOnGif")){
            response.put("status", "success");
            LinkedHashMap<String, String > data = new LinkedHashMap<>();
            data.put("message","Comment successfully created");
            data.put("createdOn", comment.getCreatedOn().toString());
            data.put("gifTitle", gif.getGifId().toString());
            data.put("comment", comment.getComment());
            data.put("employeeEmail", comment.getCommentatorEmail());
            response.put("data", data);
        }

        //look at the else condition agian if need be. for better error handling.


        return response;
    }



    public static void addingCommentToResponseSpec(LinkedHashMap<String, Object> jsonResponse, LinkedHashMap<String, Object> data, List<Comment> comments2, Article article) {
        ArrayList<Object> comments = new ArrayList<>();
        for (Comment comment : comments2) {
            LinkedHashMap<String, String > singleComment = new LinkedHashMap<>();

            singleComment.put("commentId", comment.getCommentId().toString());
            singleComment.put("authorId", comment.getCommentatorEmail());
            singleComment.put("comment", comment.getComment());

            comments.add(singleComment);
        }
        data.put("comments", comments);
        jsonResponse.put("data", data);
    }

}
