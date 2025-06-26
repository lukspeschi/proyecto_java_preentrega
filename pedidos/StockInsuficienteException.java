package pedidos;

package pedidos;

/**
 * Excepción personalizada para indicar cuando no hay suficiente stock.
 */
public class StockInsuficienteException extends Exception {
    public StockInsuficienteException(String message) {
        super(message);
    }
}
