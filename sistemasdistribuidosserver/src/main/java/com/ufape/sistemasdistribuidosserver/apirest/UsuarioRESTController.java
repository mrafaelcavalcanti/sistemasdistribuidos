package com.ufape.sistemasdistribuidosserver.apirest;

import com.ufape.sistemasdistribuidosserver.model.Usuario;
import com.ufape.sistemasdistribuidosserver.repository.UsuarioDAOI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRESTController {

    @Autowired
    UsuarioDAOI usuarioDAOI;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario addUsuario(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioDAOI.save(usuario);
        return novoUsuario;
    }
}