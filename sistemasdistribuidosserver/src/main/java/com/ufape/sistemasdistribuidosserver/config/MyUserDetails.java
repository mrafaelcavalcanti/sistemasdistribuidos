package com.ufape.sistemasdistribuidosserver.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ufape.sistemasdistribuidosserver.model.Usuario;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



public class MyUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String userName;
    private String password;
    private boolean active = true;
    private List<GrantedAuthority> autorities;

    public MyUserDetails(String userName) {
        this.userName = userName;
    }
    
    public MyUserDetails() {
    }

    public MyUserDetails(Usuario usuario) {
        this.id = usuario.getId();
        this.userName = usuario.getNome();
        this.password = usuario.getSenha();
        this.active = true;
        List<GrantedAuthority> lista = new ArrayList<>();

        lista.add(new SimpleGrantedAuthority("LOGADO"));

        this.autorities = lista;
    }

    public Long getId() {
        return this.id;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
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
        return this.active;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.autorities;
    }
}
