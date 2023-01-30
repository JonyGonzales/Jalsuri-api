package com.idat.gestionjalsuri.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaRequest{

	@NotNull
	@NotBlank
	private String nombre;
	
	
}
