package tech.beetwin.stereoscopy.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
    private final Collection<? extends GrantedAuthority> authorities;
    private final String username;
    private final String password;

    private final String name;

    private final String surname;

    private final Boolean enabled;

    private final Boolean confirmed;

    private final Long id;

    public UserDetailsImpl(Collection<? extends GrantedAuthority> authorities, String username, String password, String name, String surname, Boolean enabled, Boolean confirmed, Long id) {
        this.authorities = authorities;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.enabled = enabled;
        this.confirmed = confirmed;
        this.id = id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return confirmed;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
