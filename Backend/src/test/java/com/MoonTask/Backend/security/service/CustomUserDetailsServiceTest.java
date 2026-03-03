package com.MoonTask.Backend.security.service;

import com.MoonTask.Backend.security.exception.UserException;
import com.MoonTask.Backend.user.entity.UserInfo;
import com.MoonTask.Backend.user.repository.UserRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    UserRepo repo;
    @InjectMocks
    CustomUserDetailsService service;

    @DisplayName("Test for loadUserByUsername method")
    @Nested
    class LoadUserByUsername {
        @DisplayName("Success")
        @Test
        void success() {
            UserInfo user = UserInfo.builder().email("p@gmail.com").build();
            when(repo.findByEmail(anyString())).thenReturn(Optional.of(user));
            UserDetails userDetails = service.loadUserByUsername("p@gmail.com");
            assertNotNull(userDetails);
            assertEquals(user.getEmail(), userDetails.getUsername());
        }

        @DisplayName("Failure")
        @Test
        void failure() {
            when(repo.findByEmail(anyString())).thenReturn(Optional.empty());
            UserException ex = assertThrows(UserException.class,
                    () -> service.loadUserByUsername("pra@gmail.com"));
            assertEquals("No user is present with pra@gmail.com in database.", ex.getMessage());

        }
    }

}