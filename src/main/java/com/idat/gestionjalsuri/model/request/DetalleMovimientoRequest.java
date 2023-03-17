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
	@NotBlank
    private Long idMovAlmacen;
    @NotNull
	@NotBlank
    private Long idProducto;
    @NotNull
	@NotBlank
    private Integer cantidad;
}
