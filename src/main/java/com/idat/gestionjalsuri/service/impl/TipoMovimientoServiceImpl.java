package com.idat.gestionjalsuri.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.idat.gestionjalsuri.model.entity.TipoMovimiento;
import com.idat.gestionjalsuri.repository.TipoMovimientoRepository;
import com.idat.gestionjalsuri.service.ITipoMovimientoService;

@Service
public class TipoMovimientoServiceImpl implements ITipoMovimientoService{
	
	@Autowired
	private TipoMovimientoRepository movimientoRepository;

	@Override
	public TipoMovimiento registrar(TipoMovimiento t) {
		return this.movimientoRepository.save(t);
	}

	@Override
	public TipoMovimiento modificar(TipoMovimiento t) {
		return null;
	}

	@Override
	public boolean eliminar(Long id) {
		return false;
	}

	@Override
	public TipoMovimiento busca(Long id) {
	Optional<TipoMovimiento> tipoMovimiento = this.movimientoRepository.findById(id);
		
		if(tipoMovimiento.isPresent()) {
			return tipoMovimiento.get();
		}
				
		return null;
	}

	@Override
	public List<TipoMovimiento> listar() {
		return this.movimientoRepository.findAll();
	}

	@Override
	public Page<TipoMovimiento> listarPagina(Pageable page) {
		return this.movimientoRepository.findAll(page);
	}

}
