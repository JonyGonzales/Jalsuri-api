package com.idat.gestionjalsuri.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idat.gestionjalsuri.model.entity.TipoDocumento;
import com.idat.gestionjalsuri.service.ITipoDocumentoService;
import com.idat.gestionjalsuri.util.Constante;

@RestController
@RequestMapping(Constante.URLPREFIJO + Constante.URLSUBFIJOTIPODOCUMENTO)
@CrossOrigin(origins = { "http://192.168.3.25:4200", "http://localhost:4200", "https://jalsuriweb.000webhostapp.com" })
public class TipoDocumentoController {

	@Autowired
	private ITipoDocumentoService tipoDocumentoService;

	@GetMapping
	public ResponseEntity<List<TipoDocumento>> listar() {

		List<TipoDocumento> tipoDocumento = this.tipoDocumentoService.listar();

		if (tipoDocumento.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(tipoDocumento);

	}

	// Metodo para buscar por ID
	@GetMapping("/{id}")
	public ResponseEntity<TipoDocumento> buscar(@PathVariable("id") Long id) {
		TipoDocumento td = tipoDocumentoService.busca(id);
		if (td == null) {
			td = new TipoDocumento();
		}
		return new ResponseEntity<>(td, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<TipoDocumento> insertar(@RequestBody TipoDocumento TipoDocumento) {
		return new ResponseEntity<>(this.tipoDocumentoService.registrar(TipoDocumento), HttpStatus.CREATED);
	}

}
