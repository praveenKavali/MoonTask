package com.MoonTask.Backend.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * In order to login user need to enter email and password*/
@Data
@AllArgsConstructor
public class LoginDTO {
    @NotNull
    @Email
    private String email;
    @Size(min = 8)
    private String password;
}
