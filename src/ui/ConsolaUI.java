package ui;

import controller.TiendaController;
import model.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Interfaz de usuario por consola para la tienda de pre-órdenes.
 *
 * <p><b>Arquitectura por capas (UI Layer):</b> Esta clase pertenece a la capa de
 * presentación. Solo se encarga de mostrar información al usuario y capturar
 * sus entradas. Delega toda la lógica al {@link TiendaController}.</p>
 *
 * <p><b>Dependencia:</b> La UI depende del controlador. Nunca accede directamente
 * a las clases del modelo, respetando la separación de capas.</p>
 *
 * @author Portafolio POO - CENFOTEC
 * @version 1.0
 */
public class ConsolaUI {

    private final TiendaController controlador;
    private final Scanner scanner;

    /**
     * Constructor de la interfaz de consola.
     *
     * @param controlador El controlador que gestiona la lógica de negocio.
     */
    public ConsolaUI(TiendaController controlador) {
        this.controlador = controlador;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Inicia el bucle principal del menú de la tienda.
     */
    public void iniciar() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║   BIENVENIDO A LEVEL UP PRE-ORDER STORE  ║");
        System.out.println("╚══════════════════════════════════════════╝");

        boolean ejecutando = true;
        while (ejecutando) {
            mostrarMenuPrincipal();
            int opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> controlador.mostrarInventario();
                case 2 -> flujoCrearPreOrden();
                case 3 -> flujoCancelarPreOrden();
                case 4 -> flujoBuscarProducto();
                case 5 -> controlador.mostrarPreOrdenes();
                case 6 -> controlador.mostrarResumen();
                case 0 -> {
                    System.out.println("\n¡Hasta pronto! Gracias por visitar " +
                            controlador.getTienda().getNombre());
                    ejecutando = false;
                }
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
        scanner.close();
    }

    // ── Menús ────────────────────────────────────────────────────────────────

    private void mostrarMenuPrincipal() {
        System.out.println("\n┌─────────────────────────────┐");
        System.out.println("│        MENÚ PRINCIPAL       │");
        System.out.println("├─────────────────────────────┤");
        System.out.println("│ 1. Ver inventario           │");
        System.out.println("│ 2. Crear pre-orden          │");
        System.out.println("│ 3. Cancelar pre-orden       │");
        System.out.println("│ 4. Buscar producto          │");
        System.out.println("│ 5. Ver pre-órdenes          │");
        System.out.println("│ 6. Resumen de la tienda     │");
        System.out.println("│ 0. Salir                    │");
        System.out.println("└─────────────────────────────┘");
    }

    // ── Flujos de interacción ────────────────────────────────────────────────

    private void flujoCrearPreOrden() {
        System.out.println("\n── NUEVA PRE-ORDEN ──");

        System.out.print("ID del cliente: ");
        String idCliente = scanner.nextLine().trim();
        System.out.print("Nombre del cliente: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Teléfono: ");
        String tel = scanner.nextLine().trim();

        Cliente cliente = controlador.registrarCliente(idCliente, nombre, email, tel);
        if (cliente == null) {
            // Si ya existe, buscarlo
            cliente = controlador.getTienda().getClientes().stream()
                    .filter(c -> c.getId().equals(idCliente))
                    .findFirst().orElse(null);
            if (cliente == null) {
                System.out.println("Error: no se pudo obtener el cliente.");
                return;
            }
        }

        controlador.mostrarInventario();
        System.out.print("ID del producto a pre-ordenar: ");
        String idProducto = scanner.nextLine().trim();

        double deposito = leerDouble("Monto del depósito ($): ");
        System.out.print("Método de pago (TARJETA/SINPE/EFECTIVO): ");
        String metodo = scanner.nextLine().trim().toUpperCase();

        PreOrden orden = controlador.crearPreOrden(cliente, idProducto, deposito, metodo);
        if (orden != null) {
            System.out.println("\n✓ Pre-orden registrada exitosamente:");
            System.out.println(orden);
        }
    }

    private void flujoCancelarPreOrden() {
        System.out.println("\n── CANCELAR PRE-ORDEN ──");
        System.out.print("ID del cliente: ");
        String idCliente = scanner.nextLine().trim();

        Cliente cliente = controlador.getTienda().getClientes().stream()
                .filter(c -> c.getId().equals(idCliente))
                .findFirst().orElse(null);

        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        List<PreOrden> ordenes = cliente.getHistorialPreOrdenes();
        if (ordenes.isEmpty()) {
            System.out.println("Este cliente no tiene pre-órdenes.");
            return;
        }

        System.out.println("Pre-órdenes del cliente:");
        for (PreOrden o : ordenes) {
            System.out.println("  - " + o.getIdOrden() + " | " +
                    o.getProducto().getNombre() + " | Estado: " + o.getEstado());
        }

        System.out.print("ID de la orden a cancelar: ");
        String idOrden = scanner.nextLine().trim();

        boolean resultado = controlador.cancelarPreOrden(cliente, idOrden);
        System.out.println(resultado
                ? "✓ Pre-orden cancelada y depósito reembolsado."
                : "✗ No se pudo cancelar la pre-orden.");
    }

    private void flujoBuscarProducto() {
        System.out.print("\nTérmino de búsqueda: ");
        String termino = scanner.nextLine().trim();
        List<Producto> resultados = controlador.buscarProducto(termino);

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron productos con \"" + termino + "\".");
        } else {
            System.out.println("Resultados (" + resultados.size() + "):");
            resultados.forEach(System.out::println);
        }
    }

    // ── Utilidades de entrada ────────────────────────────────────────────────

    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        try {
            int valor = Integer.parseInt(scanner.nextLine().trim());
            return valor;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private double leerDouble(String mensaje) {
        System.out.print(mensaje);
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
