package com.dev.loja.controle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dev.loja.constants.ImagemC;
import com.dev.loja.modelos.Categoria;
import com.dev.loja.modelos.Estado;
import com.dev.loja.modelos.Imagem;
import com.dev.loja.modelos.Produto;
import com.dev.loja.repositorios.CategoriaRepositorio;
import com.dev.loja.repositorios.EstadoRepositorio;
import com.dev.loja.repositorios.ImagemRepositorio;
import com.dev.loja.repositorios.ProdutoRepositorio;

@Controller
public class CategoriaControle {
	
	@Autowired
    private CategoriaRepositorio categoriaRepositorio;
	
	
	@GetMapping("/administrativo/categorias/cadastrar")
	
    public ModelAndView cadastrar(Categoria categoria) {
		
        ModelAndView mv = new ModelAndView("administrativo/categorias/cadastro");    
        
        mv.addObject("categoria", categoria);
        
        return mv;
    }
	
	

    @GetMapping("/administrativo/categorias/listar")
    
    public ModelAndView listar() {
    	
        ModelAndView mv = new ModelAndView("administrativo/categorias/lista");
        
        mv.addObject("categoriasLista", categoriaRepositorio.findAll());
        
        return mv;
        
    }
    
    @GetMapping("/administrativo/categorias/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
    	
        Optional<Categoria> categoriaEscolha = categoriaRepositorio.findById(id);
        
        categoriaRepositorio.delete(categoriaEscolha.get());
        
        return listar();
    }
    
	@PostMapping("/administrativo/categorias/salvar")
		
	    public ModelAndView salvar(Categoria categoria, BindingResult result) {
		
	        if (result.hasErrors())
	        {
	            return cadastrar(categoria);
	        }
	        categoriaRepositorio.save(categoria);
	        
	        return listar();
	    }

    @GetMapping("/administrativo/categorias/editar/{id}")
    
    public ModelAndView editar(@PathVariable("id") Long id) {
    	
        Optional<Categoria> categoriaOptional = categoriaRepositorio.findById(id);
        
        return cadastrar(categoriaOptional.get());
    }
}
