package com.idat.gestionjalsuri.service;

import java.util.List;

import com.idat.gestionjalsuri.exception.ExceptionService;
import com.idat.gestionjalsuri.model.entity.Producto;
import com.idat.gestionjalsuri.model.request.GenericoRequest;
import com.idat.gestionjalsuri.model.request.ProductoRequest;
import com.idat.gestionjalsuri.model.request.ProductoStockRequest;
import com.idat.gestionjalsuri.model.response.GenericResponse;

public interface IProductoService {

	Producto insertar (ProductoRequest productoRequest);
	GenericResponse actualizarStok (ProductoStockRequest productoRequest);
	List<Producto> listar () ;
	Producto buscarXid (Long id) throws ExceptionService;
	void eliminar (Long id) throws ExceptionService;
	Producto cambiaEstado (Long id, GenericoRequest genericoRequest);
	
}
