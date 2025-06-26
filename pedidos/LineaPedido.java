package pedidos;

package pedidos;

import productos.Producto;

/**
 * Clase LineaPedido que representa una línea de pedido, con un producto y cantidad.
 */
public class LineaPedido {
    private Producto producto;
    private int cantidad;

    public LineaPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return producto.getNombre() + "\tCantidad: " + cantidad + "\tSubtotal: $" + getSubtotal();
    }
}
