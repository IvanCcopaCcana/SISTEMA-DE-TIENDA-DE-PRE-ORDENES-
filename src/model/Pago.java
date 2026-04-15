package model;

import java.time.LocalDateTime;

/**
 * Representa un pago asociado a una pre-orden.
 *
 * <p><b>Composición:</b> {@link Pago} es parte de {@link PreOrden}. Si la
 * pre-orden desaparece, el pago también. No tiene sentido un pago sin
 * su pre-orden correspondiente.</p>
 *
 * <p><b>Sobrecarga:</b> El método {@link #procesar()} tiene variantes
 * que aceptan distintos parámetros.</p>
 *
 * @author Portafolio POO - CENFOTEC
 * @version 1.0
 */
public class Pago {

    /** Posibles estados de un pago. */
    public enum EstadoPago { PENDIENTE, PROCESADO, RECHAZADO, REEMBOLSADO }

    private String idPago;
    private double monto;
    private LocalDateTime fecha;
    private String metodoPago;   // "TARJETA", "SINPE", "EFECTIVO"
    private EstadoPago estado;
    private String referencia;   // Código de referencia externo

    /**
     * Constructor de Pago.
     *
     * @param idPago     Identificador único del pago.
     * @param monto      Monto a cobrar.
     * @param metodoPago Método de pago utilizado.
     */
    public Pago(String idPago, double monto, String metodoPago) {
        this.idPago = idPago;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.fecha = LocalDateTime.now();
        this.estado = EstadoPago.PENDIENTE;
    }

    // ── Sobrecarga de procesar() ─────────────────────────────────────────────

    /**
     * Procesa el pago con configuración por defecto.
     *
     * @return {@code true} si el pago fue procesado exitosamente.
     */
    public boolean procesar() {
        if (estado == EstadoPago.PENDIENTE) {
            this.estado = EstadoPago.PROCESADO;
            this.referencia = "REF-" + System.currentTimeMillis();
            return true;
        }
        return false;
    }

    /**
     * Sobrecarga: procesa el pago con una referencia externa proporcionada.
     * Útil cuando el sistema externo de pagos ya generó el código.
     *
     * @param referenciaExterna Referencia generada por el procesador externo.
     * @return {@code true} si el pago fue procesado exitosamente.
     */
    public boolean procesar(String referenciaExterna) {
        if (procesar()) {
            this.referencia = referenciaExterna;
            return true;
        }
        return false;
    }

    /**
     * Realiza el reembolso del pago.
     *
     * @return {@code true} si el reembolso fue exitoso.
     */
    public boolean reembolsar() {
        if (estado == EstadoPago.PROCESADO) {
            this.estado = EstadoPago.REEMBOLSADO;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Pago[" + idPago + "] $" + monto + " vía " + metodoPago
                + " | Estado: " + estado + " | Ref: " + referencia;
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    /** @return el identificador del pago. */
    public String getIdPago() { return idPago; }

    /** @return el monto del pago. */
    public double getMonto() { return monto; }

    /** @return la fecha y hora del pago. */
    public LocalDateTime getFecha() { return fecha; }

    /** @return el método de pago. */
    public String getMetodoPago() { return metodoPago; }

    /** @return el estado actual del pago. */
    public EstadoPago getEstado() { return estado; }

    /** @return la referencia externa del pago. */
    public String getReferencia() { return referencia; }
}
