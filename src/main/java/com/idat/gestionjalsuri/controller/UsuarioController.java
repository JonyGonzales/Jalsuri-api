package com.idat.gestionjalsuri.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idat.gestionjalsuri.controller.beam.PasswordRequest;
import com.idat.gestionjalsuri.model.entity.Usuario;
import com.idat.gestionjalsuri.model.request.GenericoRequest;
import com.idat.gestionjalsuri.model.request.UsuarioRequest;
import com.idat.gestionjalsuri.service.IUsuarioService;
import com.idat.gestionjalsuri.util.Constante;

@RestController
@RequestMapping(Constante.URLPREFIJO + Constante.URLSUBFIJOUSUARIOS)
@CrossOrigin(origins = { Constante.CROSS_LOCAL, Constante.CROSS_WEB })
public class UsuarioController {

	@Autowired
	private IUsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<List<Usuario>> listar() {
		return ResponseEntity.ok(this.usuarioService.listar());

	}

	@PostMapping
	public ResponseEntity<Usuario> agregar(@RequestBody @Validated UsuarioRequest usuarioReq) {

		Usuario categoria = this.usuarioService.registrar(usuarioReq);

		if (categoria != null) {
			return ResponseEntity.created(URI.create("/" + categoria)).build();
		}

		return ResponseEntity.notFound().build();
	}

	// Metodo para buscar por ID
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> buscar(@PathVariable("id") Long id) {
		return new ResponseEntity<>(usuarioService.busca(id), HttpStatus.OK);
	}

	// Metodo para Actualizar por ID
	@PutMapping("/{id}")
	public ResponseEntity<Usuario> actualizarUsuarioxId(@PathVariable Long id,
			@RequestBody @Validated UsuarioRequest usuarioRequest) {

		return ResponseEntity.ok(usuarioService.modificar(id, usuarioRequest));

	}

	@PutMapping("/cambiaEstado/{id}")
	public ResponseEntity<Usuario> cambiaEstadoXId(@PathVariable Long id, @RequestBody GenericoRequest usuarioRequest) {
		Usuario usuario = usuarioService.busca(id);
		if (usuario == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		usuario.setEstado(usuarioRequest.getEstado());

		Usuario usuariomod = usuarioService.estado(id, usuarioRequest);

		return ResponseEntity.ok(usuariomod);

	}

	// Metodo que sirve para eliminar un item
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> eliminarUsuario(@PathVariable("id") Long id) {
		return ResponseEntity.ok(usuarioService.eliminar(id));
	}

	@PutMapping("/cambiaPassword/{id}")
	public ResponseEntity<Usuario> cambiaPassword(@PathVariable Long id, @RequestBody PasswordRequest passwordRequest) {
		Usuario usuario = usuarioService.busca(id);

		if (usuario == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		// if (passwordRequest.getOldPassword().equals(usuario.getPassword()) && passwordRequest.getNewPassword().length() > 3) {
		//if (passwordRequest.getOldPassword().equals(usuario.getPassword())) {

			Usuario usuarioActualizado = usuarioService.cambiaPassword(id, passwordRequest);
			return ResponseEntity.ok(usuarioActualizado);
		//}
		//return ResponseEntity.notFound().build();
	}

}
