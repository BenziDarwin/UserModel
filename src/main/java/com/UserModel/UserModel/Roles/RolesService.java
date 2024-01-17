package com.UserModel.UserModel.Roles;

import com.UserModel.UserModel.Roles.Models.Role;
import com.UserModel.UserModel.User.Persona;
import com.UserModel.UserModel.User.User;
import com.UserModel.UserModel.User.UsersRepository;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolesService {
    private final RolesRepository rolesRepository;
    private final UsersRepository userRepository;

    public HashMap<String, String> addRole(Role role) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        if(user.getPersona().equals(Persona.ADMIN)||user.getRole().getRoleName().equalsIgnoreCase("ADMIN")) {
            Optional<Roles> getRole = rolesRepository.findRolesByName(role.getRoleName().toUpperCase());
            if(getRole.isPresent()) {
                throw new Exception("This role already exists!");
            } else {
                ArrayList<String> lst = role.getPermissions();
                lst.forEach(permission -> permission.toUpperCase());
                lst.stream().filter(permission -> List.of("WRITE", "READ","DELETE","UPDATE").contains(permission));
                Roles newRole = Roles.builder()
                        .roleName(role.getRoleName().toUpperCase().strip())
                        .department(role.getDepartment())
                        .permissions(lst)
                        .activities(role.getActivities())
                        .build();
                rolesRepository.save(newRole);
                HashMap<String, String> res = new HashMap<>();
                res.put("result", "success");
                return res;
            }
        } else {
            throw new AuthException("Insufficient Permissions to add Role!");
        }

    }


    public HashMap<String, String> removeRole(String roleName) throws AuthException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        if(user.getPersona().equals(Persona.ADMIN) || user.getRole().getRoleName().equalsIgnoreCase("ADMIN")) {
            Roles role = rolesRepository.findRolesByName(roleName.toUpperCase()).orElseThrow(() -> new IllegalArgumentException("Invalid role name!"));
            rolesRepository.delete(role);
            HashMap<String, String> res = new HashMap<>();
            res.put("result", "success");
            return res;
        } else {
            throw new AuthException("Insufficient Permissions to remove Role!");
        }
    }

    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }

    @Transactional
    public HashMap<String, String> updatePermissions(Role role) throws AuthException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        if(user.getPersona() == Persona.ADMIN || user.getRole().getRoleName().equalsIgnoreCase("ADMIN")) {
            Roles checkRole = rolesRepository.findRolesByName(role.getRoleName().toUpperCase()).orElseThrow(() -> new IllegalArgumentException("Invalid role name!"));
            ArrayList<String> lst = role.getPermissions();
            lst.forEach(permission -> permission.toUpperCase());
            lst.stream().filter(permission -> List.of("WRITE", "READ","DELETE","UPDATE").contains(permission));
            checkRole.setPermissions(lst);
            HashMap<String, String> res = new HashMap<>();
            res.put("result", "success");
            return res;
        } else {
            throw new AuthException("Insufficient Permissions to update role permissions!");
        }
    }

    @Transactional
    public HashMap<String, String> updateActivities(Role role) throws AuthException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        if(user.getPersona() == Persona.ADMIN || user.getRole().getRoleName().equalsIgnoreCase("ADMIN")) {
            Roles checkRole = rolesRepository.findRolesByName(role.getRoleName().toUpperCase()).orElseThrow(() -> new IllegalArgumentException("Invalid role name!"));
            checkRole.setActivities(role.getActivities());
            HashMap<String, String> res = new HashMap<>();
            res.put("result", "success");
            return res;
        } else {
            throw new AuthException("Insufficient Permissions to update role permissions!");
        }
    }
    @Transactional
    public HashMap<String, String> updateUserRole(String role, Long userId) throws AuthException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        if(user.getPersona() == Persona.ADMIN || user.getRole().getRoleName().equalsIgnoreCase("ADMIN")) {
            User userupdate = userRepository.findById(userId).orElseThrow();
            userupdate.setRole(rolesRepository.findRolesByName(role).orElseThrow());
            HashMap<String, String> res = new HashMap<>();
            res.put("result", "success");
            return res;
        }else {
            throw new AuthException("Insufficient Permissions to update User Role!");
        }
    }

    @Transactional
    public HashMap<String,String> updateRole(Role role) throws AuthException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        if(user.getPersona() == Persona.ADMIN || user.getRole().getRoleName().equalsIgnoreCase("ADMIN")) {
            Roles checkRole = rolesRepository.findRolesByName(role.getRoleName().toUpperCase()).orElseThrow(() -> new IllegalArgumentException("Invalid role name!"));
            checkRole.setRoleName(role.getRoleName().toUpperCase().strip());
            checkRole.setWeight(role.getWeight());
            checkRole.setPermissions(role.getPermissions());
            checkRole.setActivities(role.getActivities());
            HashMap<String, String> res = new HashMap<>();
            res.put("result", "success");
            return res;
        }else {
            throw new AuthException("Insufficient Permissions to update User Role!");
        }

    }

    public List<Roles> getRoles() {
        return rolesRepository.findAll();
    }

    public Roles viewRole(Long id) throws Exception {
        return rolesRepository.findById(id).orElseThrow(()-> new Exception("Role doesn't exist!"));
    }
}
