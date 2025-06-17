package cl.ucn.disc.pa.taller4.dominio.colecciones;

/**
 * Implementación de una lista enlazada simple que implementa la interface Lista.
 * Utiliza nodos simples para almacenar elementos de forma dinámica.
 */
public class ContenedorNexoSimple implements Lista {
    private Nodo cabecera;
    private int cantidadNodos;

    /**
     * Constructor que inicializa una lista vacía.
     */
    public ContenedorNexoSimple() {
        this.cabecera = null;
        this.cantidadNodos = 0;
    }

    /**
     * Agrega un elemento al inicio de la lista.
     *
     * @param elemento el elemento a agregar
     * @return true si se agregó exitosamente, false si el elemento ya existe
     */
    @Override
    public boolean agregar(Elemento elemento) {
        if (this.contiene(elemento)) {
            return false;
        }

        Nodo nuevoNodo;
        try {
            nuevoNodo = new Nodo(elemento);
        } catch (IllegalArgumentException excepcion) {
            return false;
        }

        // Si la lista está vacía, el nuevo nodo se convierte en la cabecera
        if (this.isVacia()) {
            this.cabecera = nuevoNodo;
        } else {
            // El nuevo nodo apunta a la cabecera actual
            nuevoNodo.setSiguiente(this.cabecera);
            // El nuevo nodo se convierte en la cabecera
            this.cabecera = nuevoNodo;
        }

        this.cantidadNodos++;
        return true;
    }

    /**
     * Verifica si la lista contiene un elemento específico.
     *
     * @param elemento el elemento a buscar
     * @return true si el elemento está en la lista, false en caso contrario
     */
    @Override
    public boolean contiene(Elemento elemento) {
        for (Nodo aux = this.cabecera; aux != null; aux = aux.getSiguiente()) {
            if (aux.getElemento().esIgual(elemento)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vacía completamente la lista.
     */
    @Override
    public void vaciar() {
        this.cabecera = null;
        this.cantidadNodos = 0;
    }

    /**
     * Verifica si la lista está vacía.
     *
     * @return true si la lista está vacía, false en caso contrario
     */
    @Override
    public boolean isVacia() {
        return this.cabecera == null;
    }

    /**
     * Obtiene el tamaño actual de la lista.
     *
     * @return el número de elementos en la lista
     */
    @Override
    public int tamanio() {
        return this.cantidadNodos;
    }

    /**
     * Elimina un elemento de la lista.
     *
     * @param elemento el elemento a eliminar
     * @return true si se eliminó exitosamente, false si no se encontró
     */
    @Override
    public boolean eliminar(Elemento elemento) {
        if (this.isVacia()) {
            return false;
        }

        // Si el elemento a eliminar está en la cabecera
        if (this.cabecera.getElemento().esIgual(elemento)) {
            this.cabecera = this.cabecera.getSiguiente();
            this.cantidadNodos--;
            return true;
        }

        // Buscar en el resto de la lista
        for (Nodo aux = this.cabecera; aux.getSiguiente() != null; aux = aux.getSiguiente()) {
            if (aux.getSiguiente().getElemento().esIgual(elemento)) {
                Nodo nodoQueSeElimina = aux.getSiguiente();
                aux.setSiguiente(nodoQueSeElimina.getSiguiente());
                this.cantidadNodos--;
                return true;
            }
        }

        return false;
    }

    /**
     * Agrega un elemento en una posición específica.
     *
     * @param posicion la posición donde insertar (0-based)
     * @param elemento el elemento a insertar
     * @throws IndexOutOfBoundsException si la posición es inválida
     */
    @Override
    public void agregar(int posicion, Elemento elemento) {
        if (posicion < 0 || posicion > this.cantidadNodos) {
            throw new IndexOutOfBoundsException("Posición inválida: " + posicion);
        }

        if (posicion == 0) {
            this.agregar(elemento);
            return;
        }

        Nodo nuevoNodo = new Nodo(elemento);
        Nodo aux = this.cabecera;

        // Avanzar hasta la posición anterior a donde insertar
        for (int i = 0; i < posicion - 1; i++) {
            aux = aux.getSiguiente();
        }

        nuevoNodo.setSiguiente(aux.getSiguiente());
        aux.setSiguiente(nuevoNodo);
        this.cantidadNodos++;
    }

    /**
     * Obtiene el elemento en una posición específica.
     *
     * @param posicion la posición del elemento (0-based)
     * @return el elemento en la posición especificada
     * @throws IndexOutOfBoundsException si la posición es inválida
     */
    @Override
    public Elemento obtener(int posicion) {
        if (posicion < 0 || posicion >= this.cantidadNodos) {
            throw new IndexOutOfBoundsException("Posición inválida: " + posicion);
        }

        Nodo aux = this.cabecera;
        for (int i = 0; i < posicion; i++) {
            aux = aux.getSiguiente();
        }

        return aux.getElemento();
    }

    /**
     * Obtiene la posición de un elemento en la lista.
     *
     * @param elemento el elemento a buscar
     * @return la posición del elemento, o -1 si no se encuentra
     */
    @Override
    public int posicionDe(Elemento elemento) {
        int posicion = 0;
        for (Nodo aux = this.cabecera; aux != null; aux = aux.getSiguiente()) {
            if (aux.getElemento().esIgual(elemento)) {
                return posicion;
            }
            posicion++;
        }
        return -1;
    }

    /**
     * Elimina el elemento en una posición específica.
     *
     * @param posicion la posición del elemento a eliminar
     * @param elemento el elemento a eliminar (para verificación)
     * @return el elemento eliminado
     * @throws IndexOutOfBoundsException si la posición es inválida
     */
    @Override
    public Elemento eliminar(int posicion, Elemento elemento) {
        if (posicion < 0 || posicion >= this.cantidadNodos) {
            throw new IndexOutOfBoundsException("Posición inválida: " + posicion);
        }

        Elemento elementoEliminado;

        if (posicion == 0) {
            elementoEliminado = this.cabecera.getElemento();
            this.cabecera = this.cabecera.getSiguiente();
        } else {
            Nodo aux = this.cabecera;
            for (int i = 0; i < posicion - 1; i++) {
                aux = aux.getSiguiente();
            }
            elementoEliminado = aux.getSiguiente().getElemento();
            aux.setSiguiente(aux.getSiguiente().getSiguiente());
        }

        this.cantidadNodos--;
        return elementoEliminado;
    }
}
