package com.idat.gestionjalsuri.service.impl;

import com.idat.gestionjalsuri.exception.ExceptionService;
import com.idat.gestionjalsuri.model.entity.Categoria;
import com.idat.gestionjalsuri.model.entity.DetalleMovimiento;
import com.idat.gestionjalsuri.model.entity.MovAlmacen;
import com.idat.gestionjalsuri.model.entity.Producto;
import com.idat.gestionjalsuri.model.request.DetalleMovimientoRequest;
import com.idat.gestionjalsuri.repository.DetalleMovimientoRepository;
import com.idat.gestionjalsuri.repository.MovAlmacenRepository;
import com.idat.gestionjalsuri.repository.ProductoRepository;
import com.idat.gestionjalsuri.service.IDetalleMovimientoService;
import com.idat.gestionjalsuri.util.Constante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DetalleMovimientoServiceImpl implements IDetalleMovimientoService {
    private DetalleMovimientoRepository detalleMovimientoRepository;

    private ProductoRepository productoRepository;

    private MovAlmacenRepository movAlmacenRepository;

    public DetalleMovimientoServiceImpl(DetalleMovimientoRepository detalleMovimientoRepository, ProductoRepository productoRepository, MovAlmacenRepository movAlmacenRepository) {
        this.detalleMovimientoRepository = detalleMovimientoRepository;
        this.productoRepository = productoRepository;
        this.movAlmacenRepository = movAlmacenRepository;
    }

    @Override
    public void registrar(DetalleMovimientoRequest t) {
        Optional<Producto> producto = Optional.ofNullable(this.productoRepository.findById(t.getIdProducto())
                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, "Id de producto no encontrado...", HttpStatus.NOT_FOUND)));
        Optional<MovAlmacen> movAlmacen = Optional.ofNullable(this.movAlmacenRepository.findById(t.getIdMovAlmacen())
                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, "Id de movimiento almacen no encontrado...", HttpStatus.NOT_FOUND)));

        DetalleMovimiento detalleMovimiento = DetalleMovimiento.builder()
                .cantidadProducto(t.getCantidadProducto())
                .observacionMovimiento(t.getObservacionMovimiento())
                .producto(producto.get())
                .movAlmacen(movAlmacen.get())
                .estado(Constante.ESTADO_ACTIVO)
                .build();
        this.detalleMovimientoRepository.save(detalleMovimiento);
        return;
    }

    @Override
    public void modificar(Long id, DetalleMovimientoRequest t) {
        this.busca(id);
        Optional<Producto> producto = Optional.ofNullable(this.productoRepository.findById(t.getIdProducto())
                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, "Id de producto no encontrado...", HttpStatus.NOT_FOUND)));
        Optional<MovAlmacen> movAlmacen = Optional.ofNullable(this.movAlmacenRepository.findById(t.getIdMovAlmacen())
                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, "Id de movimiento almacen no encontrado...", HttpStatus.NOT_FOUND)));

        DetalleMovimiento detalleMovimiento = DetalleMovimiento.builder()
                .id(this.busca(id).getId())
                .cantidadProducto(producto.get().getStock())
                .observacionMovimiento(t.getObservacionMovimiento())
                .producto(producto.get())
                .movAlmacen(movAlmacen.get())
                .estado(Constante.ESTADO_ACTIVO)
                .build();
        this.detalleMovimientoRepository.save(detalleMovimiento);
        return;
    }

    @Override
    public boolean eliminar(Long id) {
        this.busca(id);
        this.detalleMovimientoRepository.deleteById(id);
        return true;
    }

    @Override
    public DetalleMovimiento busca(Long id) {
        return this.detalleMovimientoRepository.findById(id)
                .filter(d->d.getEstado().equalsIgnoreCase("A"))
                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO,
                        "Id de detalle movimiento  no encontrado...",
                        HttpStatus.NOT_FOUND));

    }

    @Override
    public List<DetalleMovimiento> listar() {
        List<DetalleMovimiento> movAlmacens = this.detalleMovimientoRepository.findAll()
                .stream()
                .filter(m -> m.getEstado().equalsIgnoreCase("A"))
                .sorted(Comparator.comparing(DetalleMovimiento::getId).reversed())
                .collect(Collectors.toList());
        if (movAlmacens.isEmpty()) {
            throw new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, Constante.LISTA_VACIA, HttpStatus.NOT_FOUND);

        }
        return movAlmacens;
    }

    @Override
    public Page<Categoria> listarPagina(Pageable page) {
        return null;
    }
}
