package com.idat.gestionjalsuri.model.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

@Entity
@Table(name="insumos")
public class Insumo extends Generico{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	private Integer stock ;
	
	private Double precio;
	
	private LocalDate fechaIngreso;
	
	private LocalDate fechaVencimiento;
	
    @ManyToOne()
    @JoinColumn(name = "id_categoria")
	private Categoria categoria;
	
    @ManyToOne()
    @JoinColumn(name = "id_unidadmedida")
	private UnidadMedida unidadMedida;

	@ManyToOne()
	@JoinColumn(name = "id_proveedor")
	private Proveedor proveedor;


}
