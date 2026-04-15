package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gestiona el catálogo de productos disponibles en la tienda.
 *
 * <p><b>Agregación con Producto:</b> El inventario contiene productos, pero
 * estos pueden existir de forma independiente. Si el inventario se elimina,
 * los productos no necesariamente desaparecen.</p>
 *
 * <p><b>Modularidad:</b> Encapsula toda la lógica de gestión de productos,
 * permitiendo que otras clases (como {@link Tienda}) la usen sin conocer
 * los detalles internos de cómo se almacenan los productos.</p>
 *
 * @author Portafolio POO - CENFOTEC
 * @version 1.0
 */
public class Inventario {

    private List<Producto> productos;  // Agregación
    private int capacidadMaxima;

    /**
     * Constructor de Inventario.
     *
     * @param capacidadMaxima Número máximo de productos distintos.
     */
    public Inventario(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        this.productos = new ArrayList<>();
    }

    /**
     * Agrega un producto al inventario si hay espacio y no está duplicado.
     *
     * @param producto Producto a agregar.
     * @return {@code true} si fue agregado exitosamente.
     */
    public boolean agregarProducto(Producto producto) {
        if (productos.size() >= capacidadMaxima) {
            System.out.println("Inventario lleno. Capacidad máxima: " + capacidadMaxima);
            return false;
        }
        if (productos.contains(producto)) {  // Usa equals() del producto
            System.out.println("El producto ya existe en el inventario: " + producto.getId());
            return false;
        }
        productos.add(producto);
        return true;
    }

    /**
     * Elimina un producto del inventario por su ID.
     *
     * @param idProducto ID del producto a eliminar.
     * @return {@code true} si fue eliminado.
     */
    public boolean eliminarProducto(String idProducto) {
        return productos.removeIf(p -> p.getId().equals(idProducto));
    }

    /**
     * Busca un producto por su identificador único.
     *
     * @param id ID del producto.
     * @return El producto encontrado, o {@code null} si no existe.
     */
    public Producto buscarProducto(String id) {
        for (Producto p : productos) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

    /**
     * Busca productos por nombre (búsqueda parcial, sin distinción de mayúsculas).
     *
     * @param nombre Término de búsqueda.
     * @return Lista de productos que coinciden.
     */
    public List<Producto> buscarPorNombre(String nombre) {
        return productos.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Retorna todos los productos de un tipo específico.
     * Demuestra polimorfismo mediante el operador {@code instanceof}.
     *
     * @param tipo Clase del tipo de producto (Juego.class, Merchandising.class, etc.)
     * @return Lista filtrada de productos del tipo indicado.
     */
    public List<Producto> buscarPorTipo(Class<?> tipo) {
        return productos.stream()
                .filter(tipo::isInstance)
                .collect(Collectors.toList());
    }

    /**
     * Lista todos los productos con stock disponible mayor a cero.
     *
     * @return Lista de productos disponibles.
     */
    public List<Producto> getProductosDisponibles() {
        return productos.stream()
                .filter(p -> p.getStockDisponible() > 0)
                .collect(Collectors.toList());
    }

    /**
     * Imprime todo el inventario en consola.
     * Usa polimorfismo: {@code toString()} de cada subclase se resuelve
     * en tiempo de ejecución.
     */
    public void mostrarInventario() {
        System.out.println("═══════ INVENTARIO (" + productos.size() + "/" + capacidadMaxima + ") ═══════");
        if (productos.isEmpty()) {
            System.out.println("  (Sin productos)");
        } else {
            for (Producto p : productos) {
                System.out.println(p);  // Polimorfismo: llama a toString() de la subclase
                System.out.println("  ─────────────────────────────────");
            }
        }
    }

    /** @return número de productos actualmente en el inventario. */
    public int getCantidadProductos() { return productos.size(); }

    /** @return la capacidad máxima del inventario. */
    public int getCapacidadMaxima() { return capacidadMaxima; }

    /** @return copia de la lista de todos los productos. */
    public List<Producto> getTodosLosProductos() { return new ArrayList<>(productos); }
}
