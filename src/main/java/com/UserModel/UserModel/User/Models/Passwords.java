package com.UserModel.UserModel.User.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Passwords {
    private String oldPassword;
    private String newPassword;
}
