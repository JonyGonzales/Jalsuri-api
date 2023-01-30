package com.idat.gestionjalsuri.service.impl;

import com.idat.gestionjalsuri.exception.ExceptionService;
import com.idat.gestionjalsuri.model.entity.Categoria;
import com.idat.gestionjalsuri.model.entity.Producto;
import com.idat.gestionjalsuri.model.entity.Proveedor;
import com.idat.gestionjalsuri.model.entity.UnidadMedida;
import com.idat.gestionjalsuri.model.request.GenericoRequest;
import com.idat.gestionjalsuri.model.request.ProductoRequest;
import com.idat.gestionjalsuri.model.request.ProductoStockRequest;
import com.idat.gestionjalsuri.model.response.GenericResponse;
import com.idat.gestionjalsuri.repository.CategoriaRepository;
import com.idat.gestionjalsuri.repository.ProductoRepository;
import com.idat.gestionjalsuri.repository.ProveedorRepository;
import com.idat.gestionjalsuri.repository.UnidadMedidaRepository;
import com.idat.gestionjalsuri.service.IProductoService;
import com.idat.gestionjalsuri.util.Constante;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductoServiceImpl implements IProductoService {

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Producto insertar(ProductoRequest productoRequest) {

        Producto producto = new Producto();
        if (this.productoRepository.existsByNombre(productoRequest.getNombre())) {
            throw new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, Constante.MENSAGE_NOMBRE_EXISTE, HttpStatus.BAD_REQUEST);
        }
        Optional<Categoria> categoria = Optional.ofNullable(this.categoriaRepository.findById(productoRequest.getIdCategoria())
                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, "Id de categoria no encontrado...", HttpStatus.NOT_FOUND)));
        Optional<Proveedor> proveedor = Optional.ofNullable(this.proveedorRepository.findById(productoRequest.getIdProveedor())
                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, "Id de proveedor no encontrado...", HttpStatus.NOT_FOUND)));
        Optional<UnidadMedida> unidadMedida = Optional.ofNullable(this.unidadMedidaRepository.findById(productoRequest.getIdUnidadMedida())
                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, "Id de unidad medida no encontrado...", HttpStatus.NOT_FOUND)));

        producto.setNombre(productoRequest.getNombre());
        producto.setStock(productoRequest.getStock());
        producto.setPrecio(productoRequest.getPrecio());
        producto.setFechaIngreso(LocalDate.now());
        producto.setFechaVencimiento(LocalDate.now().plusMonths(2));
        producto.setEstado(Constante.ESTADO_ACTIVO);
        producto.setCategoria(categoria.get());
        producto.setUnidadMedida(unidadMedida.get());
        producto.setProveedor(proveedor.get());
        return this.productoRepository.save(producto);

    }


    @Override
    public GenericResponse actualizarStok(ProductoStockRequest request) {
        Producto producto = this.buscarXid(request.getId());
        producto.setStock(request.getStock() + producto.getStock());
        producto.setFechaIngreso(LocalDate.now());
        producto.setFechaVencimiento(LocalDate.now().plusMonths(2));
        this.productoRepository.save(producto);
        return GenericResponse.builder().cod("0").mensage("Stock actualizado").build();
    }

    @Override
    public List<Producto> listar() {
        // DataResponse response = new DataResponse();
        List<Producto> productos = this.productoRepository.findAll().stream().filter(p -> p.getEstado().equalsIgnoreCase(Constante.ESTADO_ACTIVO)).collect(Collectors.toList());
        if (productos.isEmpty()) {
            throw new ExceptionService("-2", "Lista vacia", HttpStatus.NOT_FOUND);
        }
        // response.setProductos(productos);
        // response.setTotalProducto(productos.stream().mapToInt(p -> p.getStock()).sum());
        // response.setPrecioTotalProducto(productos.stream().mapToDouble(p -> p.getPrecio() * p.getStock()).sum());
        return productos;
    }

    @Override
    public Producto buscarXid(Long id) throws ExceptionService {

        Optional<Producto> producto = Optional.ofNullable(this.productoRepository.findById(id)
                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, "Id de producto no encontrado...", HttpStatus.NOT_FOUND)));
        log.info("Esdo producto: {}", producto.get().getEstado());
        if (!producto.get().getEstado().equalsIgnoreCase(Constante.ESTADO_ACTIVO)) {
            throw new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, "Estado de producto incorrecto", HttpStatus.NOT_FOUND);
        }
        return producto.get();
    }

    @Override
    public void eliminar(Long id) throws ExceptionService {
        this.buscarXid(id);
        this.productoRepository.deleteById(id);


    }


    @Override
    public Producto cambiaEstado(Long id, GenericoRequest t) {
        Producto producto = new Producto();
        producto.setEstado(t.getEstado());
        return this.productoRepository.save(producto);
    }

}
