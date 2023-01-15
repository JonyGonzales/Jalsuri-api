package com.idat.gestionjalsuri.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.idat.gestionjalsuri.exception.ExceptionService;
import com.idat.gestionjalsuri.model.entity.Categoria;
import com.idat.gestionjalsuri.model.entity.Insumo;
import com.idat.gestionjalsuri.model.entity.Proveedor;
import com.idat.gestionjalsuri.model.entity.UnidadMedida;
import com.idat.gestionjalsuri.model.request.InsumoRequest;
import com.idat.gestionjalsuri.repository.CategoriaRepository;
import com.idat.gestionjalsuri.repository.InsumoRepository;
import com.idat.gestionjalsuri.repository.ProveedorRepository;
import com.idat.gestionjalsuri.repository.UnidadMedidaRepository;
import com.idat.gestionjalsuri.service.IInsumoService;
import com.idat.gestionjalsuri.util.Constante;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InsumoServiceImpl implements IInsumoService {

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private InsumoRepository insumoRepository;

    @Override
    public Insumo insertar(InsumoRequest InsumoRequest) {
        Insumo insumo = new Insumo();
        if (this.insumoRepository.existsByNombre(InsumoRequest.getNombre())) {
            throw new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, Constante.MENSAGE_NOMBRE_EXISTE, HttpStatus.BAD_REQUEST);
        }
        Optional<Categoria> categoria = Optional.ofNullable(this.categoriaRepository.findById(InsumoRequest.getIdCategoria())
                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, "Id de categoria no encontrado...", HttpStatus.NOT_FOUND)));
        Optional<Proveedor> proveedor = Optional.ofNullable(this.proveedorRepository.findById(InsumoRequest.getIdProveedor())
                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, "Id de proveedor no encontrado...", HttpStatus.NOT_FOUND)));
        Optional<UnidadMedida> unidadMedida = Optional.ofNullable(this.unidadMedidaRepository.findById(InsumoRequest.getIdUnidadMedida())
                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, "Id de unidad medida no encontrado...", HttpStatus.NOT_FOUND)));

        insumo.setNombre(InsumoRequest.getNombre());
        insumo.setStock(InsumoRequest.getStock());
        insumo.setPrecio(InsumoRequest.getPrecio());
        insumo.setFechaIngreso(LocalDate.now());
        insumo.setFechaVencimiento(LocalDate.now().plusMonths(2));
        insumo.setEstado(Constante.ESTADO_ACTIVO);
        insumo.setCategoria(categoria.get());
        insumo.setUnidadMedida(unidadMedida.get());
        insumo.setProveedor(proveedor.get());
        return this.insumoRepository.save(insumo);
    }

    @Override
    public Insumo actualizar(InsumoRequest InsumoRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Insumo> listar() {
        List<Insumo>listinsumo= this.insumoRepository.findAll()
        .stream()
        .filter(c->c.getEstado().equalsIgnoreCase(Constante.ESTADO_ACTIVO))
        .collect(Collectors.toList());
if (listinsumo.isEmpty()){
    throw new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO,Constante.LISTA_VACIA,HttpStatus.NOT_FOUND);
}
// product.sort(Comparator.comparing(Proveedor::getId)
// 		.reversed());
return listinsumo;
    }

    @Override
    public Insumo buscarXid(Long id) throws ExceptionService {
        Optional<Insumo> insumo = Optional.ofNullable(this.insumoRepository.findById(id)
                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, "Id de categoria no encontrado...", HttpStatus.NOT_FOUND)));
        log.info("Esdo insumo: {}", insumo.get().getEstado());
        if (!insumo.get().getEstado().equalsIgnoreCase(Constante.ESTADO_ACTIVO)) {
            throw new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, "Estado de insumo incorrecto", HttpStatus.NOT_FOUND);
        }
        return insumo.get();
    }

    @Override
    public void eliminar(Long id) throws ExceptionService {
        Optional<Insumo> insumo = this.insumoRepository.findById(id);
        if (insumo.isPresent()) {
            this.insumoRepository.deleteById(id);
        } else {
            throw new ExceptionService("-1", "No se Encontro el Id", HttpStatus.NO_CONTENT);
        }
    }
    
}
