package com.odazie.teamworkapi.business.service;

import com.odazie.teamworkapi.data.entity.Article;
import com.odazie.teamworkapi.data.entity.Gif;
import com.odazie.teamworkapi.data.entity.User;
import com.odazie.teamworkapi.data.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void saveArticle(Article article, User currentUser){

        article.setUser(currentUser);
        currentUser.addArticle(article);

        getArticleRepository().save(article);
    }

    public LinkedHashMap<String, Object> modifyJsonResponse(String requestType, Article article){
        LinkedHashMap<String, Object> jsonResponse = new LinkedHashMap<>();

        if(requestType.equals("create")){
            jsonResponse.put("status", "success");
            LinkedHashMap<String, String > data = new LinkedHashMap<>();
            data.put("articleId", article.getArticleId().toString());
            data.put("message","Article successfully posted");
            data.put("createdOn", article.getCreatedOn().toString());
            data.put("title", article.getTitle());

            jsonResponse.put("data", data);
        }
        //look at the else condition agian if need be. for better error handling.
        else {
            jsonResponse.put("status", "FAILED");

        }

        return jsonResponse;
    }

    public ArticleRepository getArticleRepository() {
        return articleRepository;
    }
}
