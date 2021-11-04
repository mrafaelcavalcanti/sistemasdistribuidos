package com.ufape.sistemasdistribuidosserver.service;

import java.util.Optional;

import com.ufape.sistemasdistribuidosserver.config.MyUserDetails;
import com.ufape.sistemasdistribuidosserver.model.Usuario;
import com.ufape.sistemasdistribuidosserver.repository.UsuarioDAOI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UsuarioDAOI usuarioDAOI;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional <Usuario> oUsuario = usuarioDAOI.findUsuarioByUsename(username);
        System.out.println(username);

        oUsuario.orElseThrow(() -> new UsernameNotFoundException("Not Found"+ username));

        return new MyUserDetails(oUsuario.get());
    }

    
}
