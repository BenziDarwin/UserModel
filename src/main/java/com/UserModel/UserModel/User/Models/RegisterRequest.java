package com.UserModel.UserModel.User.Models;

import lombok.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private Map<String, Object> dynamicProperties = new HashMap<>();

    private String firstname;
    private String lastname;
    private String email;
    private String persona;
    private String profileImage;
    private String role;
    private String password;

    public Map<String, Object> getDynamicProperties() {
        return dynamicProperties;
    }

    public void setDynamicProperties(Map<String, Object> dynamicProperties) {
        this.dynamicProperties = dynamicProperties;
    }

    public void setDynamicProperty(String key, Object value) {
        dynamicProperties.put(key, value);
    }

    public Object getDynamicProperty(String key) {
        return dynamicProperties.get(key);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private RegisterRequest registerRequest;

        private Builder() {
            this.registerRequest = new RegisterRequest();
        }

        public Builder firstname(String firstname) {
            this.registerRequest.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname) {
            this.registerRequest.lastname = lastname;
            return this;
        }

        public Builder email(String email) {
            this.registerRequest.email = email;
            return this;
        }

        public Builder persona(String persona) {
            this.registerRequest.persona = persona;
            return this;
        }

        public Builder profileImage(String profileImage) {
            this.registerRequest.profileImage = profileImage;
            return this;
        }

        public Builder role(String role) {
            this.registerRequest.role = role;
            return this;
        }

        public Builder password(String password) {
            this.registerRequest.password = password;
            return this;
        }

        public Builder addDynamicProperties(Object obj) {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    if (value != null && !field.getName().equals("dynamicProperties")) {
                        this.registerRequest.setDynamicProperty(field.getName(), value);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
            return this;
        }

        public RegisterRequest build() {
            return this.registerRequest;
        }
    }
}
