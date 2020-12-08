package com.odazie.teamworkapi.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "job_roles")
public class JobRole {

    @Id
    @Column(name = "job_role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long JobRoleId;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;


    public Long getJobRoleId() {
        return JobRoleId;
    }

    public void setJobRoleId(Long jobRoleId) {
        JobRoleId = jobRoleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
