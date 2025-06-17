package cl.ucn.disc.pa.taller4.dominio.colecciones;

public interface Lista extends Coleccion {

    void agregar(int posicion, Elemento elemento);
    Elemento obtener(int posicion);
    int posicionDe(Elemento elemento);
    Elemento eliminar(int posicion, Elemento elemento);
}