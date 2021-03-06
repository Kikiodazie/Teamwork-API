package com.odazie.teamworkapi.webRestControllers;

import com.odazie.teamworkapi.business.model.ArticleUpdateModel;
import com.odazie.teamworkapi.business.service.ArticleService;
import com.odazie.teamworkapi.business.service.CommentService;
import com.odazie.teamworkapi.business.service.UserService;
import com.odazie.teamworkapi.data.entity.Article;
import com.odazie.teamworkapi.data.entity.Comment;
import com.odazie.teamworkapi.data.entity.Gif;
import com.odazie.teamworkapi.data.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
@ApiOperation(value = "", authorizations = { @Authorization(value="Bearer ") })
@Api(description = "Operations pertaining to ARTICLES")
public class ArticleRestController {

    private final UserService userService;
    private final ArticleService articleService;

    public ArticleRestController(UserService userService, ArticleService articleService, CommentService commentService) {
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


        return new ResponseEntity<>(jsonResponseSpec, HttpStatus.ACCEPTED);
    }



    @DeleteMapping("/articles/{articleId}")
    public ResponseEntity<LinkedHashMap<String, Object>> deleteArticle(@PathVariable Long articleId, Authentication authentication){
        User currentUser = userService.findUserByEmail(authentication.getName());

        Article article = articleService.findArticleByIdAndUser(articleId, currentUser);
        articleService.deleteArticle(article, currentUser);
        LinkedHashMap<String, Object> jsonResponseSpec = articleService.modifyJsonResponse("delete", article);

        return new ResponseEntity<>(jsonResponseSpec, HttpStatus.ACCEPTED);
    }



    @GetMapping("articles/{articleId}")
    public ResponseEntity<LinkedHashMap<String, Object>> getASpecificGif(@PathVariable Long articleId){
        Article article = articleService.getArticleRepository().findByArticleId(articleId);

        if(article == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LinkedHashMap<String, Object> jsonResponseSpec = articleService.modifyJsonResponse("get", article);

        return new ResponseEntity<>(jsonResponseSpec, HttpStatus.OK);
    }




}


