package cl.ucn.disc.pa.taller3.dominio;

import java.time.LocalDate;

public abstract class Oferta {
    private String id;
    private String titulo;
    private String descripcion;
    private String nombreUnidad;
    private int duracionDias;
    private LocalDate fechaPublicacion;
    private LocalDate fechaCierre;

    public Oferta(String id, String titulo, String descripcion, String nombreUnidad, int duracionDias) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.nombreUnidad = nombreUnidad;
        this.duracionDias = duracionDias;
        calcularFechas();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public int getDuracionDias() {
        return duracionDias;
    }

    public void setDuracionDias(int duracionDias) {
        this.duracionDias = duracionDias;
        calcularFechas();
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public LocalDate getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDate fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public void calcularFechas() {
        this.fechaPublicacion = LocalDate.now();
        this.fechaCierre = this.fechaPublicacion.plusDays(duracionDias);
    }

    @Override
    public abstract String toString();
}