package com.tcc.helpinghand.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcc.helpinghand.models.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class CurrentUser implements UserDetails {

    private User user;

    private Collection<? extends GrantedAuthority> authorities;

    public CurrentUser(User user) {
        this.user = user;
    }

    public static CurrentUser create(User user) {
        return new CurrentUser(user);
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
