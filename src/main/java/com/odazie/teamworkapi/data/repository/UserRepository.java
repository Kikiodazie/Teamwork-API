package com.odazie.teamworkapi.data.repository;

import com.odazie.teamworkapi.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    @Query(
            value = "SELECT created_on as createdOn\n" +
                    "FROM article\n" +
                    "UNION ALL\n" +
                    "SELECT created_on as createdOn\n" +
                    "FROM gif\n" +
                    "ORDER BY createdOn DESC",
            nativeQuery = true
    )
    ArrayList<Object> findAndSortGifAndArticlesByOrderOfDesc();
}
