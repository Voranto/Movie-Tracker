package com.example.movie.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO (
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "email must be a valid email address")
    String email,

    @NotBlank(message = "Password cannot be blank")
    String passwordRaw
) {
}
