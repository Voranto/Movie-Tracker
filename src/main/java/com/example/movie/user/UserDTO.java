package com.example.movie.user;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserDTO(
        @NotBlank(message = "Display Name cannot be blank")
        String displayName,

        @NotBlank(message = "Password cannot be blank")
        String passwordRaw,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "email must be a valid email address")
        String email
) {
}