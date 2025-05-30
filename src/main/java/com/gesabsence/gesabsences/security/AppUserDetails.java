package com.gesabsences.gesabsences.security;

import com.gesabsences.gesabsences.data.Entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class AppUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // Pas de rôles pour l’instant
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // ← correspond à ton champ dans User
    }

    @Override
    public String getUsername() {
        return user.getLogin(); // ← correspond à "Login" dans User
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