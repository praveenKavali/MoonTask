package com.MoonTask.Backend.security.jwt;

import com.MoonTask.Backend.security.service.CustomUserDetailsService;
import com.MoonTask.Backend.security.service.JwtService;
import com.MoonTask.Backend.user.entity.UserInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtFilterTest {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    FilterChain filterChain;
    @Mock
    JwtService service;
    @Mock
    CustomUserDetailsService customUserDetailsService;
    @InjectMocks
    JwtFilter filter;

    @DisplayName("Test for doFilterInternal method")
    @Nested
    class doFilterInternal {
        @DisplayName("Success when path is login")
        @Test
        void success() throws ServletException, IOException {
            MockHttpServletRequest request1 = new MockHttpServletRequest();
            request1.setServletPath("/login");
            filter.doFilterInternal(request1, response, filterChain);
            verify(filterChain).doFilter(request1, response);
        }

        @DisplayName("Success request pass through security filter")
        @Test
        void success1() throws ServletException, IOException {
            SecurityContextHolder.clearContext();
            String token = "A.valid.Token";
            String email = "p@gmail.com";
            UserDetails user = UserInfo.builder().email(email).build();

            when(request.getServletPath()).thenReturn("/task");
            when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
            when(service.extractEmail(anyString())).thenReturn(email);
            when(service.validateToken(anyString(), any(UserDetails.class), anyString())).thenReturn(true);
            when(customUserDetailsService.loadUserByUsername(anyString())).thenReturn(user);

            filter.doFilterInternal(request, response, filterChain);

            assertNotNull(SecurityContextHolder.getContext().getAuthentication());
            assertEquals(email, SecurityContextHolder.getContext().getAuthentication().getName());
            verify(filterChain).doFilter(request, response);
        }
    }

}