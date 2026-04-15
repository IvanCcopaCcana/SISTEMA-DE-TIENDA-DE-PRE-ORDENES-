package model;

/**
 * Clase abstracta que representa un producto genérico en la tienda de pre-órdenes.
 *
 * <p><b>Abstracción:</b> Esta clase abstrae los atributos y comportamientos comunes
 * de todos los productos, ocultando los detalles específicos de cada tipo.</p>
 *
 * <p><b>Encapsulamiento:</b> Todos los atributos son privados y se acceden
 * únicamente mediante getters y setters.</p>
 *
 * <p><b>Modularidad:</b> Esta clase pertenece al paquete {@code model}, que agrupa
 * todas las entidades del dominio de negocio.</p>
 *
 * @author Portafolio POO - CENFOTEC
 * @version 1.0
 */
public abstract class Producto {

    // ── Encapsulamiento: atributos privados ──────────────────────────────────
    private String id;
    private String nombre;
    private double precio;
    private int stockDisponible;

    /**
     * Constructor de Producto.
     *
     * @param id              Identificador único del producto.
     * @param nombre          Nombre descriptivo del producto.
     * @param precio          Precio base del producto.
     * @param stockDisponible Cantidad de unidades disponibles.
     */
    public Producto(String id, String nombre, double precio, int stockDisponible) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stockDisponible = stockDisponible;
    }

    // ── Método abstracto (Abstracción) ───────────────────────────────────────

    /**
     * Retorna una descripción detallada del producto.
     * Cada subclase debe implementar este método según su tipo.
     *
     * @return String con los detalles específicos del producto.
     */
    public abstract String getDetalles();

    // ── Sobrescritura de toString (Polimorfismo) ─────────────────────────────

    /**
     * Representación en texto del producto.
     * Utiliza {@link #getDetalles()} que es resuelto en tiempo de ejecución
     * según el tipo real del objeto (polimorfismo).
     *
     * @return String con información del producto.
     */
    @Override
    public String toString() {
        return "[" + id + "] " + nombre + " - $" + precio + " | Stock: " + stockDisponible
                + "\n  " + getDetalles();
    }

    /**
     * Compara dos productos por su identificador único.
     *
     * @param obj Objeto a comparar.
     * @return {@code true} si ambos productos tienen el mismo id.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Producto)) return false;
        Producto otro = (Producto) obj;
        return this.id.equals(otro.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    // ── Getters y Setters (Encapsulamiento) ──────────────────────────────────

    /** @return el identificador único del producto. */
    public String getId() { return id; }

    /** @return el nombre del producto. */
    public String getNombre() { return nombre; }

    /** @param nombre el nuevo nombre del producto. */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** @return el precio base del producto. */
    public double getPrecio() { return precio; }

    /** @param precio el nuevo precio. */
    public void setPrecio(double precio) { this.precio = precio; }

    /** @return la cantidad de unidades disponibles en stock. */
    public int getStockDisponible() { return stockDisponible; }

    /** @param stockDisponible la nueva cantidad de stock. */
    public void setStockDisponible(int stockDisponible) { this.stockDisponible = stockDisponible; }
}
