package model;

/**
 * Interfaz que define el contrato para productos que pueden ser reservados.
 *
 * <p><b>Interfaz:</b> Define un conjunto de operaciones que cualquier producto
 * reservable debe implementar, sin imponer una jerarquía de herencia específica.
 * Esto permite aplicar el principio de "programar hacia interfaces, no hacia
 * implementaciones".</p>
 *
 * <p><b>Polimorfismo:</b> Permite tratar objetos de distintos tipos (Juego,
 * EdicionLimitada) de forma uniforme cuando se trabaja con reservas.</p>
 *
 * @author Portafolio POO - CENFOTEC
 * @version 1.0
 */
public interface Reservable {

    /**
     * Realiza una reserva del producto para un cliente dado.
     *
     * @param cliente El cliente que realiza la reserva.
     * @return {@code true} si la reserva fue exitosa.
     */
    boolean reservar(Cliente cliente);

    /**
     * Cancela la reserva activa del producto.
     *
     * @return {@code true} si la cancelación fue exitosa.
     */
    boolean cancelarReserva();

    /**
     * Retorna el estado actual de la reserva.
     *
     * @return String describiendo el estado ("DISPONIBLE", "RESERVADO", etc.)
     */
    String getEstadoReserva();
}
