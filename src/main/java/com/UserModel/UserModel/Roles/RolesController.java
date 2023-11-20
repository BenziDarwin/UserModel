package com.UserModel.UserModel.Roles;

import com.UserModel.UserModel.Roles.Models.Role;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RolesController {
    private final RolesService rolesService;

    @PostMapping("/add-role")
    public ResponseEntity<HashMap<String, String>> addRole(@RequestBody Role role) {
        try {
            return ResponseEntity.ok(rolesService.addRole(role));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/get-roles")
    public  ResponseEntity<List<Roles>> getAllRoles() {
        return ResponseEntity.ok(rolesService.getAllRoles());
    }

    @PostMapping("/update-role")
    public  ResponseEntity<HashMap<String, String>> updateRole(@RequestBody Role role) {
        try {
            return ResponseEntity.ok(rolesService.updateRole(role));
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/delete-role")
    public ResponseEntity<HashMap<String, String>> deleteRole(@RequestBody Role role) {
        try {
            return ResponseEntity.ok(rolesService.removeRole(role.getRoleName()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/update-permissions")
    public ResponseEntity<HashMap<String, String>> updatePermissions(@RequestBody Role role) {
        try {
            return ResponseEntity.ok(rolesService.updatePermissions(role));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/update-activities")
    public ResponseEntity<HashMap<String, String>> updateActivities(@RequestBody Role role) {
        try {
            return ResponseEntity.ok(rolesService.updateActivities(role));
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/view-role/{id}")
    public ResponseEntity<Roles> viewRole(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(rolesService.viewRole(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/view-all-roles")
    public ResponseEntity<List<Roles>> getRoles() {
        return ResponseEntity.ok(rolesService.getRoles());
    }
}
