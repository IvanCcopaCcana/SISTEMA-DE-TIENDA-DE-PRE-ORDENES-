package model;

import java.time.LocalDate;

/**
 * Representa un videojuego disponible para pre-orden en la tienda.
 *
 * <p><b>Herencia:</b> Extiende {@link Producto} heredando sus atributos y métodos,
 * y añade atributos propios del dominio de videojuegos.</p>
 *
 * <p><b>Interfaz:</b> Implementa {@link Reservable}, comprometiendo a esta clase
 * a definir la lógica de reserva específica para juegos.</p>
 *
 * <p><b>Sobrescritura de métodos:</b> Redefine {@code getDetalles()} y
 * {@code toString()} heredados de {@link Producto}.</p>
 *
 * @author Portafolio POO - CENFOTEC
 * @version 1.0
 */
public class Juego extends Producto implements Reservable {

    private String plataforma;
    private String genero;
    private LocalDate fechaLanzamiento;

    // ── Estado de reserva (privado, encapsulado) ─────────────────────────────
    private boolean reservado;
    private Cliente clienteReservador;

    /**
     * Constructor de Juego.
     *
     * @param id               Identificador único.
     * @param nombre           Nombre del juego.
     * @param precio           Precio base.
     * @param stock            Unidades disponibles.
     * @param plataforma       Plataforma destino (PS5, Xbox, PC, etc.)
     * @param genero           Género del juego (RPG, Acción, etc.)
     * @param fechaLanzamiento Fecha de lanzamiento oficial.
     */
    public Juego(String id, String nombre, double precio, int stock,
                 String plataforma, String genero, LocalDate fechaLanzamiento) {
        super(id, nombre, precio, stock); // Llama al constructor de Producto
        this.plataforma = plataforma;
        this.genero = genero;
        this.fechaLanzamiento = fechaLanzamiento;
        this.reservado = false;
        this.clienteReservador = null;
    }

    // ── Sobrescritura (Override) de método abstracto ─────────────────────────

    /**
     * {@inheritDoc}
     *
     * <p>Proporciona los detalles específicos de un videojuego: plataforma,
     * género y fecha de lanzamiento.</p>
     */
    @Override
    public String getDetalles() {
        return "Plataforma: " + plataforma + " | Género: " + genero
                + " | Lanzamiento: " + fechaLanzamiento;
    }

    // ── Implementación de Reservable ─────────────────────────────────────────

    /**
     * {@inheritDoc}
     *
     * <p>Verifica que haya stock disponible y que el juego no esté ya reservado
     * antes de proceder.</p>
     *
     * @param cliente El cliente que desea reservar el juego.
     */
    @Override
    public boolean reservar(Cliente cliente) {
        if (!reservado && getStockDisponible() > 0) {
            this.reservado = true;
            this.clienteReservador = cliente;
            setStockDisponible(getStockDisponible() - 1);
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean cancelarReserva() {
        if (reservado) {
            this.reservado = false;
            this.clienteReservador = null;
            setStockDisponible(getStockDisponible() + 1);
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEstadoReserva() {
        return reservado ? "RESERVADO" : "DISPONIBLE";
    }

    // ── Sobrecarga de métodos ────────────────────────────────────────────────

    /**
     * Verifica si el juego está disponible para una plataforma específica.
     * Ejemplo de <b>sobrecarga</b>: mismo nombre, distintos parámetros.
     *
     * @param plataformaConsulta La plataforma a verificar.
     * @return {@code true} si el juego es de esa plataforma.
     */
    public boolean esCompatible(String plataformaConsulta) {
        return this.plataforma.equalsIgnoreCase(plataformaConsulta);
    }

    /**
     * Sobrecarga: verifica si el juego es compatible con alguna de las
     * plataformas de una lista.
     *
     * @param plataformas Array de plataformas a verificar.
     * @return {@code true} si el juego es compatible con al menos una.
     */
    public boolean esCompatible(String[] plataformas) {
        for (String p : plataformas) {
            if (this.plataforma.equalsIgnoreCase(p)) return true;
        }
        return false;
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    /** @return la plataforma del juego. */
    public String getPlataforma() { return plataforma; }

    /** @return el género del juego. */
    public String getGenero() { return genero; }

    /** @return la fecha de lanzamiento. */
    public LocalDate getFechaLanzamiento() { return fechaLanzamiento; }

    /** @return {@code true} si el juego ya fue reservado. */
    public boolean isReservado() { return reservado; }

    /** @return el cliente que realizó la reserva, o {@code null} si no hay reserva. */
    public Cliente getClienteReservador() { return clienteReservador; }
}
