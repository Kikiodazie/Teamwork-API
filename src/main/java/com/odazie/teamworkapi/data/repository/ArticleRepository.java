package com.odazie.teamworkapi.data.repository;

import com.odazie.teamworkapi.data.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {


}
