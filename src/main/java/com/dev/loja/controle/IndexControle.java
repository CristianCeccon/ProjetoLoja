package com.dev.loja.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dev.loja.modelos.Cidade;
import com.dev.loja.repositorios.ProdutoRepositorio;


@Controller
public class IndexControle {
	
	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	
	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("/index");
		mv.addObject("listaProdutos", produtoRepositorio.findAll());
		return mv;
	}

}
