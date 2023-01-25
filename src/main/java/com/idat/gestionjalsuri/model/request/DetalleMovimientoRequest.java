package com.idat.gestionjalsuri.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DetalleMovimientoRequest {

    private String observacionMovimiento;
    private Long idMovAlmacen;
    private Long idProducto;
    private Integer cantidadProducto;
}
