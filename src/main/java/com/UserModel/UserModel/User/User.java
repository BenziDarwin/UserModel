package com.UserModel.UserModel.User;

import com.UserModel.UserModel.QuarterlyGoal.QuarterlyGoal;
import com.UserModel.UserModel.Roles.Roles;
import com.UserModel.UserModel.task.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Entity
@Table(name = "Users")
@Data
@Builder
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
    private  String lastname;
    private  String email;
    private  String password;
    private String profileImage;
    @ElementCollection
    @MapKeyColumn(name="myMapKey")
    @Column(name="myMapValue")
    Map<String, String> properties = new HashMap<String, String>();
    @Enumerated(EnumType.STRING)
    private Persona persona;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Roles role;

    @OneToMany(mappedBy = "assignedTo", cascade = CascadeType.DETACH)
    @Column(name = "assignedTo")
    @JsonIgnore
    private List<Task> tasks;

    public void setProperties(HashMap<String, String> properties) {
        this.properties.putAll(properties);
    }

    public void setNewProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(persona.name()));
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
}
