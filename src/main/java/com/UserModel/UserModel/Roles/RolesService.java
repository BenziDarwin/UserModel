package com.UserModel.UserModel.Roles;

import com.UserModel.UserModel.Roles.Models.Role;
import com.UserModel.UserModel.User.Persona;
import com.UserModel.UserModel.User.UsersRepository;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class RolesService {
    private RolesRepository rolesRepository;
    private UsersRepository userRepository;

    public HashMap<String, String> addRole(Role role) throws AuthException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        if(user.getPersona() == Persona.ADMIN) {
            Roles newRole = Roles.builder()
                    .roleName(role.getRoleName())
                    .permissions(role.getPermissions()).build();
            rolesRepository.save(newRole);
            HashMap<String, String> res = new HashMap<>();
            res.put("result", "success");
            return res;
        } else {
            throw new AuthException("Insufficient Permissions to add Role!");
        }

    }
}
