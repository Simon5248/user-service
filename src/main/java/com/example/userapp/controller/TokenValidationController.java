package com.example.userapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.userapp.security.JwtUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class TokenValidationController {

    private final JwtUtil jwtUtil;

    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            try {
                // 使用現有的 JwtTokenProvider 來驗證令牌
                // 使用 JwtUtil 來驗證令牌
                boolean isValid = jwtUtil.validateToken(token);
                return ResponseEntity.ok(isValid);
            } catch (Exception e) {
                return ResponseEntity.ok(false);
            }
        }
        return ResponseEntity.ok(false);
    }
}
