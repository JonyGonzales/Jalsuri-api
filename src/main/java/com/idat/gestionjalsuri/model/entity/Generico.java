package com.idat.gestionjalsuri.model.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class Generico {

	@Column (name="estado",nullable = false, length = 11)
	private String estado;
	
	
}
