package com.example.userapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.userapp.security.JwtUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class TokenValidationController {

    private static final Logger log = LoggerFactory.getLogger(TokenValidationController.class);
    private final JwtUtil jwtUtil;

    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String bearerToken) {
        log.debug("收到令牌驗證請求，Bearer token 長度: {}", 
            bearerToken != null ? bearerToken.length() : 0);

        if (bearerToken == null) {
            log.warn("驗證失敗：Bearer token 為 null");
            return ResponseEntity.ok(false);
        }

        if (!bearerToken.startsWith("Bearer ")) {
            log.warn("驗證失敗：非法的 Bearer token 格式");
            return ResponseEntity.ok(false);
        }

        String token = bearerToken.substring(7);
        log.debug("提取的 JWT token 長度: {}", token.length());

        try {
            log.debug("開始驗證 JWT token");
            boolean isValid = jwtUtil.validateToken(token);
            
            if (isValid) {
                log.debug("JWT token 驗證成功");
            } else {
                log.warn("JWT token 驗證失敗");
            }
            
            return ResponseEntity.ok(isValid);
        } catch (Exception e) {
            log.error("JWT token 驗證過程中發生錯誤: {}", e.getMessage());
            return ResponseEntity.ok(false);
        }
    }
}

