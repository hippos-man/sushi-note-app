//package com.lazyhippos.todolistapp.application.controller;
//
//import com.lazyhippos.todolistapp.application.resource.UserRequest;
//import com.lazyhippos.todolistapp.domain.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDateTime;
//
//import static org.hamcrest.CoreMatchers.containsString;
//import static org.mockito.Mockito.doNothing;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userServiceMock;
//
//    @Test
//    public void showUserRegisterPage_ShouldReturnSignUpPage() throws Exception {
//        mockMvc.perform(get("/user/sign-up"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("Sign up")));
//    }
//
//    @Test
//    public void register_GivenValidInput_ReturnLoginPage() throws Exception {
//        UserRequest userRequest = new UserRequest(
//            "test-user", "John", "Tanaka","password"
//        );
//        LocalDateTime localDateTime = LocalDateTime.parse("2019-12-25T10:15:30");
//        doNothing().when(userServiceMock).register(userRequest, localDateTime);
//        // Execute
//        mockMvc.perform(post("/user/register")
//                .with(csrf())
//                .flashAttr("request",
//                        new UserRequest("test-user", "John", "Tanaka","password"))
//        )
//                // Assert
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("Login")));
//    }
//
//    @Test
//    public void register_GivenInvalidInput_ReturnSignUpPage() throws Exception {
//        // Prepare
//        UserRequest userRequest = new UserRequest(
//                "", "John", "Tanaka","password"
//        );
//        LocalDateTime localDateTime = LocalDateTime.parse("2019-12-25T10:15:30");
//        doNothing().when(userServiceMock).register(userRequest, localDateTime);
//        // Execute
//        mockMvc.perform(post("/user/register")
//                .with(csrf())
//                .flashAttr("request",
//                        new UserRequest("", "", "",""))
//        )
//                // Assert
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("Input value is not valid.")));
//    }
//}