package com.UserModel.UserModel.Roles.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    private String roleName;
    private Integer weight;
    private ArrayList<String> permissions;
    private ArrayList<String> activities;
}
