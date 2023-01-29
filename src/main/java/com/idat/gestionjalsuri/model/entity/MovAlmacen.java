package com.idat.gestionjalsuri.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="movimiento_almacen")
public class MovAlmacen{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	private LocalDate fechaMovimiento;
	private LocalDateTime fechaCreado;
	private String observaciones;

	@NotBlank
	private String numeroDocumento;

	@ManyToOne()
	@JoinColumn(name = "id_tipo_movimiento")
	private TipoMovimiento tipoMovimiento;

	@ManyToOne()
	@JoinColumn(name = "id_tipo_documento")
	private TipoDocumento tipoDocumento;

	@ManyToOne()
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@ManyToOne()
	@JoinColumn(name = "id_proveedor")
	private Proveedor proveedor;

	@ManyToOne()
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
	private String estado;

}
