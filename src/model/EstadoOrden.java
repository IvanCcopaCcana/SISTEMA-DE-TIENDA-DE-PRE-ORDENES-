package model;

/**
 * Enumeración que representa los posibles estados de una pre-orden.
 *
 * <p><b>Modularidad:</b> Al centralizar los estados en un enum, se evita el uso
 * de cadenas de texto libres (magic strings) dispersas en el código, facilitando
 * el mantenimiento y evitando errores.</p>
 *
 * @author Portafolio POO - CENFOTEC
 * @version 1.0
 */
public enum EstadoOrden {

    /** La orden fue creada pero aún no ha sido confirmada. */
    PENDIENTE,

    /** La orden fue confirmada y el depósito fue recibido. */
    CONFIRMADA,

    /** La orden fue cancelada por el cliente o la tienda. */
    CANCELADA,

    /** El producto fue entregado al cliente. */
    COMPLETADA
}
