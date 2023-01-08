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

import com.idat.gestionjalsuri.model.entity.TipoMovimiento;
import com.idat.gestionjalsuri.service.ITipoMovimientoService;
import com.idat.gestionjalsuri.util.Constante;

@RestController
@RequestMapping(Constante.URLPREFIJO + Constante.URLSUBFIJOTIPOMOVIMIENTO)
@CrossOrigin(origins = {"http://192.168.3.25:4200","http://localhost:4200","https://jalsuriweb.000webhostapp.com"})
public class TipoMovimientoController {
	
	@Autowired
	private ITipoMovimientoService tipoMovimientoService;
	
	@GetMapping
	public ResponseEntity<List<TipoMovimiento>> listar() {

		List<TipoMovimiento> tipoMovimiento = this.tipoMovimientoService.listar();

		if (tipoMovimiento.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(tipoMovimiento);

	}

		// Metodo para buscar por ID
		@GetMapping("/{id}")
		public ResponseEntity<TipoMovimiento> buscar(@PathVariable("id") Long id) {
			TipoMovimiento tm = tipoMovimientoService.busca(id);
			if (tm == null) {
				tm = new TipoMovimiento();
			}
			return new ResponseEntity<>(tm, HttpStatus.OK);
		}
		@PostMapping
		public ResponseEntity<TipoMovimiento>insertar(@RequestBody TipoMovimiento tipoMovimiento){
		return new ResponseEntity<>(this.tipoMovimientoService.registrar(tipoMovimiento),HttpStatus.CREATED);
		}

}
