package cl.ucn.disc.pa.taller4.dominio.colecciones;

/**
 * Clase que representa un nodo para listas enlazadas simples.
 * Cada nodo contiene un elemento y una referencia al siguiente nodo.
 */
public class Nodo {
    private Elemento elemento;
    private Nodo siguiente;

    /**
     * Constructor que crea un nodo con un elemento específico.
     *
     * @param elemento el elemento a almacenar en el nodo
     * @throws IllegalArgumentException si el elemento es null
     */
    public Nodo(Elemento elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("Los nodos no deben guardar elementos nulos.");
        }
        this.elemento = elemento;
        this.siguiente = null;
    }

    /**
     * Obtiene el elemento almacenado en el nodo.
     *
     * @return el elemento del nodo
     */
    public Elemento getElemento() {
        return elemento;
    }

    /**
     * Obtiene la referencia al siguiente nodo.
     *
     * @return el siguiente nodo
     */
    public Nodo getSiguiente() {
        return siguiente;
    }

    /**
     * Establece la referencia al siguiente nodo.
     *
     * @param siguiente el nodo que será el siguiente
     */
    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
}
