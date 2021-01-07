package com.odazie.teamworkapi.business.service;

import com.odazie.teamworkapi.data.entity.JobRole;
import com.odazie.teamworkapi.data.entity.User;
import com.odazie.teamworkapi.data.repository.JobRolesRepository;
import com.odazie.teamworkapi.data.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JobRolesRepository jobRolesRepository;

    public UserService(UserRepository userRepository, JobRolesRepository jobRolesRepository) {
        this.userRepository = userRepository;
        this.jobRolesRepository = jobRolesRepository;
    }

    public void saveUser(User user){
        JobRole jobRole = jobRolesRepository.findByName("EMPLOYEE");

        Set<JobRole> jobRoles = new HashSet<>();
        jobRoles.add(jobRole);

        if(user.getEmail().contains("admin")){
            jobRole = jobRolesRepository.findByName("ADMIN");
            jobRoles.add(jobRole);
        }

        user.setJobRoles(jobRoles);
        getUserRepository().save(user);
    }



    public User findUserByEmail(String email){
        return getUserRepository().findUserByEmail(email);
    }


    public UserRepository getUserRepository() {
        return userRepository;
    }

    public JobRolesRepository getJobRolesRepository() {
        return jobRolesRepository;
    }
}
