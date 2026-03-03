package com.MoonTask.Backend.user.service;

import com.MoonTask.Backend.security.exception.UserException;
import com.MoonTask.Backend.security.service.JwtService;
import com.MoonTask.Backend.user.dto.CreateUserDTO;
import com.MoonTask.Backend.user.dto.UpdateUserDTO;
import com.MoonTask.Backend.user.entity.UserInfo;
import com.MoonTask.Backend.user.mapper.MapperDTO;
import com.MoonTask.Backend.user.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepo repo;
    @Mock
    MapperDTO dto;
    @Mock
    BCryptPasswordEncoder encoder;
    @Mock
    AuthenticationManager manager;
    @Mock
    JwtService jwtService;
    @InjectMocks
    UserService service;
    UserInfo user;
    CreateUserDTO create;
    @BeforeEach
    void setUp() {
        user = new UserInfo();
        user.setEmail("p@gmail.com");
        create = CreateUserDTO.builder()
                .name("praveen")
                .email("p@gmail.com")
                .password("00000000").build();
    }

    @DisplayName("Test for create method")
    @Nested
    class Crate {
        @DisplayName("Success case")
        @Test
        void success() {
            when(repo.findByEmail(anyString())).thenReturn(Optional.empty());
            when(dto.createToUserInfo(any())).thenReturn(user);
            when(encoder.encode(create.getPassword())).thenReturn("hello");
            String result = service.create(create);
            assertNotNull(result);
            assertEquals("Account created successfully. Welcome, " + user.getName() + "!", result);
            verify(repo).findByEmail(anyString());
            verify(dto).createToUserInfo(any());
            verify(encoder).encode(anyString());
            verify(repo).save(any());
        }

        @DisplayName("Failure case: account is present")
        @Test
        void failure(){
            when(repo.findByEmail(anyString())).thenReturn(Optional.of(user));
            UserException ex = assertThrows(UserException.class,
                    () -> service.create(create));
            assertNotNull(ex);
            assertEquals("An user account present with " + create.getEmail() + ". Please use anther one.", ex.getMessage());
            verify(repo).findByEmail(anyString());
            verify(encoder, times(0)).encode(anyString());
            verify(dto, times(0)).createToUserInfo(create);
            verify(repo, times(0)).save(any());
        }
    }

    @DisplayName("Test for update method")
    @Nested
    class Update {
        @DisplayName("Success case")
        @Test
        void success() {
            UpdateUserDTO update = UpdateUserDTO.builder().name("kavali").password("00000001").build();
            when(repo.findByEmail("p@gmail.com")).thenReturn(Optional.of(user));
            String result = service.update("p@gmail.com", update);
            assertEquals("Account updated successfully.", result);
            verify(repo).findByEmail("p@gmail.com");
            verify(repo).save(any());
            verify(encoder).encode(anyString());
        }
        @DisplayName("Failure case")
        @Test
        void failure() {
            UpdateUserDTO userDTO = new UpdateUserDTO();
            when(repo.findByEmail(anyString())).thenReturn(Optional.empty());
            UserException ex = assertThrows(UserException.class,
                    () -> service.update("p@gmail.com", userDTO));
            assertEquals("No user account present with p@gmail.com. Please create an account.", ex.getMessage());
            verify(repo).findByEmail(anyString());
            verify(repo, times(0)).save(any());
        }
    }

    @DisplayName("Test for delete method")
    @Nested
    class delete {
        @DisplayName("Success case")
        @Test
        void success() {
            when(repo.findByEmail(anyString())).thenReturn(Optional.of(user));
            String result = service.delete("praveen@gmail.com");
            assertEquals("Account deleted successfully.", result);
            verify(repo).findByEmail(anyString());
            verify(repo).delete(any());
        }
        @DisplayName("Failure case")
        @Test
        void failure() {
            when(repo.findByEmail(anyString())).thenReturn(Optional.empty());
            UserException ex = assertThrows(UserException.class,
                    () -> service.delete("p@gmail.com"));
            assertEquals("No user account is present with p@gmail.com. Please Login.", ex.getMessage());
            verify(repo).findByEmail(anyString());
            verify(repo, times(0)).delete(any());
        }
    }

    @DisplayName("Test for verifyUser method")
    @Nested
    class verifyUser {
        @DisplayName("Success case")
        @Test
        void success() {
            Authentication auth = mock(Authentication.class);
            when(auth.isAuthenticated()).thenReturn(true);
            when(manager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
            when(jwtService.generateToken(anyString())).thenReturn("A-fake-key");
            String result = service.verifyUser("p@gmail.com", "00000000");
            assertNotNull(result);
            assertEquals("A-fake-key", result);
            verify(auth).isAuthenticated();
            verify(jwtService).generateToken(anyString());
            verify(manager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        }

        @DisplayName("Failure case")
        @Test
        void failure() {
            Authentication auth = mock(Authentication.class);
            when(manager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
            when(auth.isAuthenticated()).thenReturn(false);
            UserException ex = assertThrows(UserException.class,
                    () -> service.verifyUser("p@gmail.com", "00000000"));
            assertEquals("Something went wrong. Please check your email and password.", ex.getMessage());
            verify(auth).isAuthenticated();
            verify(manager).authenticate(any(UsernamePasswordAuthenticationToken.class));
            verify(jwtService, times(0)).generateToken(anyString());
        }
    }

    @DisplayName("Test for username method")
    @Nested
    class username {
        @DisplayName("Success case")
        @Test
        void success() {
            user.setName("p");
            UserDetails user1 = UserInfo.builder().email("p@gmail.com").build();
            when(repo.findByEmail(anyString())).thenReturn(Optional.of(user));
            String result = service.username(user1);
            assertNotNull(result);
            assertEquals("p", result);
        }

        @DisplayName("Failure case")
        @Test
        void failure() {
            when(repo.findByEmail(anyString())).thenReturn(Optional.empty());
            UserException ex = assertThrows(UserException.class,
                    () -> service.username(UserInfo.builder().email("p@gmail.com").build()));
            assertEquals("No username present", ex.getMessage());
        }
    }
}