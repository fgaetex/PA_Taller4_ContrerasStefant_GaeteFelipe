package cl.ucn.disc.pa.taller4.dominio.colecciones;

/**
 * Implementación de una lista enlazada doble que implementa la interface Lista.
 * Utiliza nodos dobles para permitir navegación bidireccional.
 */
public class ContenedorNexoDoble implements Lista {
    private NodoDoble cabecera;
    private NodoDoble cola;
    private int cantidadNodos;

    /**
     * Constructor que inicializa una lista doblemente enlazada vacía.
     */
    public ContenedorNexoDoble() {
        this.cabecera = null;
        this.cola = null;
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

        NodoDoble nuevoNodo;
        try {
            nuevoNodo = new NodoDoble(elemento);
        } catch (IllegalArgumentException excepcion) {
            return false;
        }

        if (this.isVacia()) {
            this.cabecera = nuevoNodo;
            this.cola = nuevoNodo;
        } else {
            nuevoNodo.setSiguiente(this.cabecera);
            this.cabecera.setAnterior(nuevoNodo);
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
        for (NodoDoble aux = this.cabecera; aux != null; aux = aux.getSiguiente()) {
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
        this.cola = null;
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

            if (this.cabecera == null) {
                this.cola = null;
            } else {
                this.cabecera.setAnterior(null);
            }

            this.cantidadNodos--;
            return true;
        }

        // Si el elemento a eliminar está en la cola
        if (this.cola.getElemento().esIgual(elemento)) {
            this.cola = this.cola.getAnterior();
            this.cola.setSiguiente(null);
            this.cantidadNodos--;
            return true;
        }

        // Buscar en el resto de la lista
        for (NodoDoble aux = this.cabecera.getSiguiente(); aux != null; aux = aux.getSiguiente()) {
            if (aux.getElemento().esIgual(elemento)) {
                this.eliminarNodo(aux);
                return true;
            }
        }

        return false;
    }

    /**
     * Método privado para eliminar un nodo específico.
     *
     * @param nodoQueSeElimina el nodo a eliminar
     */
    private void eliminarNodo(NodoDoble nodoQueSeElimina) {
        NodoDoble anterior = nodoQueSeElimina.getAnterior();
        NodoDoble siguiente = nodoQueSeElimina.getSiguiente();

        anterior.setSiguiente(siguiente);
        siguiente.setAnterior(anterior);

        this.cantidadNodos--;
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

        if (posicion == this.cantidadNodos) {
            this.agregarAlUltimo(elemento);
            return;
        }

        NodoDoble nuevoNodo = new NodoDoble(elemento);
        NodoDoble aux = this.cabecera;

        // Avanzar hasta la posición
        for (int i = 0; i < posicion; i++) {
            aux = aux.getSiguiente();
        }

        // Insertar el nuevo nodo antes del nodo actual
        nuevoNodo.setSiguiente(aux);
        nuevoNodo.setAnterior(aux.getAnterior());
        aux.getAnterior().setSiguiente(nuevoNodo);
        aux.setAnterior(nuevoNodo);

        this.cantidadNodos++;
    }

    /**
     * Agrega un elemento al final de la lista.
     *
     * @param elemento el elemento a agregar
     */
    public void agregarAlUltimo(Elemento elemento) {
        NodoDoble nuevoNodo = new NodoDoble(elemento);

        if (this.isVacia()) {
            this.cabecera = nuevoNodo;
            this.cola = nuevoNodo;
        } else {
            this.cola.setSiguiente(nuevoNodo);
            nuevoNodo.setAnterior(this.cola);
            this.cola = nuevoNodo;
        }

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

        NodoDoble aux;

        // Optimización: si la posición está más cerca del final, empezar desde la cola
        if (posicion > this.cantidadNodos / 2) {
            aux = this.cola;
            for (int i = this.cantidadNodos - 1; i > posicion; i--) {
                aux = aux.getAnterior();
            }
        } else {
            aux = this.cabecera;
            for (int i = 0; i < posicion; i++) {
                aux = aux.getSiguiente();
            }
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
        for (NodoDoble aux = this.cabecera; aux != null; aux = aux.getSiguiente()) {
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

        NodoDoble nodoAEliminar;

        // Encontrar el nodo en la posición especificada
        if (posicion > this.cantidadNodos / 2) {
            nodoAEliminar = this.cola;
            for (int i = this.cantidadNodos - 1; i > posicion; i--) {
                nodoAEliminar = nodoAEliminar.getAnterior();
            }
        } else {
            nodoAEliminar = this.cabecera;
            for (int i = 0; i < posicion; i++) {
                nodoAEliminar = nodoAEliminar.getSiguiente();
            }
        }

        Elemento elementoEliminado = nodoAEliminar.getElemento();

        // Eliminar el nodo
        if (nodoAEliminar == this.cabecera && nodoAEliminar == this.cola) {
            // Único elemento
            this.cabecera = null;
            this.cola = null;
        } else if (nodoAEliminar == this.cabecera) {
            // Primer elemento
            this.cabecera = this.cabecera.getSiguiente();
            this.cabecera.setAnterior(null);
        } else if (nodoAEliminar == this.cola) {
            // Último elemento
            this.cola = this.cola.getAnterior();
            this.cola.setSiguiente(null);
        } else {
            // Elemento en el medio
            this.eliminarNodo(nodoAEliminar);
        }

        this.cantidadNodos--;
        return elementoEliminado;
    }

    /**
     * Convierte la lista en un arreglo de elementos.
     *
     * @return un arreglo con todos los elementos de la lista
     */
    public Elemento[] toArray() {
        Elemento[] arreglo = new Elemento[this.cantidadNodos];
        int indice = 0;

        for (NodoDoble aux = this.cabecera; aux != null; aux = aux.getSiguiente()) {
            arreglo[indice] = aux.getElemento();
            indice++;
        }

        return arreglo;
    }
}