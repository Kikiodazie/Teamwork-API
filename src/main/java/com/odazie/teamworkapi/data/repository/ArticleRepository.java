package com.odazie.teamworkapi.data.repository;

import com.odazie.teamworkapi.data.entity.Article;
import com.odazie.teamworkapi.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findByArticleIdAndUser (Long aLong, User user);

    Article findArticleByArticleIdAndUser(Long id, User user);

    Article findByArticleId(Long id);


    Article findArticleByCreatedOn(Date createdOn);


}
