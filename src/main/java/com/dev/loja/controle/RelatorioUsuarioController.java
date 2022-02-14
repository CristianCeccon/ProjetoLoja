package com.dev.loja.controle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.dev.loja.modelos.Cliente;
import com.dev.loja.modelos.Compra;
import com.dev.loja.modelos.Estado;
import com.dev.loja.modelos.ItensCompra;
import com.dev.loja.modelos.Produto;
import com.dev.loja.repositorios.ClienteRepositorio;
import com.dev.loja.repositorios.CompraRepositorio;
import com.dev.loja.repositorios.EstadoRepositorio;
import com.dev.loja.repositorios.ItensCompraRepositorio;
import com.dev.loja.repositorios.ProdutoRepositorio;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
public class RelatorioUsuarioController {
	
	@Autowired
	private DataSource dataSource;

	@GetMapping("/listaUsuarios")
	public void listaUsuarios(HttpServletResponse response) throws JRException, SQLException, IOException{
		InputStream jasperFile = this.getClass().getResourceAsStream("/relatorios/RelatorioUsuario.jasper");
		
		JasperReport jasperRepor= (JasperReport) JRLoader.loadObject(jasperFile);
			
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperRepor, null, dataSource.getConnection());
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline;filename=relatoriousuario.pdf");
		
		OutputStream outStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
	}
	@GetMapping("/listaProdutos")
	public void listaProdutos(HttpServletResponse response) throws JRException, SQLException, IOException{
		InputStream jasperFile = this.getClass().getResourceAsStream("/relatorios/RelatorioProdutos.jasper");
		
		JasperReport jasperRepor= (JasperReport) JRLoader.loadObject(jasperFile);
			
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperRepor, null, dataSource.getConnection());
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline;filename=relatorioprodutos.pdf");
		
		OutputStream outStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
	}
	
}
