package controller;

import model.*;
import java.util.List;

/**
 * Controlador principal de la tienda. Actúa como intermediario entre la
 * capa de interfaz de usuario ({@code ui}) y la capa del modelo ({@code model}).
 *
 * <p><b>Arquitectura por capas (CL - Control Layer):</b> Esta clase pertenece
 * a la capa de control. Recibe solicitudes de la UI, las valida, delega
 * la lógica de negocio al modelo, y retorna resultados.</p>
 *
 * <p><b>Dependencia:</b> El controlador depende de {@link Tienda} y del modelo
 * para operar. No contiene lógica de negocio propia; la delega.</p>
 *
 * @author Portafolio POO - CENFOTEC
 * @version 1.0
 */
public class TiendaController {

    // ── Dependencia con la capa del modelo ───────────────────────────────────
    private final Tienda tienda;

    /**
     * Inicializa el controlador con una tienda existente.
     *
     * @param tienda La tienda a gestionar.
     */
    public TiendaController(Tienda tienda) {
        this.tienda = tienda;
    }

    // ── Gestión de productos ─────────────────────────────────────────────────

    /**
     * Agrega un producto al inventario de la tienda.
     *
     * @param producto El producto a agregar.
     * @return {@code true} si fue agregado exitosamente.
     */
    public boolean agregarProducto(Producto producto) {
        if (producto == null) {
            System.out.println("Error: producto no puede ser nulo.");
            return false;
        }
        return tienda.getInventario().agregarProducto(producto);
    }

    /**
     * Lista todos los productos disponibles (con stock > 0).
     *
     * @return Lista de productos disponibles.
     */
    public List<Producto> listarProductosDisponibles() {
        return tienda.getInventario().getProductosDisponibles();
    }

    /**
     * Busca productos por nombre (parcial).
     *
     * @param nombre Término de búsqueda.
     * @return Lista de coincidencias.
     */
    public List<Producto> buscarProducto(String nombre) {
        return tienda.getInventario().buscarPorNombre(nombre);
    }

    /**
     * Muestra el inventario completo en consola.
     */
    public void mostrarInventario() {
        tienda.getInventario().mostrarInventario();
    }

    // ── Gestión de clientes ──────────────────────────────────────────────────

    /**
     * Registra un nuevo cliente en la tienda.
     *
     * @param id       ID del cliente.
     * @param nombre   Nombre completo.
     * @param email    Correo electrónico.
     * @param telefono Teléfono de contacto.
     * @return El cliente creado, o {@code null} si ya existía.
     */
    public Cliente registrarCliente(String id, String nombre, String email, String telefono) {
        Cliente cliente = new Cliente(id, nombre, email, telefono);
        if (tienda.registrarCliente(cliente)) {
            return cliente;
        }
        return null;
    }

    // ── Gestión de pre-órdenes ───────────────────────────────────────────────

    /**
     * Crea una nueva pre-orden para un cliente y producto.
     *
     * @param cliente    El cliente que pre-ordena.
     * @param idProducto ID del producto.
     * @param deposito   Monto del depósito.
     * @param metodo     Método de pago.
     * @return La pre-orden creada, o {@code null} si falló.
     */
    public PreOrden crearPreOrden(Cliente cliente, String idProducto,
                                   double deposito, String metodo) {
        if (cliente == null) {
            System.out.println("Error: cliente no válido.");
            return null;
        }
        if (deposito <= 0) {
            System.out.println("Error: el depósito debe ser mayor a cero.");
            return null;
        }
        return tienda.procesarPreOrden(cliente, idProducto, deposito, metodo);
    }

    /**
     * Cancela una pre-orden de un cliente.
     *
     * @param cliente  El cliente dueño de la orden.
     * @param idOrden  ID de la orden a cancelar.
     * @return {@code true} si fue cancelada.
     */
    public boolean cancelarPreOrden(Cliente cliente, String idOrden) {
        return cliente.cancelarPreOrden(idOrden);
    }

    /**
     * Muestra el resumen general de la tienda.
     */
    public void mostrarResumen() {
        tienda.mostrarResumen();
    }

    /**
     * Muestra todas las pre-órdenes registradas.
     */
    public void mostrarPreOrdenes() {
        tienda.mostrarPreOrdenes();
    }

    /** @return la instancia de la tienda gestionada. */
    public Tienda getTienda() { return tienda; }
}
