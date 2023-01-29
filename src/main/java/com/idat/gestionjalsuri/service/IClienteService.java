package com.idat.gestionjalsuri.service;

import com.idat.gestionjalsuri.model.entity.Cliente;
import com.idat.gestionjalsuri.model.request.ClienteRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClienteService {
	

	public Cliente registrar(ClienteRequest t) ;

	
	public Cliente modificar(Long id,ClienteRequest t) ;

	
	public boolean eliminar(Long id) ;

	
	public Cliente busca(Long id) ;

	
	public List<Cliente> listar() ;

	
	public Page<Cliente> listarPagina(Pageable page);

}
