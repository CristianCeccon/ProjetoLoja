package com.dev.loja.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.loja.modelos.Imagem;

public interface ImagemRepositorio extends JpaRepository<Imagem, Long> {

} 