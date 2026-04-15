package model;

/**
 * Representa un artículo de merchandising (figuras, ropa, accesorios)
 * asociado a franquicias de videojuegos.
 *
 * <p><b>Herencia:</b> Extiende {@link Producto} de forma independiente a
 * {@link Juego}, demostrando que la jerarquía puede ramificarse según el
 * dominio del problema.</p>
 *
 * @author Portafolio POO - CENFOTEC
 * @version 1.0
 */
public class Merchandising extends Producto {

    private String tipo;        // Figura, Ropa, Accesorio, etc.
    private String franquicia;  // Franquicia de origen (ej: "The Legend of Zelda")
    private String material;    // Material principal (PVC, tela, metal, etc.)

    /**
     * Constructor de Merchandising.
     *
     * @param id         Identificador único.
     * @param nombre     Nombre del artículo.
     * @param precio     Precio del artículo.
     * @param stock      Unidades disponibles.
     * @param tipo       Tipo de merchandising.
     * @param franquicia Franquicia de origen.
     * @param material   Material principal del artículo.
     */
    public Merchandising(String id, String nombre, double precio, int stock,
                         String tipo, String franquicia, String material) {
        super(id, nombre, precio, stock);
        this.tipo = tipo;
        this.franquicia = franquicia;
        this.material = material;
    }

    // ── Sobrescritura de getDetalles() ───────────────────────────────────────

    /**
     * {@inheritDoc}
     *
     * <p>Proporciona detalles específicos del artículo de merchandising.</p>
     */
    @Override
    public String getDetalles() {
        return "Tipo: " + tipo + " | Franquicia: " + franquicia
                + " | Material: " + material;
    }

    /**
     * Compara dos artículos de merchandising. Dos artículos son iguales si
     * tienen el mismo id Y pertenecen a la misma franquicia.
     *
     * @param obj Objeto a comparar.
     * @return {@code true} si representan el mismo artículo de la misma franquicia.
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof Merchandising)) return false;
        Merchandising otro = (Merchandising) obj;
        return this.franquicia.equalsIgnoreCase(otro.franquicia);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + franquicia.hashCode();
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    /** @return el tipo de merchandising. */
    public String getTipo() { return tipo; }

    /** @return la franquicia de origen. */
    public String getFranquicia() { return franquicia; }

    /** @return el material principal. */
    public String getMaterial() { return material; }
}
