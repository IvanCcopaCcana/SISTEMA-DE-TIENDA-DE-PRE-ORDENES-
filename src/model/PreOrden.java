package model;

import java.time.LocalDateTime;

/**
 * Representa una pre-orden de un producto en la tienda.
 *
 * <p><b>Composición con Pago:</b> La pre-orden contiene un {@link Pago}. El pago
 * es creado y destruido junto con la pre-orden; no existe de forma independiente.</p>
 *
 * <p><b>Asociación con Cliente:</b> La pre-orden referencia al {@link Cliente}
 * que la realizó, pero el cliente puede existir sin esta orden.</p>
 *
 * <p><b>Dependencia con Producto:</b> La pre-orden depende de {@link Producto}
 * para su creación, pero no lo posee.</p>
 *
 * @author Portafolio POO - CENFOTEC
 * @version 1.0
 */
public class PreOrden {

    private String idOrden;
    private LocalDateTime fechaCreacion;
    private EstadoOrden estado;          // Dependencia con enum EstadoOrden

    // ── Composición: la PreOrden crea y posee su Pago ────────────────────────
    private final Pago pago;

    // ── Asociación: referencia al cliente y al producto ──────────────────────
    private final Cliente cliente;
    private final Producto producto;

    private double depositoPagado;

    /**
     * Constructor de PreOrden.
     * Crea automáticamente el objeto {@link Pago} asociado (composición).
     *
     * @param idOrden       Identificador único de la orden.
     * @param cliente       Cliente que realiza la pre-orden.
     * @param producto      Producto pre-ordenado.
     * @param deposito      Monto del depósito inicial.
     * @param metodoPago    Método de pago del depósito.
     */
    public PreOrden(String idOrden, Cliente cliente, Producto producto,
                    double deposito, String metodoPago) {
        this.idOrden = idOrden;
        this.cliente = cliente;
        this.producto = producto;
        this.fechaCreacion = LocalDateTime.now();
        this.estado = EstadoOrden.PENDIENTE;
        this.depositoPagado = deposito;

        // Composición: la PreOrden crea su propio Pago
        this.pago = new Pago("PAG-" + idOrden, deposito, metodoPago);
        this.pago.procesar();
    }

    /**
     * Calcula el total pendiente a pagar al retirar el producto.
     * Descuenta el depósito ya abonado.
     *
     * @return Monto total del producto menos el depósito pagado.
     */
    public double calcularTotalPendiente() {
        double precioFinal = producto.getPrecio();

        // Polimorfismo: si es EdicionLimitada, usa su precio con premium
        if (producto instanceof EdicionLimitada) {
            precioFinal = ((EdicionLimitada) producto).calcularPrecio();
        }
        return Math.max(0, precioFinal - depositoPagado);
    }

    /**
     * Confirma la pre-orden, cambiando su estado a CONFIRMADA.
     *
     * @return {@code true} si la confirmación fue exitosa.
     */
    public boolean confirmar() {
        if (estado == EstadoOrden.PENDIENTE) {
            this.estado = EstadoOrden.CONFIRMADA;
            return true;
        }
        return false;
    }

    /**
     * Cancela la pre-orden y gestiona el reembolso del depósito.
     *
     * @return {@code true} si la cancelación fue exitosa.
     */
    public boolean cancelar() {
        if (estado != EstadoOrden.CANCELADA && estado != EstadoOrden.COMPLETADA) {
            this.estado = EstadoOrden.CANCELADA;
            pago.reembolsar();

            // Devuelve el stock al producto
            if (producto instanceof Juego) {
                ((Juego) producto).cancelarReserva();
            }
            return true;
        }
        return false;
    }

    /**
     * Marca la orden como completada (producto entregado).
     *
     * @return {@code true} si se marcó como completada correctamente.
     */
    public boolean completar() {
        if (estado == EstadoOrden.CONFIRMADA) {
            this.estado = EstadoOrden.COMPLETADA;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "PreOrden[" + idOrden + "]"
                + "\n  Cliente : " + cliente.getNombre()
                + "\n  Producto: " + producto.getNombre()
                + "\n  Estado  : " + estado
                + "\n  Depósito: $" + depositoPagado
                + "\n  Pendiente: $" + calcularTotalPendiente()
                + "\n  " + pago;
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    /** @return el identificador de la orden. */
    public String getIdOrden() { return idOrden; }

    /** @return la fecha y hora de creación. */
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }

    /** @return el estado actual de la orden. */
    public EstadoOrden getEstado() { return estado; }

    /** @return el pago asociado (composición). */
    public Pago getPago() { return pago; }

    /** @return el cliente asociado. */
    public Cliente getCliente() { return cliente; }

    /** @return el producto pre-ordenado. */
    public Producto getProducto() { return producto; }

    /** @return el depósito ya pagado. */
    public double getDepositoPagado() { return depositoPagado; }
}
