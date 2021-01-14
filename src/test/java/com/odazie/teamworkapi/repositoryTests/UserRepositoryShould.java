package com.odazie.teamworkapi.repositoryTests;


import com.odazie.teamworkapi.data.entity.Article;
import com.odazie.teamworkapi.data.entity.Gif;
import com.odazie.teamworkapi.data.entity.User;
import com.odazie.teamworkapi.data.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
class UserRepositoryShould {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    User testUser = new User();

    public UserRepositoryShould() {

        testUser.setPassword("Test");
        testUser.setGender("Test");
        testUser.setEmail("test@testmail.com");
        testUser.setFirstName("test");
    }

    @Test
    void returnUserWhenGivenEmail(){
        testEntityManager.persist(testUser);

        User actual = userRepository.findUserByEmail("test@testmail.com");

        assertThat(actual).isEqualTo(testUser);
    }

    @Test
    void feedOrSortGifAndArticlesByOrderOfDescAndMostRecentTest(){

        //This created gif and article then compare which is most recent using time created.

        Gif gif = new Gif();
        gif.setTitle("Test Gif");
        gif.setImageUrl("rweskicemiencineicomeocmoemc");

        Article article = new Article();
        article.setTitle("Test");
        article.setArticle("Test");

        // Article is saved before the Gif
        testEntityManager.persist(article);
        testEntityManager.persist(gif);

        ArrayList<Object> feed = userRepository.findAndSortGifAndArticlesByOrderOfDesc();


        Date articleCreationTime = (Date) feed.get(0);
        Date gifCreationTime = (Date) feed.get(1);


        assertThat(articleCreationTime.getTime()).isGreaterThan(gifCreationTime.getTime());

    }


}
