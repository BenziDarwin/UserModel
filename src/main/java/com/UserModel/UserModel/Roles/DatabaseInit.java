package com.UserModel.UserModel.Roles;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DatabaseInit implements CommandLineRunner {
    @Autowired
    private RolesRepository rolesRepository;
    @Override
    public void run(String... args) {
        Roles role = Roles.builder().roleName("ADMIN").permissions(new ArrayList<>(List.of("WRITE","READ","UPDATE","DELETE"))).build();
        Optional<Roles> checkRole = rolesRepository.findRolesByName("ADMIN");
        if(checkRole.isEmpty()) {
            rolesRepository.save(role);
        }
    }
}
