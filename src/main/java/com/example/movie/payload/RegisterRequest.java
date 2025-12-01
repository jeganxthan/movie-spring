package com.example.movie.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, message = "Username must be at least 3 characters long")
    private String username;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    // ✅ Default constructor (needed for JSON deserialization)
    public RegisterRequest() {}

    // ✅ Parameterized constructor (optional)
    public RegisterRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // ✅ Getters (to read the values)
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // ✅ Setters (to set the values)
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
