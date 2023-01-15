package com.idat.gestionjalsuri.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idat.gestionjalsuri.model.entity.Insumo;

public interface InsumoRepository extends JpaRepository<Insumo,Long>{
    boolean existsByNombre(String nombre);
    
}
