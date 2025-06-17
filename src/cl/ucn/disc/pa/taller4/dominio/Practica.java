package cl.ucn.disc.pa.taller4.dominio;


import java.time.LocalDate;

public class Practica extends ActividadEmpresa {
    private boolean poseeRemuneracion;

    public Practica(String id, String titulo, String descripcion, String nombreUnidad, int duracionDias, LocalDate fechaInicio, String nombreEmpresa, String nombreGuia, boolean poseeRemuneracion) {
        super(id, titulo, descripcion, nombreUnidad, duracionDias, fechaInicio, nombreEmpresa, nombreGuia);
        this.poseeRemuneracion = poseeRemuneracion;
    }

    public boolean isPoseeRemuneracion() {
        return poseeRemuneracion;
    }

    public void setPoseeRemuneracion(boolean poseeRemuneracion) {
        this.poseeRemuneracion = poseeRemuneracion;
    }

    @Override
    public String toString() {
        return "Practica{" +
                "id='" + getId() + '\'' +
                ", titulo='" + getTitulo() + '\'' +
                ", descripcion='" + getDescripcion() + '\'' +
                ", nombreUnidad='" + getNombreUnidad() + '\'' +
                ", duracionDias=" + getDuracionDias() +
                ", fechaInicio=" + getFechaInicio() +
                ", nombreEmpresa='" + getNombreEmpresa() + '\'' +
                ", nombreGuia='" + getNombreGuia() + '\'' +
                ", poseeRemuneracion=" + poseeRemuneracion +
                '}';
    }
}