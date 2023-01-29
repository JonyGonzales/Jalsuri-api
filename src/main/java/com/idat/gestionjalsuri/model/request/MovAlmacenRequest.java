package com.idat.gestionjalsuri.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MovAlmacenRequest {

    
    private Long tipoMovimiento;

    private String numeroMovimiento;
    
    private String observaciones;
   
    private Long tipoDocumento;

    private Long proveedor;

    private Long cliente;

    
    private Long usuario;
}
