package model;

import java.time.LocalDate;

/**
 * Representa una edición limitada de un videojuego, con contenido exclusivo
 * y numeración de colección.
 *
 * <p><b>Herencia multinivel:</b> Extiende {@link Juego}, que a su vez extiende
 * {@link Producto}. Esto demuestra una cadena de herencia de tres niveles.</p>
 *
 * <p><b>Sobrescritura:</b> Redefine {@link #getDetalles()} y {@link #equals(Object)}
 * para incluir atributos propios de las ediciones limitadas.</p>
 *
 * <p><b>Identidad de objetos (equals):</b> Dos ediciones limitadas se consideran
 * iguales si comparten el mismo id Y el mismo número de edición.</p>
 *
 * @author Portafolio POO - CENFOTEC
 * @version 1.0
 */
public class EdicionLimitada extends Juego {

    private int numeracion;          // Número de la copia (ej: 042 de 500)
    private String contenidoExtra;   // Descripción del contenido adicional
    private boolean esExclusiva;     // Si es exclusiva de la tienda
    private double porcentajePremium; // % adicional al precio base

    /**
     * Constructor de EdicionLimitada.
     *
     * @param id                Identificador único.
     * @param nombre            Nombre de la edición.
     * @param precio            Precio base (antes del premium).
     * @param stock             Unidades disponibles.
     * @param plataforma        Plataforma destino.
     * @param genero            Género del juego.
     * @param fechaLanzamiento  Fecha de lanzamiento.
     * @param numeracion        Número de esta copia en la colección.
     * @param contenidoExtra    Descripción del contenido exclusivo incluido.
     * @param esExclusiva       Indica si es exclusiva de esta tienda.
     * @param porcentajePremium Porcentaje adicional al precio base (0.0 - 1.0).
     */
    public EdicionLimitada(String id, String nombre, double precio, int stock,
                           String plataforma, String genero, LocalDate fechaLanzamiento,
                           int numeracion, String contenidoExtra,
                           boolean esExclusiva, double porcentajePremium) {
        super(id, nombre, precio, stock, plataforma, genero, fechaLanzamiento);
        this.numeracion = numeracion;
        this.contenidoExtra = contenidoExtra;
        this.esExclusiva = esExclusiva;
        this.porcentajePremium = porcentajePremium;
    }

    // ── Sobrescritura de getDetalles() ───────────────────────────────────────

    /**
     * {@inheritDoc}
     *
     * <p>Agrega información de la numeración y el contenido extra al detalle
     * base heredado de {@link Juego}.</p>
     */
    @Override
    public String getDetalles() {
        return super.getDetalles()
                + " | Edición #" + numeracion
                + " | Extras: " + contenidoExtra
                + (esExclusiva ? " | ★ EXCLUSIVA TIENDA" : "");
    }

    /**
     * Calcula el precio final de la edición limitada aplicando el porcentaje
     * premium sobre el precio base.
     *
     * @return Precio final con el recargo de edición limitada.
     */
    public double calcularPrecio() {
        return getPrecio() * (1 + porcentajePremium);
    }

    // ── Identidad de objetos (equals personalizado) ──────────────────────────

    /**
     * Dos ediciones limitadas son iguales si tienen el mismo id
     * Y el mismo número de edición.
     *
     * @param obj Objeto a comparar.
     * @return {@code true} si representan la misma copia de la misma edición.
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof EdicionLimitada)) return false;
        EdicionLimitada otra = (EdicionLimitada) obj;
        return this.numeracion == otra.numeracion;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + numeracion;
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    /** @return el número de esta copia en la colección. */
    public int getNumeracion() { return numeracion; }

    /** @return la descripción del contenido extra incluido. */
    public String getContenidoExtra() { return contenidoExtra; }

    /** @return {@code true} si es exclusiva de la tienda. */
    public boolean isEsExclusiva() { return esExclusiva; }

    /** @return el porcentaje premium aplicado al precio base. */
    public double getPorcentajePremium() { return porcentajePremium; }
}
