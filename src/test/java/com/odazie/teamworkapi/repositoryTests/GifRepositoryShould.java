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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
class GifRepositoryShould {


    @Autowired
    private GifRepository gifRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    Gif expected = new Gif();

    public GifRepositoryShould() {
        expected.setTitle("Test Gif");
        expected.setImageUrl("rweskicemiencineicomeocmoemc");
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
        User testUser = new User();

        //Initialize a user
        testUser.setPassword("Test");
        testUser.setGender("Test");
        testUser.setEmail("test@testmail.com");
        testUser.setFirstName("test");

        testUser.addGif(expected);


        testEntityManager.persist(expected);
        testEntityManager.persist(testUser);

        Gif actual = gifRepository.findGifByGifIdAndUser(expected.getGifId(), testUser);

        assertThat(actual).isEqualTo(expected);
    }






}
