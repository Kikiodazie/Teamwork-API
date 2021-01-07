package com.odazie.teamworkapi.data.repository;

import com.odazie.teamworkapi.data.entity.Article;
import com.odazie.teamworkapi.data.entity.Gif;
import com.odazie.teamworkapi.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

@Repository
public interface GifRepository extends JpaRepository<Gif, Long> {


    Gif findGifByImageUrl(String imageUrl);
    Gif findGifByGifIdAndUser(Long id, User user);
    Gif findByGifId(Long id);

    Gif findGifByCreatedOn(Date createdOn);



}
