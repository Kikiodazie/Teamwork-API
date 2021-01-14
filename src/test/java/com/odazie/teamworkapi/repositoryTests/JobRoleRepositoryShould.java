package com.odazie.teamworkapi.repositoryTests;

import com.odazie.teamworkapi.data.entity.JobRole;
import com.odazie.teamworkapi.data.repository.JobRolesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
class JobRoleRepositoryShould {

    @Autowired
    private JobRolesRepository jobRolesRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void returnAJobRoleWhenGivenItsName(){
        JobRole expected = new JobRole();
        expected.setName("ADMIN");
        expected.setDescription("ADMIN ROLE");

        testEntityManager.persist(expected);

        JobRole actual = jobRolesRepository.findByName("ADMIN");

        assertThat(actual).isEqualTo(expected);
    }


}
