package com.example.movie.service;

import java.time.Duration;
import java.util.Objects;
import java.util.Random;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    private static final String OTP_PREFIX = "otp:";
    private final StringRedisTemplate redisTemplate;

    public OtpService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // Generate and store OTP
    public String generateOtp(String email) {
        email = email.trim().toLowerCase(); // normalize key

        String otp = String.format("%06d", new Random().nextInt(999999));
        String key = OTP_PREFIX + email;

        Duration ttl = Duration.ofMinutes(5);
        Objects.requireNonNull(ttl, "TTL cannot be null");
        Objects.requireNonNull(otp, "OTP cannot be null");
        Objects.requireNonNull(key, "Key cannot be null");
        redisTemplate.opsForValue().set(key, otp, ttl);

        System.out.println("✅ OTP generated for " + email + ": " + otp);

        // Check Redis store confirmation
        String test = redisTemplate.opsForValue().get(key);
        System.out.println("🔍 Stored OTP in Redis: " + test);

        return otp;
    }

    // Verify OTP
    public boolean verifyOtp(String email, String otp) {
        email = email.trim().toLowerCase();
        String key = OTP_PREFIX + email;

        String storedOtp = redisTemplate.opsForValue().get(key);
        System.out.println("🔍 OTP retrieved from Redis for " + email + ": " + storedOtp);

        boolean valid = storedOtp != null && storedOtp.equals(otp);

        if (valid) {
            redisTemplate.delete(key);
            System.out.println("✅ OTP verified and deleted for " + email);
        } else {
            System.out.println("❌ Invalid or expired OTP for " + email);
        }

        return valid;
    }
}
