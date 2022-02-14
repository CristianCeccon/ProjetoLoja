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
import com.dev.loja.modelos.Marca;
import com.dev.loja.modelos.Produto;
import com.dev.loja.repositorios.CategoriaRepositorio;
import com.dev.loja.repositorios.EstadoRepositorio;
import com.dev.loja.repositorios.ImagemRepositorio;
import com.dev.loja.repositorios.MarcaRepositorio;
import com.dev.loja.repositorios.ProdutoRepositorio;

@Controller
public class MarcaControle {
	
	@Autowired
    private MarcaRepositorio marcaRepositorio;

    @GetMapping("/administrativo/marcas/cadastrar")
    public ModelAndView cadastrar(Marca marca) {
        ModelAndView mv = new ModelAndView("administrativo/marcas/cadastro");
        mv.addObject("marca", marca);
        return mv;
    }

    @GetMapping("/administrativo/marcas/listar")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("administrativo/marcas/lista");
        mv.addObject("marcasLista", marcaRepositorio.findAll());
        return mv;
    }

    @GetMapping("/administrativo/marcas/editar/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        Optional<Marca> marcaOptional = marcaRepositorio.findById(id);
        return cadastrar(marcaOptional.get());
    }

    @GetMapping("/administrativo/marcas/remover/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Marca> marcaOptional = marcaRepositorio.findById(id);
        marcaRepositorio.delete(marcaOptional.get());
        return listar();
    }

    @GetMapping("/administrativo/marcas/remover")
    public ModelAndView removerAll() {
    	marcaRepositorio.deleteAll();
        return listar();
    }

    @PostMapping("/administrativo/marcas/salvar")
    public ModelAndView salvar(Marca marca, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(marca);
        }
        marcaRepositorio.save(marca);
        return listar();
    }
}
