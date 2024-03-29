package com.UserModel.UserModel.User;

import com.UserModel.UserModel.ResetTokens.Models.TokenModel;
import com.UserModel.UserModel.User.Models.*;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/admin-register")
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody RegisterRequest request) throws AuthException {
        return ResponseEntity.ok(userService.registerAdmin(request));
    }

    @PostMapping("/user-register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegisterRequest request) throws AuthException {
        return ResponseEntity.ok(userService.registerUser(request));
    }

    @PostMapping("/user-details")
    public ResponseEntity<HashMap<String, String>> userDetails(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.getUserByEmail(request.getEmail()));
    }

    @GetMapping("/get-users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(userService.login(request));
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/get-users/{userID}")
    public ResponseEntity<?> getUser(@PathVariable Long userID) {
        try{
            Map<String,Object> response = userService.getSingleUser(userID);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
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
