package com.ufape.sistemasdistribuidosserver.controller;

import com.ufape.sistemasdistribuidosserver.model.Usuario;
import com.ufape.sistemasdistribuidosserver.repository.UsuarioDAOI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	@Autowired
	UsuarioDAOI usuarioDAOI;
	
	@RequestMapping(value = "/novo")
	public String novo(Long Id, Model model) {
		model.addAttribute("usuarioForm", new Usuario());
		System.out.println("testetss");
		return "cadastrarusuario";
	}
}
