package com.example.movie.service;

import java.time.Duration;
import java.util.Objects;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final StringRedisTemplate redisTemplate;

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine, StringRedisTemplate redisTemplate) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.redisTemplate = redisTemplate;
    }

    public void sendOtpEmail(String toEmail, String otp) {
        Objects.requireNonNull(toEmail, "toEmail must not be null");
        Objects.requireNonNull(otp, "otp must not be null");

        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String redisKey = "otp_rate_limit:" + toEmail;

        if (ops.get(redisKey) != null) {
            throw new RuntimeException("OTP already sent recently. Try again after 1 minute.");
        }
        try {
            // Create email context for Thymeleaf
            Context context = new Context();
            context.setVariable("otp", otp);

            // Generate HTML content from Thymeleaf template
            String htmlContent = Objects.requireNonNull(
                    templateEngine.process("otp-email", context),
                    "Template processing returned null"
            );

            // Create MIME message
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject("Your OTP Code");
            helper.setText(htmlContent, true);

            // Send email
            mailSender.send(message);

            // ⏱️ Store rate limit key in Redis (expire in 60 seconds)
            Duration ttl = Duration.ofSeconds(60);
            Objects.requireNonNull(ttl, "ttl must not be null"); // defensive, usually unnecessary
            ops.set(redisKey, "sent", ttl);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send OTP email", e);
        }
    }
}
