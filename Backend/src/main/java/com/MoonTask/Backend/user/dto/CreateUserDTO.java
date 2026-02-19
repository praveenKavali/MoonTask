package com.MoonTask.Backend.user.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * In order to create a new account user need to enter some details, these are the information user need to enter.*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDTO {
    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    @Size(min = 8)
    private String password;
}
