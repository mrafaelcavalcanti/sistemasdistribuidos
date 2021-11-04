package com.ufape.sistemasdistribuidosserver.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.ufape.sistemasdistribuidosserver.model.Usuario;
import com.ufape.sistemasdistribuidosserver.repository.UsuarioDAOI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
	UsuarioDAOI usuarioDAOI;

	@RequestMapping(method= RequestMethod.GET,path={"/","/login"})
	public String entrar() {

		return "login";
	}

	@RequestMapping("/efetuarlogin")
	public ModelAndView efetuarlogin(@Valid @ModelAttribute("usuarioForm") Usuario usuarioForm, BindingResult bindingResults, HttpServletRequest request) { 
		
		ModelAndView mv= new ModelAndView();
		
		if (bindingResults.hasErrors()) {
			System.out.println(bindingResults.getAllErrors());
			mv.setViewName("login");
		} else {
			Usuario usuario = usuarioDAOI.findUsuario(usuarioForm.getNome(),usuarioForm.getSenha());
            System.out.println(usuario.getNome());
			//login existente, senha correta
			if (usuario!=null && usuario.getId()>0) {
				request.getSession().setAttribute("usuarioLogado", usuario);
				mv.setViewName("usuariologado");
				System.out.println("usuário logado com sucesso="+ usuarioForm.getNome());
			} else {
				mv.addObject("errors", "Usuário ou Senha inválido");
				mv.setViewName("login");
			}
		}
		return mv;
	}
    
}
