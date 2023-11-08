package com.UserModel.UserModel.Roles;

import com.UserModel.UserModel.Roles.Models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
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
}
