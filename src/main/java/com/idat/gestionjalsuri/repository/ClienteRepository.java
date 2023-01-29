package com.idat.gestionjalsuri.repository;

import com.idat.gestionjalsuri.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	boolean existsByNombre(String nombre);
	boolean existsByEmail(String email);
	boolean existsByDocumento(String documento);

}
