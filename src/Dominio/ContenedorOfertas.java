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
            throw new IllegalArgumentException("No hay espacio en el contenedor para nuevas cartas.");
        }

        if (nueva == null) {
            throw new IllegalArgumentException("No se puede agregar el carta, ya que es nula.");
        }

        this.ofertas[this.cantidadActual] = nueva;
        this.cantidadActual++;
    }

    public Oferta obtener(int posicion) {
        if (posicion < 0 || posicion >= this.cantidadActual) {
            throw new IllegalArgumentException("La posicion no es valida.");
        }

        return this.ofertas[posicion];
    }

}
