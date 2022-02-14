package com.dev.loja.controle;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dev.loja.modelos.Cidade;


@Controller
public class NegadoControle {
	
	@GetMapping("/negadoAdministrativo")
	public ModelAndView negadoAdministrativo(Cidade cidade) {
		ModelAndView mv = new ModelAndView("/negadoAdministrativo");
		return mv;
	}
	
	@GetMapping("/negadoCliente")
	public ModelAndView negadoCliente() {
		ModelAndView mv = new ModelAndView("/negadoCliente");
		return mv;
	}

}
