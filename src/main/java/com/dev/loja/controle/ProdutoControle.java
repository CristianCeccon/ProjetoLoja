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
import com.dev.loja.modelos.Estado;
import com.dev.loja.modelos.Imagem;
import com.dev.loja.modelos.Produto;
import com.dev.loja.repositorios.CategoriaRepositorio;
import com.dev.loja.repositorios.EstadoRepositorio;
import com.dev.loja.repositorios.ImagemRepositorio;
import com.dev.loja.repositorios.MarcaRepositorio;
import com.dev.loja.repositorios.ProdutoRepositorio;

@Controller
public class ProdutoControle {
	
	
	private static String caminhoImagens = "C:\\Frank\\Imagens-loja\\";

	@Autowired
    private ImagemRepositorio imagemRepositorio;

	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	
	@Autowired
    private CategoriaRepositorio categoriaRepositorio;
	
	@Autowired
	private MarcaRepositorio marcaRepositorio;
	

	@GetMapping("/administrativo/produtos/cadastrar")
	public ModelAndView cadastrar(Produto produto) {
		ModelAndView mv = new ModelAndView("/administrativo/produtos/cadastro");
		mv.addObject("produto", produto);
		mv.addObject("categoriasLista", categoriaRepositorio.findAll());
		mv.addObject("marcasLista", marcaRepositorio.findAll());
		return mv;
	}

	@GetMapping("/administrativo/produtos/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
		//mv.addObject("listaProdutos", produtoRepositorio.findAll());
		List<Imagem> listaDeImagens = imagemRepositorio.findAll();
        List<Produto> listaDeProdutos = produtoRepositorio.findAll();
        for (Imagem imagem : listaDeImagens) {
            for(Produto prod: listaDeProdutos){
                if(imagem.getProduto().equals(prod)){
                    prod.setNomeImagem(imagem.getNome());
                }
            }
        }
        mv.addObject("listaProdutos", listaDeProdutos);
		return mv;
	}

	@GetMapping("/administrativo/produtos/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Produto> produto = produtoRepositorio.findById(id);
		return cadastrar(produto.get());
	}

	@GetMapping("/administrativo/produtos/remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Produto> produto = produtoRepositorio.findById(id);
		produtoRepositorio.delete(produto.get());
		return listar();
	}
	
	@GetMapping("/administrativo/produtos/mostrarImagem/{imagem}")
	@ResponseBody
	public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException {
		File imagemArquivo = new File(caminhoImagens+imagem);
		if(imagem!=null || imagem.trim().length()>0) {
			
			return Files.readAllBytes(imagemArquivo.toPath());
		}
		return null;
	}

	@PostMapping("/administrativo/produtos/salvar")
	public ModelAndView salvar(@Validated Produto produto, BindingResult result, 
			@RequestParam("file") List<MultipartFile> arquivo) {
		
		if (result.hasErrors()) {
			return cadastrar(produto);
		}
		
		produtoRepositorio.save(produto);
		
		try {
			
			if(!arquivo.isEmpty()) {
				Imagem imagem = new Imagem();
                produtoRepositorio.saveAndFlush(produto);
                for (MultipartFile file : arquivo) {
                    byte[] bytes = file.getBytes();
  
                    Path caminho = Paths.get(ImagemC.ImagemCaminho + String.valueOf(produto.getId()) + file.getOriginalFilename());
                    Files.write(caminho, bytes);
                    imagem.setNome(String.valueOf(produto.getId() + file.getOriginalFilename()));
                    imagem.setProduto(produto);
                    imagemRepositorio.saveAndFlush(imagem);
                }
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return listar();
	}

}
