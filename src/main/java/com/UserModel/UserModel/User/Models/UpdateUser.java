package com.UserModel.UserModel.User.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUser {
    private String firstname;
    private  String lastname;
    private  String email;
    private String profileImage;
    private String role;
    HashMap<String, String> properties = new HashMap<String, String>();
}
