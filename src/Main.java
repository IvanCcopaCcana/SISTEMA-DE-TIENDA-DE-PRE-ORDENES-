import controller.TiendaController;
import model.*;
import ui.ConsolaUI;
import java.time.LocalDate;

/**
 * Punto de entrada principal de la aplicación.
 *
 * <p>Inicializa la tienda con datos de demostración y lanza la
 * interfaz de usuario por consola.</p>
 *
 * <p><b>Arquitectura por capas:</b></p>
 * <ul>
 *   <li><b>UI Layer</b>    → {@code ui.ConsolaUI}</li>
 *   <li><b>Control Layer</b> → {@code controller.TiendaController}</li>
 *   <li><b>Model Layer</b>  → {@code model.*}</li>
 * </ul>
 *
 * @author Portafolio POO - CENFOTEC
 * @version 1.0
 */
public class Main {

    /**
     * Método principal de la aplicación.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {

        // ── 1. Crear la tienda (Composición: crea su Inventario internamente) ──
        Tienda tienda = new Tienda(
                "Level Up Pre-Order Store",
                "Calle 10, Av. Central, San José, CR",
                50
        );

        TiendaController controlador = new TiendaController(tienda);

        // ── 2. Poblar inventario con datos de demostración ────────────────────
        cargarDatosDemostracion(controlador);

        // ── 3. Lanzar la interfaz de usuario ─────────────────────────────────
        ConsolaUI ui = new ConsolaUI(controlador);
        ui.iniciar();
    }

    /**
     * Carga productos y clientes de demostración para pruebas.
     *
     * @param ctrl Controlador de la tienda.
     */
    private static void cargarDatosDemostracion(TiendaController ctrl) {

        // Juegos base
        Juego zelda = new Juego(
                "J001", "The Legend of Zelda: Echoes of Wisdom",
                69.99, 10,
                "Nintendo Switch", "Aventura",
                LocalDate.of(2026, 9, 26)
        );

        Juego gta6 = new Juego(
                "J002", "Grand Theft Auto VI",
                79.99, 25,
                "PS5", "Acción",
                LocalDate.of(2026, 5, 15)
        );

        // Edición limitada (herencia multinivel: EdicionLimitada -> Juego -> Producto)
        EdicionLimitada zeldaLE = new EdicionLimitada(
                "EL001", "Zelda: Echoes of Wisdom - Collector's Edition",
                69.99, 3,
                "Nintendo Switch", "Aventura",
                LocalDate.of(2026, 9, 26),
                42,                             // Numeración: copia #42
                "Artbook, OST, figura de Link",  // Contenido extra
                true,                            // Exclusiva de la tienda
                0.40                             // 40% de precio premium
        );

        // Merchandising (herencia directa de Producto)
        Merchandising figuraLink = new Merchandising(
                "M001", "Figura de Link - Edición Deluxe",
                49.99, 15,
                "Figura de acción",
                "The Legend of Zelda",
                "PVC de alta calidad"
        );

        Merchandising camisetaGTA = new Merchandising(
                "M002", "Camiseta Oficial GTA VI",
                29.99, 30,
                "Ropa",
                "Grand Theft Auto",
                "100% algodón"
        );

        // Agregar al inventario a través del controlador
        ctrl.agregarProducto(zelda);
        ctrl.agregarProducto(gta6);
        ctrl.agregarProducto(zeldaLE);
        ctrl.agregarProducto(figuraLink);
        ctrl.agregarProducto(camisetaGTA);

        // Registrar clientes de demostración
        Cliente mario = ctrl.registrarCliente(
                "C001", "Mario Rodríguez", "mario@email.com", "8888-1111"
        );
        Cliente ana = ctrl.registrarCliente(
                "C002", "Ana Vargas", "ana@email.com", "7777-2222"
        );

        // Crear pre-órdenes de ejemplo
        if (mario != null) {
            ctrl.crearPreOrden(mario, "EL001", 35.00, "TARJETA");
        }
        if (ana != null) {
            ctrl.crearPreOrden(ana, "J002", 20.00, "SINPE");
        }

        System.out.println("\n✓ Datos de demostración cargados correctamente.\n");
    }
}
