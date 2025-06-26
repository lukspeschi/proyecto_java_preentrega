package pedidos;

package pedidos;

import productos.Producto;
import java.util.ArrayList;

/**
 * Clase Pedido que contiene una lista de líneas de pedido y calcula el total.
 */
public class Pedido {
    private static int contadorId = 1;
    private int id;
    private ArrayList<LineaPedido> lineas;

    public Pedido() {
        this.id = contadorId++;
        this.lineas = new ArrayList<LineaPedido>();
    }

    public int getId() {
        return id;
    }

    public void agregarLinea(Producto producto, int cantidad) throws StockInsuficienteException {
        if (producto.getStock() < cantidad) {
            throw new StockInsuficienteException("Stock insuficiente para el producto " + producto.getNombre());
        }
        producto.setStock(producto.getStock() - cantidad);
        LineaPedido linea = new LineaPedido(producto, cantidad);
        lineas.add(linea);
    }

    public double calcularTotal() {
        double total = 0;
        for (LineaPedido linea : lineas) {
            total += linea.getSubtotal();
        }
        return total;
    }

    public boolean hasLineas() {
        return !lineas.isEmpty();
    }

    public ArrayList<LineaPedido> getLineas() {
        return lineas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido ID: ").append(id).append("\n");
        for (LineaPedido linea : lineas) {
            sb.append(linea.toString()).append("\n");
        }
        sb.append("Total: $").append(calcularTotal());
        return sb.toString();
    }
}
