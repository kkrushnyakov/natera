/**
 * 
 */
package ru.krushnyakov.natera.lib;

/**
 * @author kkrushnyakov
 *
 */
public class GraphException extends Exception {

    private static final long serialVersionUID = 1L;

    public GraphException() {
    }

    public GraphException(String message) {
        super(message);
    }

    public GraphException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public GraphException(String message, Throwable cause) {
        super(message, cause);
    }

}
