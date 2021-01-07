package com.odazie.teamworkapi.data.repository;

import com.odazie.teamworkapi.data.entity.JobRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRolesRepository extends JpaRepository<JobRole, Long> {

    JobRole findByName(String name);
}
