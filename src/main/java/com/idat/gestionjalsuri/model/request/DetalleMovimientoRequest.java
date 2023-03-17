package com.idat.gestionjalsuri.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DetalleMovimientoRequest {

    private String observaciones;

    @NotNull
    private Long idMovAlmacen;
	@NotBlank
    private Long idProducto;
    @NotNull
    private Integer cantidad;
}
