package com.idat.gestionjalsuri.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idat.gestionjalsuri.model.entity.TipoMovimiento;

public interface TipoMovimientoRepository extends JpaRepository<TipoMovimiento, Long> {
	
}
