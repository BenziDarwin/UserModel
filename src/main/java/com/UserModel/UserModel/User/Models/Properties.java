package com.UserModel.UserModel.User.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Properties {
    private HashMap<String,String> properties;
}
