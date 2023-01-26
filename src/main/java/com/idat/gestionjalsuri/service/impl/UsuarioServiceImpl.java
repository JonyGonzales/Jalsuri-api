package com.idat.gestionjalsuri.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.idat.gestionjalsuri.exception.ExceptionService;
import com.idat.gestionjalsuri.model.entity.Usuario;
import com.idat.gestionjalsuri.model.request.UsuarioRequest;
import com.idat.gestionjalsuri.repository.UsuarioRepository;
import com.idat.gestionjalsuri.service.IUsuarioService;
import com.idat.gestionjalsuri.util.Constante;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Usuario registrar(UsuarioRequest t) {
		if (this.existeNombreUsuario(t)) {
            throw new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, Constante.MENSAGE_NOMBRE_EXISTE, HttpStatus.BAD_REQUEST);
        }
		if (this.existeEmail(t.getEmail())){
			throw new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO,Constante.MENSAGE_EMAIL_EXISTE,HttpStatus.BAD_REQUEST);
		}
        return this.usuarioRepository.save(this.getUsuario(t));
	}

	@Override
	public Usuario modificar(Long id, UsuarioRequest t) {
		 Usuario usuario = new Usuario();
	        Optional<Usuario> oUsuario = Optional.ofNullable(this.usuarioRepository.findById(id)
	                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, Constante.MENSAGE_NO_ENCONTRADO, HttpStatus.NOT_FOUND)));

	        if (this.existeNombreUsuario(t)) {
	            throw new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, Constante.MENSAGE_NOMBRE_EXISTE, HttpStatus.BAD_REQUEST);
	        }
	        usuario.setId(oUsuario.get().getId());
	        usuario.setNombre(t.getNombre());
	        usuario.setEmail(t.getEmail());
	        usuario.setPassword(t.getPassword());
	        usuario.setRole(t.getRole());
	        usuario.setArea(t.getArea());
	        usuario.setEstado(Constante.ESTADO_ACTIVO);
	        return this.usuarioRepository.save(usuario);
	}

	@Override
	public boolean eliminar(Long id) {
		Optional<Usuario> usuario = Optional.ofNullable(this.usuarioRepository.findById(id)
                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, Constante.MENSAGE_NO_ENCONTRADO, HttpStatus.NOT_FOUND)));
        this.usuarioRepository.deleteById(id);
        return true;
	}

	@Override
	public Usuario busca(Long id) {
		Optional<Usuario> usuario = Optional.ofNullable(this.usuarioRepository.findById(id)
                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, Constante.MENSAGE_NO_ENCONTRADO, HttpStatus.NOT_FOUND)));
        return usuario.get();
	}

	@Override
	public List<Usuario> listar() {
		List<Usuario> usuarios = this.usuarioRepository.findAll()
                .stream()
                .filter(c -> c.getEstado().equalsIgnoreCase("A"))
                .collect(Collectors.toList());
        if (usuarios.isEmpty()) {
            throw new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, Constante.LISTA_VACIA, HttpStatus.NOT_FOUND);
        }
        usuarios.sort(Comparator.comparing(Usuario::getId)
                .reversed());
        return usuarios;
	}

	@Override
	public Page<Usuario> listarPagina(Pageable page) {
		return this.usuarioRepository.findAll(page);
	}
	
	 private Usuario getUsuario(UsuarioRequest t) {
	        Usuario usuario = new Usuario();
	        usuario.setNombre(t.getNombre());
	        usuario.setEmail(t.getEmail());
	        usuario.setPassword(t.getPassword());
	        usuario.setRole(t.getRole());
	        usuario.setArea(t.getArea());
	        usuario.setEstado("A");

	        return usuario;
	    }
	
	private boolean existeNombreUsuario(UsuarioRequest t) {
        return this.usuarioRepository.existsByNombre(t.getNombre());
    }
	
	private boolean existeEmail(String t){
		return this.usuarioRepository.existsByEmail(t);
	}

}
