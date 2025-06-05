package com.tcs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record PersonDTO(
    @NotNull(message = "ID cannot be null")
    Integer id,

    @NotNull(message = "Nombre cannot be null")
    @Size(min = 1, message = "Nombre must not be empty")
    String name,

    @NotNull(message = "Username cannot be null")
    @Size(min = 1, message = "Username must not be empty")
    String username,

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    String email,

    @NotNull(message = "Phone cannot be null")
    @Pattern(regexp = "\\d{7,}", message = "Phone must be at least 7 digits")
    String mobilephone,

    @NotNull(message = "Website cannot be null")
    @Size(min = 1, message = "Website must not be empty")
    @JsonProperty("website")
    String web
) {}