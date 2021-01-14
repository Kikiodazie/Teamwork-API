package com.odazie.teamworkapi.repositoryTests;

import com.odazie.teamworkapi.data.entity.Article;
import com.odazie.teamworkapi.data.entity.User;
import com.odazie.teamworkapi.data.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleRepositoryShould {

    Article expected = new Article();
    User testUser = new User();
    public ArticleRepositoryShould() {
        expected.setTitle("Test");
        expected.setArticle("Test");

        //Initialize a user
        testUser.setPassword("Test");
        testUser.setGender("Test");
        testUser.setEmail("test@testmail.com");
        testUser.setFirstName("test");

        testUser.addArticle(expected);
    }

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void returnAnArticleWhenGivenItsId(){
        testEntityManager.persist(expected);

        Article actual = articleRepository.findByArticleId(expected.getArticleId());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void returnArticleByCreatedOnDate(){
        testEntityManager.persist(expected);
        Article actual = articleRepository.findArticleByCreatedOn(expected.getCreatedOn());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void returnArticleByArticleIdAndArticleAuthor(){
        testEntityManager.persist(expected);
        testEntityManager.persist(testUser);

        Article actual = articleRepository.findArticleByArticleIdAndUser(expected.getArticleId(), testUser);

        assertThat(actual).isEqualTo(expected);
    }
}
