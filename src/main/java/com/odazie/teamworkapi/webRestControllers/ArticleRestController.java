package com.odazie.teamworkapi.webRestControllers;

import com.odazie.teamworkapi.business.model.ArticleUpdateModel;
import com.odazie.teamworkapi.business.service.ArticleService;
import com.odazie.teamworkapi.business.service.UserService;
import com.odazie.teamworkapi.data.entity.Article;
import com.odazie.teamworkapi.data.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Optional;

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
    public ResponseEntity<LinkedHashMap<String, Object>> editArticle(@PathVariable Long articleId, @RequestBody ArticleUpdateModel articleUpdateModel, Authentication authentication){
        User currentUser = userService.findUserByEmail(authentication.getName());
        Optional<Article> articleOptional = articleService.findArticleById(articleId, currentUser);

        if (articleOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //Handles patch-update logic
        Article article = articleService.performPatchUpdateOnArticle(articleUpdateModel, articleOptional);

        articleService.saveArticle(article, currentUser);
        LinkedHashMap<String, Object> jsonResponseSpec = articleService.modifyJsonResponse("update", article);


        return new ResponseEntity<>(jsonResponseSpec, HttpStatus.OK);
    }


}


