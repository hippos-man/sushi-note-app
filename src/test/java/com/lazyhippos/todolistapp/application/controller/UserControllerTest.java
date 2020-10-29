package com.lazyhippos.todolistapp.application.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void showUserRegisterPage_ShouldReturnSignUpPage() throws Exception {
        mockMvc.perform(get("/user/sign-up"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Sign up")));
    }

    @Test
    public void register_GivenProperInput_ReturnLoginPage() throws Exception {
        // TODO
    }

    @Test
    public void register_GivenInvalidInput_ReturnSignUpPage() throws Exception {
        // TODO Should I throw Exception & Return ?
    }
}