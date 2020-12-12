package com.odazie.teamworkapi;

import com.odazie.teamworkapi.data.entity.User;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


public class UserRestControllerTest extends AbstractTest{

    @Test
    void contextLoads(){

    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }


//    @Test
//    public void createUser() throws Exception{
//        String uri = "/auth/create-user";
//        User user = new User();
//
//        user.setUserId((long) 1);
//        user.setEmail("test@gmail.com");
//        user.setFirstName("test");
//        user.setLastName("testSurname");
//        user.setPassword("testTest");
//        user.setAddress("Test Cloud");
//        user.setDepartment("Cloud");
//        user.setGender("none");
//
//        String inputJson = super.mapToJson(user);
//
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
//                .content(MediaType.APPLICATION_JSON_VALUE)
//                .content(inputJson)).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//
//        assertEquals(201, status);
//
//    }



}
