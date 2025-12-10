package ru.kliuevia.springapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String login;

    private String password;

    @Column(name = "group_number")
    private Integer groupNumber;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "activation_code")
    private UUID activationCode;

    private boolean enable;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }
}
