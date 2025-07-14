package com.example.userapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.userapp.security.JwtUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users/token")
@RequiredArgsConstructor
public class TokenInfoController {

    private static final Logger log = LoggerFactory.getLogger(TokenInfoController.class);
    private final JwtUtil jwtUtil;

    @GetMapping("/extract-email")
    public ResponseEntity<String> extractEmail(@RequestHeader("Authorization") String bearerToken) {
        log.debug("Received request to extract email from token: {}", maskToken(bearerToken));
        
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            log.warn("Invalid token format received");
            return ResponseEntity.badRequest().body(null);
        }

        String token = bearerToken.substring(7);
        try {
            String email = jwtUtil.extractUsername(token);
            if (email != null) {
                log.debug("Successfully extracted email: {}", maskEmail(email));
                return ResponseEntity.ok(email);
            } else {
                log.warn("No email found in token");
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            log.error("Error extracting email from token: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/extract-user-id")
    public ResponseEntity<Long> extractUserId(@RequestHeader("Authorization") String bearerToken) {
        log.debug("Received request to extract user ID from token: {}", maskToken(bearerToken));
        
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            log.warn("Invalid token format received");
            return ResponseEntity.badRequest().body(null);
        }

        String token = bearerToken.substring(7);
        try {
            Long userId = jwtUtil.extractUserId(token);
            if (userId != null) {
                log.debug("Successfully extracted user ID: {}", userId);
                return ResponseEntity.ok(userId);
            } else {
                log.warn("No user ID found in token");
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            log.error("Error extracting user ID from token: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 用於遮罩敏感信息的輔助方法
    private String maskToken(String token) {
        if (token == null) return null;
        if (token.length() <= 10) return "***";
        return token.substring(0, 4) + "..." + token.substring(token.length() - 4);
    }

    private String maskEmail(String email) {
        if (email == null) return null;
        int atIndex = email.indexOf('@');
        if (atIndex <= 1) return email;
        return email.substring(0, 1) + "***" + email.substring(atIndex);
    }
}
