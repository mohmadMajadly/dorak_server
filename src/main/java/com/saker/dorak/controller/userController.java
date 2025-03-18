package com.saker.dorak.controller;

import com.saker.dorak.Entity.DTO.OTP_DTO;
import com.saker.dorak.Entity.DTO.VerificationResponse;
import com.saker.dorak.Entity.jwt;
import com.saker.dorak.Entity.users;
import com.saker.dorak.Entity.DTO.userRegisterDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.saker.dorak.Service.userService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class userController {
    private final userService userService;

    @PostMapping("/register")
    public ResponseEntity<jwt> registerUser(@RequestBody userRegisterDTO userRegisterDTO, HttpServletRequest request){
        try {
            users user = userRegisterDTO.convertToUser();
            String clientIp = getClientIp(request);
            jwt jwt = userService.createAccount(user, userRegisterDTO.getDevice_type(), clientIp);
            return ResponseEntity.ok(jwt);
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(null);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(null);
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For"); // في حال كان هناك Proxy
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("X-Real-IP"); // خيار إضافي
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr(); // الخيار الأخير
        }
        return clientIp;
    }

    @GetMapping("/checkUsernameExistence")
    public ResponseEntity<Boolean> checkUsernameExistence(@RequestParam String username){
        try {
            return ResponseEntity.ok(userService.checkUsernameExistence(username));
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(false);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(false);
        }
    }

    @PostMapping("/verify-credentials")
    public ResponseEntity<?> verifyCredentials(@RequestBody userRegisterDTO request) {
        boolean isValid = userService.validateCredentials(
                request.getUsername(),
                request.getPassword()
        );

        boolean isProfileComplete = userService.checkPhoneExists(request.getUsername());

        return ResponseEntity.ok(new VerificationResponse(
                true,
                isValid,
                true,
                isProfileComplete
        ));
    }

    @GetMapping("/check-conditions")
    public ResponseEntity<VerificationResponse> checkConditions(@RequestParam String username) {
        try {
            VerificationResponse conditions = userService.checkUserConditions(username);
            return ResponseEntity.ok(conditions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/send-verification")
    public ResponseEntity<?> sendVerificationCode(@RequestBody OTP_DTO request) {
        try {
            return ResponseEntity.ok(userService.sendVerificationCode(Long.parseLong(request.getVerificationId()),request.getPhoneNumber(),request.getCountryCode()));
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(new OTP_DTO("invalid user id"));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(new OTP_DTO("database error or server error"));
        }
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> VerificationCode(@RequestBody OTP_DTO request) {
        try {
            return ResponseEntity.ok(userService.VerificationCode(Long.parseLong(request.getVerificationId()),request.getPhoneNumber(),request.getCountryCode(), request.getCode()));
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(new OTP_DTO("invalid code"));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(new OTP_DTO("database error or server error"));
        }
    }
}
