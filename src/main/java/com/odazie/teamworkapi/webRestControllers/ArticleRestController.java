package com.odazie.teamworkapi.webRestControllers;

import com.odazie.teamworkapi.business.service.ArticleService;
import com.odazie.teamworkapi.business.service.UserService;
import com.odazie.teamworkapi.data.entity.Article;
import com.odazie.teamworkapi.data.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
public class ArticleRestController {

    private final UserService userService;
    private final ArticleService articleService;

    public ArticleRestController(UserService userService, ArticleService articleService) {
        this.userService = userService;
        this.articleService = articleService;
    }



    @PostMapping("/articles")
    public ResponseEntity<LinkedHashMap<String, Object>> writeArticle(@RequestBody Article article, Authentication authentication){

        User currentUser = userService.findUserByEmail(authentication.getName());

        articleService.saveArticle(article, currentUser);
        LinkedHashMap<String, Object> jsonResponse = articleService.modifyJsonResponse("create",article);
        return new ResponseEntity<>(jsonResponse,HttpStatus.CREATED);
    }

    @PatchMapping("/articles/{articleId}")
    public ResponseEntity<Void> editArticle(@RequestBody Article article, Authentication authentication){



    }
}


