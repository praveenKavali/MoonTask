package com.MoonTask.Backend.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.MoonTask.Backend.security.exception.UserException;
import com.MoonTask.Backend.security.jwt.JwtFilter;
import com.MoonTask.Backend.user.entity.UserInfo;
import com.MoonTask.Backend.user.repository.UserRepo;

import java.util.Optional;
/**
 * This class is responsible for checking if user is present in the database with email.
 * It interacts with {@link UserRepo} for retrieving the data.
 * @see UserRepo*/
@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo repo;

    /**
     * <p>
     *     In this method we will check if the user is present in the database or not.
     *     This method is used to find the userDetails.
     *     It is used in {@link JwtFilter} and {@link org.springframework.security.authentication.AuthenticationProvider}
     * </p>
     * @param email for finding the user details in the database.
     * @return user details which contains the email, password and other details.
     * @throws UserException if no user is present with the email in database
     * @see JwtFilter*/
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserInfo> user = repo.findByEmail(email);
        if (user.isEmpty()){
            throw new UserException("No user is present with " + email + " in database.");
        }
        return user.get();
    }
}
