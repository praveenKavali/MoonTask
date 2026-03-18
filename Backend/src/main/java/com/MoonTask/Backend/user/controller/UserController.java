package com.MoonTask.Backend.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.MoonTask.Backend.user.dto.CreateUserDTO;
import com.MoonTask.Backend.user.dto.LoginDTO;
import com.MoonTask.Backend.user.dto.UpdateUserDTO;
import com.MoonTask.Backend.user.service.UserService;

import jakarta.validation.Valid;

/**
 * A Rest Controller class for managing user profile and authentication.
 * Provides endpoints for register, login, update and delete.
 * @see UserService*/
@RestController
public class UserController {

    @Autowired
    private UserService service;

    /**
     * This method is used to create a user to database.
     * @param userDTO {@link CreateUserDTO} contains the fields user enters in the registration form.
     * @return response with success message and HTTP 200.
     * @throws jakarta.validation.ValidationException if form data is invalid.*/
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody CreateUserDTO userDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(userDTO));
    }

    /**
     * This method is used for login.
     * <p>
     *     When the user is logged in successfully it will generate a token which is helpful for authentication purpose.
     * </p>
     * @param login {@link LoginDTO} contains the email and password for login purpose.
     * @return String token
     * @throws jakarta.validation.ValidationException if form data is invalid.*/
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO login){
        return ResponseEntity.ok(service.verifyUser(login.getEmail(), login.getPassword()));
    }

    /**
     * This method is useful for updating the username and password.
     * @param user {@link UserDetails} when the user is logged in their details will be saved in spring we are using it here to
     * get email details.
     * @return String message if user details is updated.*/
    @PutMapping("/update")
    public ResponseEntity<String> update(@AuthenticationPrincipal UserDetails user,
                                         @Valid @RequestBody UpdateUserDTO userDTO
    ){
        return ResponseEntity.ok(service.update(user.getUsername(), userDTO));
    }

    /**
     * Deletes the user account.
     * @param user {@link UserDetails} when the user is logged in their details will be saved in spring we are using it here to
     * get email details.
     * @return String message if user is deleted successfully deleted.*/
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@AuthenticationPrincipal UserDetails user){
        return ResponseEntity.ok(service.delete(user.getUsername()));
    }

    @GetMapping("/username")
    public ResponseEntity<String> getUsername(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(service.username(user));
    }
}
