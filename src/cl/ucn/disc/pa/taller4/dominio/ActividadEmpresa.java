package cl.ucn.disc.pa.taller4.dominio;

import java.time.LocalDate;

public abstract class ActividadEmpresa extends Oferta {
    private LocalDate fechaInicio;
    private String nombreEmpresa;
    private String nombreGuia;

    public ActividadEmpresa(String id, String titulo, String descripcion, String nombreUnidad, int duracionDias, LocalDate fechaInicio, String nombreEmpresa, String nombreGuia) {
        super(id, titulo, descripcion, nombreUnidad, duracionDias);
        this.fechaInicio = fechaInicio;
        this.nombreEmpresa = nombreEmpresa;
        this.nombreGuia = nombreGuia;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNombreGuia() {
        return nombreGuia;
    }

    public void setNombreGuia(String nombreGuia) {
        this.nombreGuia = nombreGuia;
    }
}
