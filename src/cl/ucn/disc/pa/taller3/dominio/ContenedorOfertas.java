package cl.ucn.disc.pa.taller3.dominio;

public class ContenedorOfertas {

    private Oferta[] ofertas;
    private int cantidadMaxima;
    private int cantidadActual;

    public ContenedorOfertas(int cantidadMaxima) {
        this.ofertas = new Oferta[cantidadMaxima];
        this.cantidadMaxima = cantidadMaxima;
        this.cantidadActual = 0;
    }

    public int getCantidadActual() {
        return cantidadActual;
    }

    public void agregar(Oferta nueva) {
        if (this.cantidadActual >= this.cantidadMaxima) {
            throw new IllegalArgumentException("No hay espacio en el contenedor para nuevas ofertas.");
        }

        if (nueva == null) {
            throw new IllegalArgumentException("No se puede agregar la oferta, ya que es nula.");
        }

        this.ofertas[this.cantidadActual] = nueva;
        this.cantidadActual++;
    }

    public int buscarPorId(String id) {
        for (int i = 0; i < cantidadActual; i++) {
            if (ofertas[i].getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public int buscarPorTitulo(String titulo) {
        for (int i = 0; i < cantidadActual; i++) {
            if (ofertas[i].getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

    public Oferta obtener(int posicion) {
        if (posicion < 0 || posicion >= this.cantidadActual) {
            throw new IllegalArgumentException("La posicion no es valida.");
        }

        return this.ofertas[posicion];
    }

    public boolean eliminar(String id) {
        int posicion = buscarPorId(id);
        if (posicion == -1) {
            return false;
        }

        for (int i = posicion; i < cantidadActual - 1; i++) {
            ofertas[i] = ofertas[i + 1];
        }
        ofertas[cantidadActual - 1] = null;
        cantidadActual--;
        return true;
    }
}