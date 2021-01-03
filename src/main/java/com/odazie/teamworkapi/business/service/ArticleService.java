package com.odazie.teamworkapi.business.service;

import com.odazie.teamworkapi.business.model.ArticleUpdateModel;
import com.odazie.teamworkapi.data.entity.Article;
import com.odazie.teamworkapi.data.entity.User;
import com.odazie.teamworkapi.data.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.LinkedHashMap;
import java.util.Optional;

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

        if(requestType.equals("update")){
            jsonResponse.put("status", "success");
            LinkedHashMap<String, String > data = new LinkedHashMap<>();
            data.put("message","Article successfully updated");
            data.put("title", article.getTitle());
            data.put("article", article.getArticle());
            jsonResponse.put("data", data);
        }

        if(requestType.equals("delete")){
            jsonResponse.put("status", "success");
            LinkedHashMap<String, String > data = new LinkedHashMap<>();
            data.put("message","Article successfully deleted");
            jsonResponse.put("data", data);
        }


        //look at the else condition agian if need be. for better error handling.


        return jsonResponse;
    }

    public Article performPatchUpdateOnArticle(@RequestBody ArticleUpdateModel articleUpdateModel, Optional<Article> articleOptional) {
        Article article = articleOptional.get();

        if (articleUpdateModel.getArticle() != null){
            article.setArticle(articleUpdateModel.getArticle());
        }

        if (articleUpdateModel.getTitle() != null){
            article.setTitle(articleUpdateModel.getTitle());
        }
        return article;
    }

    public Article findArticleByIdAndUser(Long id, User currentUser){
        return getArticleRepository().findArticleByArticleIdAndUser(id, currentUser);
    }

    public void deleteArticle(Article article, User currentUser){
        currentUser.deleteArticle(article);
        getArticleRepository().delete(article);
    }



    public Optional<Article> findArticleById(Long id, User currentUser){
        return getArticleRepository().findByArticleIdAndUser(id, currentUser);
    }


    public ArticleRepository getArticleRepository() {
        return articleRepository;
    }
}
