package com.ufape.sistemasdistribuidosserver.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PagesController {
    
    @RequestMapping("/home")
    public String home (HttpServletRequest request, Principal principal) {
        
        if (principal!=null) {
			System.out.println("Authorities do user" +principal.getName());
        }
        return "home";

    }

    @RequestMapping("/usuario")
    public String usuario (HttpServletRequest request, Principal principal) {
        
        if (principal!=null) {
			System.out.println("Authorities do user" +principal.getName());
        }
        return "usuario";

    }

    @RequestMapping("/arquivos")
    public String carrinho (HttpServletRequest request, Principal principal) {
        if (principal!=null) {
			System.out.println("Authorities do user" +principal.getName());
        }
        return "arquivos";
    }
    
}
