package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a un cliente registrado en la tienda de pre-órdenes.
 *
 * <p><b>Asociación:</b> Un Cliente tiene una lista de {@link PreOrden}. Esta
 * relación es de asociación porque el cliente puede existir sin pre-órdenes,
 * y las pre-órdenes pueden consultarse de forma independiente.</p>
 *
 * <p><b>Encapsulamiento:</b> El historial de pre-órdenes es privado y no se
 * expone directamente; se accede mediante métodos controlados.</p>
 *
 * @author Portafolio POO - CENFOTEC
 * @version 1.0
 */
public class Cliente {

    private String id;
    private String nombre;
    private String email;
    private String telefono;

    // ── Asociación: un cliente tiene pre-órdenes ─────────────────────────────
    private List<PreOrden> historialPreOrdenes;

    /**
     * Constructor de Cliente.
     *
     * @param id       Identificador único del cliente.
     * @param nombre   Nombre completo.
     * @param email    Correo electrónico.
     * @param telefono Número de teléfono de contacto.
     */
    public Cliente(String id, String nombre, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.historialPreOrdenes = new ArrayList<>();
    }

    /**
     * Agrega una pre-orden al historial del cliente.
     *
     * @param orden La pre-orden a registrar.
     */
    public void agregarPreOrden(PreOrden orden) {
        if (orden != null) {
            historialPreOrdenes.add(orden);
        }
    }

    /**
     * Cancela una pre-orden del cliente identificada por su ID.
     *
     * @param idOrden Identificador de la orden a cancelar.
     * @return {@code true} si la orden fue encontrada y cancelada.
     */
    public boolean cancelarPreOrden(String idOrden) {
        for (PreOrden orden : historialPreOrdenes) {
            if (orden.getIdOrden().equals(idOrden)) {
                orden.cancelar();
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna una copia no modificable del historial de pre-órdenes.
     *
     * @return Lista de pre-órdenes del cliente.
     */
    public List<PreOrden> getHistorialPreOrdenes() {
        return new ArrayList<>(historialPreOrdenes);
    }

    /**
     * Identidad: dos clientes son iguales si tienen el mismo id.
     *
     * @param obj Objeto a comparar.
     * @return {@code true} si comparten el mismo identificador.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Cliente)) return false;
        Cliente otro = (Cliente) obj;
        return this.id.equals(otro.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Cliente[" + id + "] " + nombre + " <" + email + ">"
                + " | Órdenes: " + historialPreOrdenes.size();
    }

    // ── Getters y Setters ────────────────────────────────────────────────────

    /** @return el identificador único del cliente. */
    public String getId() { return id; }

    /** @return el nombre del cliente. */
    public String getNombre() { return nombre; }

    /** @param nombre el nuevo nombre del cliente. */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** @return el correo electrónico del cliente. */
    public String getEmail() { return email; }

    /** @param email el nuevo correo electrónico. */
    public void setEmail(String email) { this.email = email; }

    /** @return el teléfono del cliente. */
    public String getTelefono() { return telefono; }

    /** @param telefono el nuevo teléfono. */
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
