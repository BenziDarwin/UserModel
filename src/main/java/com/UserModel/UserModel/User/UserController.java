package com.UserModel.UserModel.User;

import com.UserModel.UserModel.ResetTokens.Models.TokenModel;
import com.UserModel.UserModel.ResetTokens.ResetTokens;
import com.UserModel.UserModel.User.Models.*;
import jakarta.security.auth.message.AuthException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.logging.Handler;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    @PostMapping("/admin-register")
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody RegisterRequest request) throws AuthException {
        return ResponseEntity.ok(userService.registerAdmin(request));
    }

    @PostMapping("/user-register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegisterRequest request) throws AuthException {
        return ResponseEntity.ok(userService.registerUser(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(userService.login(request));
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/add-property")
    public ResponseEntity<HashMap<String, String>> addProperty(@RequestBody Properties properties) {
        return ResponseEntity.ok(userService.addProperties(properties.getProperties()));
    }

    @PostMapping("/delete-properties")
    public ResponseEntity<HashMap<String, String>> addProperty(@RequestBody RemoveProperties properties) {
        return ResponseEntity.ok(userService.removeProperties(properties.getProperties()));
    }

    @PostMapping("/edit-info")
    public ResponseEntity<HashMap<String, String>> editInfo(@RequestBody UpdateUser user) {
        return ResponseEntity.ok(userService.editInfo(user));
    }

    @PostMapping("/change-password")
    public ResponseEntity<HashMap<String, String>> changePassword(@RequestBody Passwords passwords) {
        try {
            return ResponseEntity.ok(userService.changePassword(passwords));
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/verify-password")
    public ResponseEntity<HashMap<String, String>> verifyPassword() {
        return ResponseEntity.ok(userService.emailTwoFactorAuthentication());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<HashMap<String, String>> resetPassword(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.resetPassword(loginRequest.getEmail()));
    }

    @PostMapping("/validate-token")
    public ResponseEntity<AuthenticationResponse> validateToken(@RequestBody TokenModel tokens) {
        try {
            return ResponseEntity.ok(userService.validateToken(tokens));
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<HashMap<String,Object>> verify() {
        return ResponseEntity.ok(userService.verify());
    }

}
