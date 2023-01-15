package com.idat.gestionjalsuri.controller;

import com.idat.gestionjalsuri.exception.ExceptionService;
import com.idat.gestionjalsuri.model.entity.Insumo;
import com.idat.gestionjalsuri.model.request.InsumoRequest;
import com.idat.gestionjalsuri.model.response.DataResponse;
import com.idat.gestionjalsuri.service.IInsumoService;
import com.idat.gestionjalsuri.util.Constante;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constante.URLPREFIJO + Constante.URLSUBFIJOINSUMO)
@CrossOrigin(origins = { "http://192.168.3.25:4200", "http://localhost:4200", "https://jalsuriweb.000webhostapp.com" })
public class InsumoController {

	@Autowired
	private IInsumoService insumoService;

	@GetMapping
	// public ResponseEntity<DataResponse> listar() {
	// 	return ResponseEntity.ok(this.insumoService.listar());

	// }
	public ResponseEntity<List<Insumo>> listar(){
		return ResponseEntity.ok(this.insumoService.listar());
	}

	@PostMapping
	public ResponseEntity<Insumo> agregar(@RequestBody @Validated InsumoRequest insumoRequest) {
		return new ResponseEntity<>(this.insumoService.insertar(insumoRequest),HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Insumo> buscarXId(@PathVariable("id") Long id) throws ExceptionService {
		return new ResponseEntity<>(this.insumoService.buscarXid(id),HttpStatus.OK);
	}
	
		

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarInsumo(@PathVariable("id") Long id) throws ExceptionService {
		this.insumoService.eliminar(id);

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
