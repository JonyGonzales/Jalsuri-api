package com.idat.gestionjalsuri.service;

import java.util.List;

import com.idat.gestionjalsuri.exception.ExceptionService;
import com.idat.gestionjalsuri.model.entity.Insumo;
import com.idat.gestionjalsuri.model.request.InsumoRequest;

public interface IInsumoService {

    Insumo insertar (InsumoRequest InsumoRequest);
	Insumo actualizar (InsumoRequest InsumoRequest);
	//DataResponse listar () ;
	public List<Insumo> listar() ;

	Insumo buscarXid (Long id) throws ExceptionService;
	void eliminar (Long id) throws ExceptionService;
    
}
