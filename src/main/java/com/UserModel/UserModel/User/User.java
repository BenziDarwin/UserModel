package com.UserModel.UserModel.User;

import com.UserModel.UserModel.Roles.Roles;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "Users")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

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

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String profileImage;

    @ElementCollection
    @MapKeyColumn(name = "myMapKey")
    @Column(name = "myMapValue")
    Map<String, String> properties = new HashMap<>();

    @Enumerated(EnumType.STRING)
    private Persona persona;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Roles role;

    public void setProperties(HashMap<String, String> properties) {
        this.properties.putAll(properties);
    }

    public void setNewProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(persona.name()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Optional: Add a static builder method
    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private User user;

        private UserBuilder() {
            this.user = new User();
        }

        public UserBuilder id(Long id) {
            this.user.id = id;
            return this;
        }

        public UserBuilder firstname(String firstname) {
            this.user.firstname = firstname;
            return this;
        }

        public UserBuilder lastname(String lastname) {
            this.user.lastname = lastname;
            return this;
        }

        public UserBuilder email(String email) {
            this.user.email = email;
            return this;
        }

        public UserBuilder password(String password) {
            this.user.password = password;
            return this;
        }

        public UserBuilder profileImage(String profileImage) {
            this.user.profileImage = profileImage;
            return this;
        }

        public UserBuilder properties(Map<String, String> properties) {
            this.user.properties.putAll(properties);
            return this;
        }

        public UserBuilder persona(Persona persona) {
            this.user.persona = persona;
            return this;
        }

        public UserBuilder role(Roles role) {
            this.user.role = role;
            return this;
        }

        public UserBuilder addDynamicProperties(Object obj) {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    if (value != null && !field.getName().equals("dynamicProperties")) {
                        this.user.setDynamicProperty(field.getName(), value.toString());
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
            return this;
        }

        public User build() {
            return this.user;
        }

        private void setDynamicProperty(String name, String string) {
            this.user.properties.put(name, string);
        }
    }
}
