package com.odazie.teamworkapi.business.service;

import com.odazie.teamworkapi.data.entity.Article;
import com.odazie.teamworkapi.data.entity.Gif;
import com.odazie.teamworkapi.data.repository.ArticleRepository;
import com.odazie.teamworkapi.data.repository.GifRepository;
import com.odazie.teamworkapi.data.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CompanyFeedService {

    private final ArticleRepository articleRepository;
    private final GifRepository gifRepository;
    private final UserRepository userRepository;


    public CompanyFeedService(ArticleRepository articleRepository, GifRepository gifRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.gifRepository = gifRepository;
        this.userRepository = userRepository;
    }

    // FEED FEATURE LOGIc

    public LinkedHashMap<String,Object> getArticlesAndGifsByMostRecent(){
        LinkedHashMap<String, Object> feed = new LinkedHashMap<>();

        ArrayList<Object> dataList = new  ArrayList<>();


        ArrayList<Object> feedCreationTime = getUserRepository().findAndSortGifAndArticlesByOrderOfDesc();

        for (Object data: feedCreationTime) {
            Article article = getArticleRepository().findArticleByCreatedOn((Date)data);
            Gif gif = getGifRepository().findGifByCreatedOn((Date) data);

            LinkedHashMap<String, String > dataMap = new LinkedHashMap<>();
            if (article != null){

                dataMap.put("id", article.getArticleId().toString());
                dataMap.put("createdOn", article.getCreatedOn().toString());
                dataMap.put("title", article.getTitle());
                dataMap.put("article", article.getArticle());
                dataMap.put("authorId", article.getUser().getEmail());

                dataList.add(dataMap);
            }else if (gif != null){

                dataMap.put("id", gif.getGifId().toString());
                dataMap.put("createdOn", gif.getCreatedOn().toString());
                dataMap.put("title", gif.getTitle());
                dataMap.put("imageUrl", gif.getImageUrl());
                dataMap.put("authorId", gif.getUser().getEmail());

                dataList.add(dataMap);

            }

        }

        feed.put("Status", "Success");
        feed.put("data", dataList);

        return feed;
    }

    public ArticleRepository getArticleRepository() {
        return articleRepository;
    }

    public GifRepository getGifRepository() {
        return gifRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
