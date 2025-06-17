package cl.ucn.disc.pa.taller4.dominio.colecciones;

/**
 * Clase que representa un nodo para listas enlazadas dobles.
 * Cada nodo contiene un elemento y referencias tanto al nodo anterior como al siguiente.
 */
public class NodoDoble {
    private Elemento elemento;
    private NodoDoble anterior;
    private NodoDoble siguiente;

    /**
     * Constructor que crea un nodo doble con un elemento específico.
     *
     * @param elemento el elemento a almacenar en el nodo
     * @throws IllegalArgumentException si el elemento es null
     */
    public NodoDoble(Elemento elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("Los nodos no deben guardar elementos nulos.");
        }
        this.elemento = elemento;
        this.anterior = null;
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
     * Obtiene la referencia al nodo anterior.
     *
     * @return el nodo anterior
     */
    public NodoDoble getAnterior() {
        return anterior;
    }

    /**
     * Obtiene la referencia al siguiente nodo.
     *
     * @return el siguiente nodo
     */
    public NodoDoble getSiguiente() {
        return siguiente;
    }

    /**
     * Establece la referencia al nodo anterior.
     *
     * @param anterior el nodo que será el anterior
     */
    public void setAnterior(NodoDoble anterior) {
        this.anterior = anterior;
    }

    /**
     * Establece la referencia al siguiente nodo.
     *
     * @param siguiente el nodo que será el siguiente
     */
    public void setSiguiente(NodoDoble siguiente) {
        this.siguiente = siguiente;
    }
}