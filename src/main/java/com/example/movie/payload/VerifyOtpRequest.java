package com.example.movie.payload;

public class VerifyOtpRequest {
    private String email;
    private String otp;
    private String username;
    private String password;

    // ✅ Default constructor
    public VerifyOtpRequest() {}

    // ✅ Parameterized constructor
    public VerifyOtpRequest(String email, String otp, String username, String password) {
        this.email = email;
        this.otp = otp;
        this.username = username;
        this.password = password;
    }

    // ✅ Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
