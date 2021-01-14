package com.odazie.teamworkapi.repositoryTests;

import com.odazie.teamworkapi.data.entity.Article;
import com.odazie.teamworkapi.data.entity.Gif;
import com.odazie.teamworkapi.data.entity.User;
import com.odazie.teamworkapi.data.repository.GifRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GifRepositoryShould {


    @Autowired
    private GifRepository gifRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    Gif expected = new Gif();
    User testUser = new User();


    public GifRepositoryShould() {
        expected.setTitle("Test Gif");
        expected.setImageUrl("rweskicemiencineicomeocmoemc");


        //Initialize a user
        testUser.setPassword("Test");
        testUser.setGender("Test");
        testUser.setEmail("test@testmail.com");
        testUser.setFirstName("test");

        testUser.addGif(expected);
    }

    @Test
    void returnGifByImageUrl(){
        testEntityManager.persist(expected);

        Gif actual = gifRepository.findGifByImageUrl("rweskicemiencineicomeocmoemc");

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void returnAGifWhenGivenItsId(){
        testEntityManager.persist(expected);

        Gif actual = gifRepository.findByGifId(expected.getGifId());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void returnGIFByGIFIdAndAuthor(){
        testEntityManager.persist(expected);
        testEntityManager.persist(testUser);

        Gif actual = gifRepository.findGifByGifIdAndUser(expected.getGifId(), testUser);

        assertThat(actual).isEqualTo(expected);
    }






}
