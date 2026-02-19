package com.MoonTask.Backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.MoonTask.Backend.security.jwt.JwtFilter;
import com.MoonTask.Backend.security.service.CustomUserDetailsService;
import com.MoonTask.Backend.user.service.UserService;

import javax.naming.AuthenticationException;

/**
 * A configuration class for creating various beans.
 * <h4>Beans Provided and their use Cases:</h4>
 * <p>
 *     <li>1.{@link SecurityFilterChain} bean for csrf disable and other purposes.</li>
 *     <li>2.{@link BCryptPasswordEncoder} for encoding the password used in {@link UserService}</li>
 *     <li>3.{@link AuthenticationProvider} used for choosing default authentication.</li>
 *     <li>4.{@link AuthenticationManager} used for login checking in {@link UserService}</li>
 * </p>
 * @see CustomUserDetailsService
 * @see JwtFilter*/
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService service;
    @Autowired
    private JwtFilter jwtFilter;

    /**
     * Provides {@link SecurityFilterChain} bean for spring security
     * <p>
     *     It contains a series of security filters that an incoming HTTP request must pass through.
     *     <h4>Responsibilities:</h4>
     *     <li>1.Disables csrf</li>
     *     <li>2.permit access to some endpoints and others must pass through filter chain</li>
     *     <li>3.creates a stateless session policy.</li>
     *     <li>4.add a {@link JwtFilter} filter chain before {@link UsernamePasswordAuthenticationFilter}.</li>
     * </p>
     * @param httpSecurity {@link HttpSecurity} contains the incoming HTTP request.
     * @return {@link SecurityFilterChain}*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/register", "/login").permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides {@link AuthenticationProvider} bean
     * <p>
     *     This method allow spring to inject the {@link AuthenticationProvider} to other components as ..........
     *     Here We use {@link DaoAuthenticationProvider} to get {@link AuthenticationProvider}
     *     we assign {@link CustomUserDetailsService} and {@link BCryptPasswordEncoder} to the {@link DaoAuthenticationProvider}
     * </p>
     * @return {@link AuthenticationProvider} which is used as default authentication provider
     * @throws AuthenticationException if AuthenticationProvider failed.*/
    @Bean
    public AuthenticationProvider provider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(service);
        provider.setPasswordEncoder(encoder());
        return provider;
    }

    /**
     * Provides {@link AuthenticationManager} bean from {@link AuthenticationConfiguration}
     * <p>
     *     This method allow spring to inject the {@link AuthenticationManager} to other components such as service
     *     for performing authentication during login process.
     * </p>
     * @param configuration {@link AuthenticationConfiguration} spring security authentication configuration
     * @return {@link AuthenticationManager} instance
     * @throws AuthenticationException if the authentication manager canot*/
    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration configuration){
        return configuration.getAuthenticationManager();
    }
}
