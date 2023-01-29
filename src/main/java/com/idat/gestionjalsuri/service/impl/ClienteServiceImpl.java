package com.idat.gestionjalsuri.service.impl;

import com.idat.gestionjalsuri.exception.ExceptionService;
import com.idat.gestionjalsuri.model.entity.Cliente;
import com.idat.gestionjalsuri.model.request.ClienteRequest;
import com.idat.gestionjalsuri.repository.ClienteRepository;
import com.idat.gestionjalsuri.service.IClienteService;
import com.idat.gestionjalsuri.util.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements IClienteService {


	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public Cliente registrar(ClienteRequest t) {

		if (this.existeNombre(t.getNombre())){
			throw new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO,Constante.MENSAGE_NOMBRE_EXISTE,HttpStatus.BAD_REQUEST);
		}
		if (this.existeDocumento(t.getDocumento())){
			throw new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO,Constante.MENSAGE_DOCUMENTO_EXISTE,HttpStatus.BAD_REQUEST);
		}
		if (this.existeEmail(t.getEmail())){
			throw new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO,Constante.MENSAGE_EMAIL_EXISTE,HttpStatus.BAD_REQUEST);
		}

		return this.clienteRepository.save(this.getCliente(t));
	}


	@Override
	public Cliente modificar(Long id,ClienteRequest t) {
		Cliente cliente=new Cliente();
		Optional<Cliente>oCliente=this.clienteRepository.findById(id);
		if (oCliente.isPresent()){
			if (this.existeNombre(t.getNombre())||this.existeEmail(t.getEmail())||this.existeDocumento(t.getDocumento())){
				throw new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO,Constante.MENSAGE_NOMBRE_EXISTE,HttpStatus.BAD_REQUEST);
			}
			cliente.setId(oCliente.get().getId());
			cliente.setNombre(t.getNombre());
			cliente.setDocumento(t.getDocumento());
			cliente.setTelefono(t.getTelefono());
			cliente.setEmail(t.getEmail());
			cliente.setEstado(Constante.ESTADO_ACTIVO);
			return this.clienteRepository.save(cliente);
		}else {
			throw  new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO,Constante.MENSAGE_NO_ENCONTRADO,HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public boolean eliminar(Long id) {
		Optional<Cliente> cliente = this.clienteRepository.findById(id);
			
		if(cliente.isPresent()) {
			this.clienteRepository.deleteById(id);
			return true;
		}else{
			throw new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO,Constante.MENSAGE_NO_ENCONTRADO, HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Cliente busca(Long id) {
		Optional<Cliente> cliente = this.clienteRepository.findById(id);
		
		if(cliente.isPresent()) {
			return cliente.get();
		}else{
			throw new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO,Constante.MENSAGE_NO_ENCONTRADO, HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public List<Cliente> listar() {
		List<Cliente>clientes= this.clienteRepository.findAll()
				.stream()
				.filter(c->c.getEstado().equalsIgnoreCase(Constante.ESTADO_ACTIVO))
				.collect(Collectors.toList());
		if (clientes.isEmpty()){
			throw new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO,Constante.LISTA_VACIA,HttpStatus.NOT_FOUND);
		}
		clientes.sort(Comparator.comparing(Cliente::getId)
				.reversed());
		return clientes;
	}

	@Override
	public Page<Cliente> listarPagina(Pageable page) {
		return this.clienteRepository.findAll(page);
	}

	private Cliente getCliente(ClienteRequest t) {
		Cliente cliente = new Cliente() ;
		cliente.setNombre(t.getNombre());
		cliente.setDocumento(t.getDocumento());
		cliente.setTelefono(t.getTelefono());
		cliente.setEmail(t.getEmail());
		cliente.setEstado(Constante.ESTADO_ACTIVO);

		return cliente;
	}

	private boolean existeNombre(String t){
		return this.clienteRepository.existsByNombre(t);
	}
	private boolean existeEmail(String t){
		return this.clienteRepository.existsByEmail(t);
	}
	private boolean existeDocumento(String t){
		return this.clienteRepository.existsByDocumento(t);
	}
}
