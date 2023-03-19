package com.idat.gestionjalsuri.model.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VistaDetalleResponse {

    private Integer id;
	private Integer cantidad;
	private String observaciones;
	private Long id_mov_almacen;
	private String producto;
	private String estado;

}
