package cl.ucn.disc.pa.taller4.dominio.colecciones;

public interface Coleccion {
    boolean agregar(Elemento elemento);
    boolean contiene(Elemento elemento);
    void vaciar();
    boolean isVacia();
    boolean eliminar(Elemento elemento);
    int tamanio();
}