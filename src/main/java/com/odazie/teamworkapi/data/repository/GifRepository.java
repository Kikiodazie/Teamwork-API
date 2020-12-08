package com.odazie.teamworkapi.data.repository;

import com.odazie.teamworkapi.data.entity.Gif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GifRepository extends JpaRepository<Gif, Long> {


}
