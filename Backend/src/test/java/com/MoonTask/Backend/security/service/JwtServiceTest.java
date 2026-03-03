package com.MoonTask.Backend.security.service;

import com.MoonTask.Backend.user.entity.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {
    JwtService service = new JwtService();

    @Test
    void generateToken() {
        String name = "praveen";
        String result = service.generateToken(name);
        assertNotNull(result);
        assertEquals(name, service.extractEmail(result));
    }

    @Test
    void validation() {
        UserDetails user = UserInfo.builder().email("p@gmail.com").build();
        String token = service.generateToken(user.getUsername());
        assertTrue(service.validateToken(user.getUsername(), user, token));
    }
}