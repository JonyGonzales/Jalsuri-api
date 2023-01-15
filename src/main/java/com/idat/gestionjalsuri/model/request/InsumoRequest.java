package com.idat.gestionjalsuri.model.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InsumoRequest {
    
    @NotBlank
	private String nombre;
	@Min(0)
	private Integer stock ;
	@Min(0)
	private Double precio;
	
	private Long idCategoria;

	private Long idUnidadMedida;
	private Long idProveedor;
}
