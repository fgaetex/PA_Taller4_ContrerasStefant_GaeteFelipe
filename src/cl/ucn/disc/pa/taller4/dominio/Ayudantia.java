package cl.ucn.disc.pa.taller4.dominio;

public class Ayudantia extends Oferta {
    private String nombreAsignatura;
    private String rolAyudante;
    private int horasSemanales;
    private double promedioMinimo;
    private String tipoAyudantia;

    public Ayudantia(String id, String titulo, String descripcion, String nombreUnidad, int duracionDias,
                     String nombreAsignatura, String rolAyudante, int horasSemanales, double promedioMinimo, String tipoAyudantia) {
        super(id, titulo, descripcion, nombreUnidad, duracionDias);
        this.nombreAsignatura = nombreAsignatura;
        this.rolAyudante = rolAyudante;
        this.horasSemanales = horasSemanales;
        this.promedioMinimo = promedioMinimo;
        this.tipoAyudantia = tipoAyudantia;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public String getRolAyudante() {
        return rolAyudante;
    }

    public void setRolAyudante(String rolAyudante) {
        this.rolAyudante = rolAyudante;
    }

    public int getHorasSemanales() {
        return horasSemanales;
    }

    public void setHorasSemanales(int horasSemanales) {
        this.horasSemanales = horasSemanales;
    }

    public double getPromedioMinimo() {
        return promedioMinimo;
    }

    public void setPromedioMinimo(double promedioMinimo) {
        this.promedioMinimo = promedioMinimo;
    }

    public String getTipoAyudantia() {
        return tipoAyudantia;
    }

    public void setTipoAyudantia(String tipoAyudantia) {
        this.tipoAyudantia = tipoAyudantia;
    }

    @Override
    public String toString() {
        return "Ayudantia{" +
                "id='" + getId() + '\'' +
                ", titulo='" + getTitulo() + '\'' +
                ", descripcion='" + getDescripcion() + '\'' +
                ", nombreUnidad='" + getNombreUnidad() + '\'' +
                ", duracionDias=" + getDuracionDias() +
                ", nombreAsignatura='" + nombreAsignatura + '\'' +
                ", rolAyudante='" + rolAyudante + '\'' +
                ", horasSemanales=" + horasSemanales +
                ", promedioMinimo=" + promedioMinimo +
                ", tipoAyudantia='" + tipoAyudantia + '\'' +
                '}';
    }
}