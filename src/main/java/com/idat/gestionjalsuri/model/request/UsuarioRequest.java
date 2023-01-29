package com.idat.gestionjalsuri.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest extends GenericoRequest{

	@NotNull
	@NotBlank
	private String nombre;
	
	@NotNull
	@NotBlank
	@Email
	private String email;
	
	@NotNull
	@NotBlank
	private String password;
	
	@NotNull
	@NotBlank
	private String role;
	
	@NotNull
	@NotBlank
	private String area;

	
}
