package com.MoonTask.Backend.user.controller;

import com.MoonTask.Backend.security.service.CustomUserDetailsService;
import com.MoonTask.Backend.security.service.JwtService;
import com.MoonTask.Backend.user.dto.CreateUserDTO;
import com.MoonTask.Backend.user.dto.LoginDTO;
import com.MoonTask.Backend.user.dto.UpdateUserDTO;
import com.MoonTask.Backend.user.entity.UserInfo;
import com.MoonTask.Backend.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    UserService service;
    @MockitoBean
    JwtService jwtService;
    @MockitoBean
    CustomUserDetailsService userDetailsService;
    UserInfo user1;

    @BeforeEach
    void setUp() {
        user1 = UserInfo.builder().name("pra").email("p@gmail.com").password("00000000").build();
    }

    @DisplayName("Test for registerUser method")
    @Nested
    class RegisterUser {
        @DisplayName("Success case")
        @Test
        void success() throws Exception {
            CreateUserDTO create = CreateUserDTO.builder().name("praveen").email("p@gmail.com").password("00000000").build();
            when(service.create(create)).thenReturn("success");
            mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(create)))
                    .andExpect(status().isCreated())
                    .andExpect(content().string("success"));
        }

        @DisplayName("Failure case invalid email")
        @Test
        void failure() throws Exception {
            CreateUserDTO create = CreateUserDTO.builder().email("praveem").build();
            mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(create)))
                    .andExpect(status().isBadRequest());
            verifyNoInteractions(service);
        }

        @DisplayName(("Failure case invalid password"))
        @Test
        void failure2() throws Exception {
            CreateUserDTO create = CreateUserDTO.builder().password("00").build();
            mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(create)))
                    .andExpect(status().isBadRequest());
            verifyNoInteractions(service);
        }
    }

    @DisplayName("Test for login method")
    @Nested
    class login {
        @DisplayName("Success")
        @Test
        void success() throws Exception{
            LoginDTO login = LoginDTO.builder().email("p@gmail.com").password("00000000").build();
            when(service.verifyUser(anyString(), anyString())).thenReturn("varified");
            mockMvc.perform(post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(login)))
                    .andExpect(status().isOk())
                    .andExpect(content().string("varified"));
        }

        @DisplayName("Failure validation for email")
        @Test
        void failure() throws Exception {
            LoginDTO login = LoginDTO.builder().email("pra").build();
            mockMvc.perform(post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(login)))
                    .andExpect(status().isBadRequest());
            verifyNoInteractions(service);
        }

        @DisplayName("Failure validation for password")
        @Test
        void failure2() throws Exception {
            LoginDTO login = LoginDTO.builder().password("00").build();
            mockMvc.perform(post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(login)))
                    .andExpect(status().isBadRequest());
            verifyNoInteractions(service);
        }
    }

    @DisplayName("Test for update method")
    @Nested
    class update {
        @DisplayName("Success")
        @Test
        @WithMockUser(username = "testUser", roles = {"USER"})
        void success() throws Exception {
            UpdateUserDTO update = UpdateUserDTO.builder().name("p").build();
            when(service.update("testUser", update)).thenReturn("updated successfully.");
            mockMvc.perform(put("/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(update)))
                    .andExpect(status().isOk())
                    .andExpect(content().string("updated successfully."));
            verify(service).update(eq("testUser"), any(UpdateUserDTO.class));
        }

        @DisplayName("Failure password validation")
        @Test
        void failure() throws Exception {
            UpdateUserDTO update = new UpdateUserDTO("p", "000");
            mockMvc.perform(put("/update")
                    .with(user("testUser"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(update)))
                    .andExpect(status().isBadRequest());
            verifyNoInteractions(service);
        }
    }

    @DisplayName("Test for delete method")
    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void delete() throws Exception {
        when(service.delete(anyString())).thenReturn("deleted successfully.");
        mockMvc.perform(MockMvcRequestBuilders.delete("/delete"))
                .andExpect(status().isOk())
                .andExpect(content().string("deleted successfully."));
        verify(service).delete(anyString());
    }

    @DisplayName("Test for getUsername method")
    @Test
    void getUsername() throws Exception {
        when(service.username(any())).thenReturn("praveen");
        mockMvc.perform(get("/username"))
                .andExpect(status().isOk())
                .andExpect(content().string("praveen"));
        verify(service).username(any());
    }
}