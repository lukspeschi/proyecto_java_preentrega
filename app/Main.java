package app;

package app;

import productos.Producto;
import pedidos.Pedido;
import pedidos.StockInsuficienteException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase principal con menú interactivo para gestionar productos y pedidos.
 */
public class Main {
    private static ArrayList<Producto> productos = new ArrayList<>();
    private static ArrayList<Pedido> pedidos = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = -1;
        while (opcion != 7) {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
                continue;
            }
            switch (opcion) {
                case 1:
                    agregarProducto();
                    break;
                case 2:
                    mostrarProductos();
                    break;
                case 3:
                    buscarYActualizarProducto();
                    break;
                case 4:
                    eliminarProducto();
                    break;
                case 5:
                    crearPedido();
                    break;
                case 6:
                    listarPedidos();
                    break;
                case 7:
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida, intente nuevamente.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Menú Principal ---");
        System.out.println("1. Alta de producto");
        System.out.println("2. Mostrar productos");
        System.out.println("3. Buscar y actualizar producto");
        System.out.println("4. Eliminar producto");
        System.out.println("5. Crear pedido");
        System.out.println("6. Listar pedidos");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void agregarProducto() {
        try {
            System.out.print("Ingrese nombre del producto: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese precio del producto: ");
            double precio = Double.parseDouble(scanner.nextLine());
            System.out.print("Ingrese stock del producto: ");
            int stock = Integer.parseInt(scanner.nextLine());
            Producto producto = new Producto(nombre, precio, stock);
            productos.add(producto);
            System.out.println("Producto agregado con éxito. ID: " + producto.getId());
        } catch (NumberFormatException e) {
            System.out.println("Error: entrada inválida. El producto no fue agregado.");
        }
    }

    private static void mostrarProductos() {
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
        } else {
            System.out.println("\nID\tNombre\tPrecio\tStock");
            for (Producto producto : productos) {
                System.out.println(producto.toString());
            }
        }
    }

    private static void buscarYActualizarProducto() {
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        System.out.println("Buscar producto por:");
        System.out.println("1. ID");
        System.out.println("2. Nombre");
        System.out.print("Opción: ");
        int criterio;
        try {
            criterio = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opción inválida.");
            return;
        }
        Producto encontrado = null;
        if (criterio == 1) {
            System.out.print("Ingrese ID del producto: ");
            try {
                int id = Integer.parseInt(scanner.nextLine());
                for (Producto p : productos) {
                    if (p.getId() == id) {
                        encontrado = p;
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida.");
                return;
            }
        } else if (criterio == 2) {
            System.out.print("Ingrese nombre del producto: ");
            String nombre = scanner.nextLine();
            for (Producto p : productos) {
                if (p.getNombre().equalsIgnoreCase(nombre)) {
                    encontrado = p;
                    break;
                }
            }
        } else {
            System.out.println("Opción inválida.");
            return;
        }
        if (encontrado == null) {
            System.out.println("Producto no encontrado.");
            return;
        }
        System.out.println("Producto encontrado: " + encontrado.toString());
        System.out.println("¿Qué desea actualizar?");
        System.out.println("1. Precio");
        System.out.println("2. Stock");
        System.out.print("Opción: ");
        int campo;
        try {
            campo = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return;
        }
        if (campo == 1) {
            try {
                System.out.print("Ingrese nuevo precio: ");
                double nuevoPrecio = Double.parseDouble(scanner.nextLine());
                encontrado.setPrecio(nuevoPrecio);
                System.out.println("Precio actualizado.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida.");
            }
        } else if (campo == 2) {
            try {
                System.out.print("Ingrese nuevo stock: ");
                int nuevoStock = Integer.parseInt(scanner.nextLine());
                encontrado.setStock(nuevoStock);
                System.out.println("Stock actualizado.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida.");
            }
        } else {
            System.out.println("Opción inválida.");
        }
    }

    private static void eliminarProducto() {
        if (productos.isEmpty()) {
            System.out.println("No hay productos para eliminar.");
            return;
        }
        System.out.print("Ingrese el ID del producto a eliminar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Producto aEliminar = null;
            for (Producto p : productos) {
                if (p.getId() == id) {
                    aEliminar = p;
                    break;
                }
            }
            if (aEliminar != null) {
                productos.remove(aEliminar);
                System.out.println("Producto eliminado.");
            } else {
                System.out.println("Producto no encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }

    private static void crearPedido() {
        if (productos.isEmpty()) {
            System.out.println("No hay productos disponibles para pedidos.");
            return;
        }
        Pedido pedido = new Pedido();
        while (true) {
            System.out.print("Ingrese ID del producto a agregar al pedido: ");
            try {
                int id = Integer.parseInt(scanner.nextLine());
                Producto prodSeleccionado = null;
                for (Producto p : productos) {
                    if (p.getId() == id) {
                        prodSeleccionado = p;
                        break;
                    }
                }
                if (prodSeleccionado == null) {
                    System.out.println("Producto no encontrado.");
                } else {
                    System.out.print("Ingrese cantidad: ");
                    int cantidad = Integer.parseInt(scanner.nextLine());
                    try {
                        pedido.agregarLinea(prodSeleccionado, cantidad);
                        System.out.println("Producto agregado al pedido.");
                    } catch (StockInsuficienteException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida.");
            }
            System.out.print("¿Desea agregar otro producto al pedido? (s/n): ");
            String respuesta = scanner.nextLine();
            if (!respuesta.equalsIgnoreCase("s")) {
                break;
            }
        }
        if (pedido.hasLineas()) {
            pedidos.add(pedido);
            System.out.println("Pedido creado con éxito. ID: " + pedido.getId());
            System.out.println("Total del pedido: $" + pedido.calcularTotal());
        } else {
            System.out.println("Pedido sin productos. No se creó ningún pedido.");
        }
    }

    private static void listarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("No se han realizado pedidos aún.");
        } else {
            for (Pedido p : pedidos) {
                System.out.println("\n" + p.toString());
            }
        }
    }
}
