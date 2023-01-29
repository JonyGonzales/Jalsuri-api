package com.idat.gestionjalsuri.service.impl;

import com.idat.gestionjalsuri.exception.ExceptionService;
import com.idat.gestionjalsuri.model.entity.Cliente;
import com.idat.gestionjalsuri.model.entity.MovAlmacen;
import com.idat.gestionjalsuri.model.entity.Proveedor;
import com.idat.gestionjalsuri.model.entity.TipoDocumento;
import com.idat.gestionjalsuri.model.entity.TipoMovimiento;
import com.idat.gestionjalsuri.model.entity.Usuario;
import com.idat.gestionjalsuri.model.request.MovAlmacenRequest;
import com.idat.gestionjalsuri.repository.ClienteRepository;
import com.idat.gestionjalsuri.repository.MovAlmacenRepository;
import com.idat.gestionjalsuri.repository.ProveedorRepository;
import com.idat.gestionjalsuri.repository.TipoDocumentoRepository;
import com.idat.gestionjalsuri.repository.TipoMovimientoRepository;
import com.idat.gestionjalsuri.repository.UsuarioRepository;
import com.idat.gestionjalsuri.service.IMovAlmacenService;
import com.idat.gestionjalsuri.util.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovAlmacenServiceImpl implements IMovAlmacenService {

        @Autowired
        private MovAlmacenRepository movAlmacenRepository;
        @Autowired
        private UsuarioRepository usuarioRepository;
        @Autowired
        private TipoDocumentoRepository documentoRepository;
        @Autowired
        private TipoMovimientoRepository movimientoRepository;
        @Autowired
        private ProveedorRepository proveedorRepository;
        @Autowired
        private ClienteRepository clienteRepository;

        @Override
        public void registrar(MovAlmacenRequest t) {
                Optional<Usuario> usuario = Optional.ofNullable(this.usuarioRepository.findById(t.getUsuario())
                                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO,
                                                "Id de usuario no encontrado...", HttpStatus.NOT_FOUND)));

                Optional<TipoDocumento> tipoDocumento = Optional.of(this.documentoRepository
                                .findById(t.getTipoDocumento())
                                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO,
                                                "Id de tipo documento no encontrado...", HttpStatus.NOT_FOUND)));

                Optional<Proveedor> proveedor = null;
                Proveedor p = null;
                if (t.getProveedor() != null) {
                        proveedor = Optional.of(this.proveedorRepository.findById(t.getProveedor())
                                        .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO,
                                                        "Id del Proveedor no encontrado...", HttpStatus.NOT_FOUND)));
                        p = proveedor.get();
                }

                Optional<Cliente> cliente = null;
                Cliente c = null;
                if (t.getCliente() != null) {
                        cliente = Optional.of(this.clienteRepository.findById(t.getCliente())
                                        .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO,
                                                        "Id del Cliente no encontrado...", HttpStatus.NOT_FOUND)));
                        c = cliente.get();
                }

                Optional<TipoMovimiento> tipoMovimiento = Optional.of(this.movimientoRepository
                                .findById(t.getTipoMovimiento())
                                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO,
                                                "Tipo de movimiento no existe...", HttpStatus.NOT_FOUND)));

                MovAlmacen movAlmacen = MovAlmacen.builder()
                                .tipoMovimiento(tipoMovimiento.get())
                                .fechaMovimiento(LocalDate.now())
                                .fechaCreado(LocalDateTime.now())
                                .observaciones(t.getObservaciones())
                                .tipoDocumento(tipoDocumento.get())
                                .numeroDocumento(t.getNumeroDocumento())
                                .proveedor(p)
                                .cliente(c)
                                .usuario(usuario.get())
                                .estado(Constante.ESTADO_ACTIVO)
                                .build();
                this.movAlmacenRepository.save(movAlmacen);
                return;
        }

        @Override
        public void modificar(Long id, MovAlmacenRequest t) {
                this.busca(id);
                Optional<Usuario> usuario = Optional.ofNullable(this.usuarioRepository.findById(t.getUsuario())
                                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO,
                                                "Id de usuario no encontrado...", HttpStatus.NOT_FOUND)));

                Optional<TipoDocumento> tipoDocumento = Optional.of(this.documentoRepository
                                .findById(t.getTipoDocumento())
                                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO,
                                                "Id de tipo documento no encontrado...", HttpStatus.NOT_FOUND)));

                Optional<TipoMovimiento> tipoMovimiento = Optional.of(this.movimientoRepository
                                .findById(t.getTipoMovimiento())
                                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO,
                                                "Tipo de movimiento no existe...", HttpStatus.NOT_FOUND)));

                Optional<Proveedor> proveedor = null;
                Proveedor p = null;
                if (t.getProveedor() != null) {
                        proveedor = Optional.of(this.proveedorRepository.findById(t.getProveedor())
                                        .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO,
                                                        "Id del Proveedor no encontrado...", HttpStatus.NOT_FOUND)));
                        p = proveedor.get();
                }

                Optional<Cliente> cliente = null;
                Cliente c = null;
                if (t.getCliente() != null) {
                        cliente = Optional.of(this.clienteRepository.findById(t.getCliente())
                                        .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO,
                                                        "Id del Cliente no encontrado...", HttpStatus.NOT_FOUND)));
                        c = cliente.get();
                }

                MovAlmacen movAlmacen = MovAlmacen.builder()
                                .tipoMovimiento(tipoMovimiento.get())
                                .fechaMovimiento(LocalDate.now())
                                .observaciones(t.getObservaciones())
                                .tipoDocumento(tipoDocumento.get())
                                .numeroDocumento(t.getNumeroDocumento())
                                .proveedor(p)
                                .cliente(c)
                                .usuario(usuario.get())
                                .estado(Constante.ESTADO_ACTIVO)
                                .build();
                this.movAlmacenRepository.save(movAlmacen);
                return;
        }

        @Override
        public boolean eliminar(Long id) {
                this.busca(id);
                this.movAlmacenRepository.deleteById(id);
                return true;
        }

        @Override
        public MovAlmacen busca(Long id) {
                return this.movAlmacenRepository.findById(id)
                                .orElseThrow(() -> new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO,
                                                "Id de mov-almacen no encontrado...", HttpStatus.NOT_FOUND));

        }

        @Override
        public List<MovAlmacen> listar() {
                List<MovAlmacen> movAlmacens = this.movAlmacenRepository.findAll()
                                .stream()
                                .filter(m -> m.getEstado().equalsIgnoreCase(Constante.ESTADO_ACTIVO))
                                .sorted(Comparator.comparing(MovAlmacen::getId).reversed())
                                .collect(Collectors.toList());
                if (movAlmacens.isEmpty()) {
                        throw new ExceptionService(Constante.CODIGO_ID_NO_ENCONTRADO, Constante.LISTA_VACIA,
                                        HttpStatus.NOT_FOUND);

                }
                return movAlmacens;
        }

        @Override
        public Page<MovAlmacen> listarPagina(Pageable page) {
                return null;
        }

}
