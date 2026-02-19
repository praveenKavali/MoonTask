package com.MoonTask.Backend.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.MoonTask.Backend.security.service.CustomUserDetailsService;
import com.MoonTask.Backend.security.service.JwtService;

import java.io.IOException;

/**
 * Custom filter that intercepts every incoming HTTP request to validate.
 * <p>
 *     This filter ensures that authenticated user with valid tokens can access protected resources.
 *     extends {@link OncePerRequestFilter} to ensure every single request to be validated.
 * </p>
 * @see JwtService
 * @see CustomUserDetailsService */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private CustomUserDetailsService service;

    /**
     * <h3>Responsibilities:</h3>
     * <p>
     * <li>1.Checks if any token present in the request header, and it is starts with 'Bearer '.</li>
     * <li>2.Extract user email from the token. Using {@link CustomUserDetailsService}.</li>
     * <li>3.Validate the token.</li>
     * <li>4.Initiate the {@link UsernamePasswordAuthenticationToken}
     * and pass the {@link org.springframework.security.core.userdetails.UserDetails} and their authorities.</li>
     * <li>5.set the details of {@link WebAuthenticationDetailsSource} to {@link UsernamePasswordAuthenticationToken}
     * and pass the {@link HttpServletRequest} through buildDetails.</li>
     * <li>6.Pass the {@link UsernamePasswordAuthenticationToken} to {@link SecurityContextHolder}</li>
     * <li>After validating the token It passes the request to the next filter.</li>
     * </p>
     *
     * @param request     {@link HttpServletRequest} which contains the user request details.
     * @param response    {@link HttpServletResponse} which contains the response of the server.
     * @param filterChain {@link FilterChain} which is used to pass the request to next filter.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getServletPath();
        if (path.equals("/register") || path.equals("/login")){
            filterChain.doFilter(request, response);
            return;
        }
        String header = request.getHeader("Authorization");
        String token = "";
        String email = "";
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            email = jwtService.extractEmail(token);
            System.out.println("DEBUG: Extracted email: " + email);
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails user = service.loadUserByUsername(email);
            System.out.println("Debug: user details found in bd: " + user.getUsername());

            if (jwtService.validateToken(email, user, token)) {
                UsernamePasswordAuthenticationToken authToken = new
                        UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
