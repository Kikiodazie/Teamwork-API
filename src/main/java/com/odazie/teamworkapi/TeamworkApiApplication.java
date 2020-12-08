package com.odazie.teamworkapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TeamworkApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamworkApiApplication.class, args);
    }

}
