package com.UserModel.UserModel.User;

import com.UserModel.UserModel.User.Models.*;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.logging.Handler;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> greet(@RequestBody RegisterRequest request) throws AuthException {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> greet(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
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

}
