package com.idat.gestionjalsuri.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="tipo_movimiento")
public class TipoMovimiento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;

	private String tipo;

	@Override
	public String toString() {
		return "TipoDocumento{" +
				"id=" + id +
				", nombre='" + nombre + '\'' +
				'}';
	}
}
