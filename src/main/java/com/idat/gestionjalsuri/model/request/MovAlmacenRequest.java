package com.idat.gestionjalsuri.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MovAlmacenRequest {

    
    private Long tipoMovimiento;
    
    private String obsevacionMovimiento;
   
    private Long tipoDocumento;
    
    private Long usuario;
}
