package com.idat.gestionjalsuri.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.idat.gestionjalsuri.controller.beam.PasswordRequest;
import com.idat.gestionjalsuri.model.entity.Usuario;
import com.idat.gestionjalsuri.model.request.GenericoRequest;
import com.idat.gestionjalsuri.model.request.UsuarioRequest;

public interface IUsuarioService {

	public Usuario registrar(UsuarioRequest t);

	public Usuario modificar(Long id, UsuarioRequest t);

	public boolean eliminar(Long id);

	public Usuario busca(Long id);

	public List<Usuario> listar();

	public Page<Usuario> listarPagina(Pageable page);

	public Usuario estado(Long id, GenericoRequest t);

	public Usuario cambiaPassword(Long id, PasswordRequest t);
}
