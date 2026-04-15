package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal que representa la tienda de pre-órdenes.
 *
 * <p><b>Composición con Inventario:</b> La tienda crea y posee su inventario.
 * Si la tienda cierra, el inventario desaparece con ella.</p>
 *
 * <p><b>Agregación con Cliente:</b> La tienda gestiona una lista de clientes,
 * pero los clientes pueden existir fuera del contexto de esta tienda.</p>
 *
 * <p><b>Asociación con PreOrden:</b> La tienda procesa pre-órdenes que
 * relacionan clientes con productos.</p>
 *
 * @author Portafolio POO - CENFOTEC
 * @version 1.0
 */
public class Tienda {

    private String nombre;
    private String direccion;

    // ── Composición: la Tienda crea y es dueña del Inventario ────────────────
    private final Inventario inventario;

    // ── Agregación: la Tienda gestiona Clientes ──────────────────────────────
    private List<Cliente> clientes;

    // ── Asociación: registro de pre-órdenes procesadas ───────────────────────
    private List<PreOrden> preOrdenes;

    private int contadorOrdenes;

    /**
     * Constructor de Tienda. Crea automáticamente el inventario (composición).
     *
     * @param nombre     Nombre de la tienda.
     * @param direccion  Dirección física.
     * @param capacidad  Capacidad del inventario.
     */
    public Tienda(String nombre, String direccion, int capacidad) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.inventario = new Inventario(capacidad); // Composición
        this.clientes = new ArrayList<>();
        this.preOrdenes = new ArrayList<>();
        this.contadorOrdenes = 1;
    }

    /**
     * Registra un nuevo cliente en la tienda.
     *
     * @param cliente El cliente a registrar.
     * @return {@code true} si fue registrado, {@code false} si ya existe.
     */
    public boolean registrarCliente(Cliente cliente) {
        if (clientes.contains(cliente)) {
            System.out.println("Cliente ya registrado: " + cliente.getNombre());
            return false;
        }
        clientes.add(cliente);
        System.out.println("✓ Cliente registrado: " + cliente.getNombre());
        return true;
    }

    /**
     * Procesa una nueva pre-orden para un cliente y producto específicos.
     *
     * @param cliente     El cliente que realiza la pre-orden.
     * @param idProducto  ID del producto a pre-ordenar.
     * @param deposito    Monto del depósito inicial.
     * @param metodoPago  Método de pago del depósito.
     * @return La {@link PreOrden} creada, o {@code null} si no fue posible.
     */
    public PreOrden procesarPreOrden(Cliente cliente, String idProducto,
                                     double deposito, String metodoPago) {
        // Dependencia: usa Inventario para buscar el producto
        Producto producto = inventario.buscarProducto(idProducto);

        if (producto == null) {
            System.out.println("✗ Producto no encontrado: " + idProducto);
            return null;
        }

        if (producto.getStockDisponible() <= 0) {
            System.out.println("✗ Sin stock disponible para: " + producto.getNombre());
            return null;
        }

        // Reserva el producto si es Reservable (polimorfismo con interfaz)
        if (producto instanceof Reservable) {
            ((Reservable) producto).reservar(cliente);
        }

        String idOrden = String.format("ORD-%04d", contadorOrdenes++);
        PreOrden orden = new PreOrden(idOrden, cliente, producto, deposito, metodoPago);
        orden.confirmar();

        preOrdenes.add(orden);
        cliente.agregarPreOrden(orden);

        System.out.println("✓ Pre-orden creada: " + idOrden
                + " | " + producto.getNombre()
                + " | Cliente: " + cliente.getNombre());
        return orden;
    }

    /**
     * Muestra el resumen completo de la tienda en consola.
     */
    public void mostrarResumen() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  " + nombre);
        System.out.println("║  " + direccion);
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  Clientes registrados : " + clientes.size());
        System.out.println("║  Pre-órdenes activas  : " + preOrdenes.size());
        System.out.println("╚══════════════════════════════════════╝");
    }

    /**
     * Muestra todas las pre-órdenes activas de la tienda.
     */
    public void mostrarPreOrdenes() {
        System.out.println("\n═══ PRE-ÓRDENES (" + preOrdenes.size() + ") ═══");
        for (PreOrden orden : preOrdenes) {
            System.out.println(orden);
            System.out.println("─────────────────────────────────────");
        }
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    /** @return el inventario de la tienda. */
    public Inventario getInventario() { return inventario; }

    /** @return lista de clientes registrados. */
    public List<Cliente> getClientes() { return new ArrayList<>(clientes); }

    /** @return lista de pre-órdenes. */
    public List<PreOrden> getPreOrdenes() { return new ArrayList<>(preOrdenes); }

    /** @return el nombre de la tienda. */
    public String getNombre() { return nombre; }
}
