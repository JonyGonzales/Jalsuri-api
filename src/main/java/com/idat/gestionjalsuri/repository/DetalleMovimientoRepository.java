package com.idat.gestionjalsuri.repository;

import com.idat.gestionjalsuri.model.entity.DetalleMovimiento;
import com.idat.gestionjalsuri.model.response.VistaDetalleResponse;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DetalleMovimientoRepository extends JpaRepository<DetalleMovimiento, Long> {


    @Query(nativeQuery = true, value = "SELECT * FROM vista_detalle_movs")
    List<VistaDetalleResponse> listar_productos_de_Movimiento();

}
