package com.odazie.teamworkapi.webRestControllers;

import com.odazie.teamworkapi.business.service.ArticleService;
import com.odazie.teamworkapi.business.service.CloudinaryGifService;
import com.odazie.teamworkapi.business.service.CommentService;
import com.odazie.teamworkapi.business.service.UserService;
import com.odazie.teamworkapi.data.entity.Article;
import com.odazie.teamworkapi.data.entity.Comment;
import com.odazie.teamworkapi.data.entity.Gif;
import com.odazie.teamworkapi.data.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
public class CommentRestController {

    private final CommentService commentService;
    private final UserService userService;
    private final ArticleService articleService;
    private final CloudinaryGifService gifService;

    public CommentRestController(CommentService commentService, UserService userService, ArticleService articleService, CloudinaryGifService gifService) {
        this.commentService = commentService;
        this.userService = userService;
        this.articleService = articleService;
        this.gifService = gifService;
    }


    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<LinkedHashMap<String, Object>> employeeCommentOnColleaguesArticle(@PathVariable Long articleId, @RequestBody Comment comment, Authentication authentication){
        User currentUser = userService.findUserByEmail(authentication.getName());
        Article article = articleService.getArticleRepository().findByArticleId(articleId);

        commentService.saveArticleComment(comment, article, currentUser);

        LinkedHashMap<String, Object> jsonResponse = commentService.modifyJsonResponse("commentOnArticle", article, null ,comment);

        return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
    }

    @PostMapping("/gifs/{gifId}/comments")
    public ResponseEntity<LinkedHashMap<String, Object>> employeeCommentOnColleaguesGif(@PathVariable Long gifId, @RequestBody Comment comment, Authentication authentication){
        User currentUser = userService.findUserByEmail(authentication.getName());
        Gif gif = gifService.findGifById(gifId);

        commentService.saveGifComment(comment, gif, currentUser);
        LinkedHashMap<String, Object> jsonResponse = commentService.modifyJsonResponse("commentOnGif", null, gif, comment);

        return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
    }


}
