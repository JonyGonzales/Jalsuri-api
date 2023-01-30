package com.idat.gestionjalsuri.controller;

import com.idat.gestionjalsuri.model.entity.Cliente;
import com.idat.gestionjalsuri.model.request.ClienteRequest;
import com.idat.gestionjalsuri.service.IClienteService;
import com.idat.gestionjalsuri.util.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(Constante.URLPREFIJO + Constante.URLSUBFIJO_CLIENTE)
@CrossOrigin(origins = { Constante.CROSS_LOCAL,Constante.CROSS_WEB })
public class ClienteController {

	@Autowired
	private IClienteService clienteService;

	@GetMapping
	public ResponseEntity<List<Cliente>> listar() {
		return ResponseEntity.ok(this.clienteService.listar());
	}

	@PostMapping
	public ResponseEntity<Cliente> agregar(@RequestBody @Validated ClienteRequest request) {

		Cliente cliente = this.clienteService.registrar(request);

		if (cliente != null) {
			return ResponseEntity.created(URI.create("/" + cliente)).build();
		}

		return ResponseEntity.notFound().build();
	}

	// Metodo para buscar por ID
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscar(@PathVariable("id") Long id) {
		return new ResponseEntity<>(clienteService.busca(id), HttpStatus.OK);
	}

	// Metodo para Actualizar por ID
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> actualizarClientexId(@PathVariable Long id,
			@RequestBody @Validated ClienteRequest request) {
		return ResponseEntity.ok(clienteService.modificar(id,request));
	}



	// Metodo que sirve para eliminar un item
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> eliminarCliente(@PathVariable("id") Long id) {
		return ResponseEntity.ok(clienteService.eliminar(id));
	}

}
