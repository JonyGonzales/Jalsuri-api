package com.idat.gestionjalsuri.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DetalleMovimientoRequest {

    private String observaciones;
    private Long idMovAlmacen;
    private Long idProducto;
    private Integer cantidad;
}
