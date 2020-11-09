//package com.lazyhippos.todolistapp.application.controller;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class ArticleControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private TodoService todoServiceMock;
//    @MockBean
//    private LabelService labelServiceMock;
//    @MockBean
//    private TodoLabelService todoLabelServiceMock;
//
//    @Test
//    @WithMockUser(username = "user", password = "password", roles = "USER")
//    public void showHomePage_WithoutQueryParam_ShouldReturnHomePage () throws Exception{
//        // Execute
//        mockMvc.perform(get("/to-do/list").with(csrf()))
//                // Verify
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("<title>Todos List</title>")));
//        // Verify
//        verify(todoServiceMock, times(1)).retrieveAll(any(),any());
//        verify(labelServiceMock, times(1)).retrieveAll(any());
//        verify(todoLabelServiceMock, never()).retrieveAllByLabelId(any());
//    }
//
//    @Test
//    @WithMockUser(username = "user", password = "password", roles = "USER")
//    public void showHomePage_WithQueryParam_ShouldReturnHomePage () throws Exception{
//        // Execute
//        mockMvc.perform(get("/to-do/list")
//                .queryParam("label_id", "Study")
//                .with(csrf()))
//                // Verify
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("<title>Todos List</title>")));
//        // Verify
//        verify(todoServiceMock, never()).retrieveAll(any(),any());
//        verify(labelServiceMock, times(1)).retrieveAll(any());
//        verify(todoLabelServiceMock, times(1)).retrieveAllByLabelId(any());
//    }
//
//    @Test
//    public void showTodoEdit() {
//        // do something
//    }
//
//    @Test
//    public void deleteTodo() {
//        // do something
//    }
//
//    @Test
//    public void registerTodo() {
//        // do something
//    }
//
//    @Test
//    public void updateTodo() {
//        // do something
//    }
//
//    @Test
//    public void completeTodo() {
//        // do something
//    }
//
//    @Test
//    public void retrieveTodoIds() {
//        // do something
//    }
//}