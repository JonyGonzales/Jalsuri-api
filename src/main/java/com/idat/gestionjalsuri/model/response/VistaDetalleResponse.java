package com.idat.gestionjalsuri.model.response;

import com.idat.gestionjalsuri.model.entity.MovAlmacen;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VistaDetalleResponse {

    private Integer id;
	private Integer cantidad;
	private String observaciones;
	private MovAlmacen movAlmacen;
	private String producto;
	private String estado;

}
