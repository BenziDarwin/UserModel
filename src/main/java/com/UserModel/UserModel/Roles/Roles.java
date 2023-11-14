package com.UserModel.UserModel.Roles;

import com.UserModel.UserModel.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Roles {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String roleName;
    private ArrayList<String> permissions;
    private ArrayList<String> activities;
    @OneToMany(cascade = CascadeType.DETACH)
    @Column(name = "role")
    private Collection<User> user;
}

