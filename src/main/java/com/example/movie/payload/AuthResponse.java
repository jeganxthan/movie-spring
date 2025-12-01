package com.example.movie.payload;

public class AuthResponse {

    private String token;
    private String message;

    // Default constructor
    public AuthResponse() {}

    // Constructor with both fields
    public AuthResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    // Constructor with only message
    public AuthResponse(String message) {
        this.message = message;
    }

    // Static helper methods for convenience
    public static AuthResponse withToken(String token, String message) {
        return new AuthResponse(token, message);
    }

    public static AuthResponse withMessage(String message) {
        return new AuthResponse(null, message);
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
