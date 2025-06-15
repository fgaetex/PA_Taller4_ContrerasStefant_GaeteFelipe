package cl.ucn.disc.pa.taller4.dominio;

import java.time.LocalDate;
import java.util.Arrays;

public class Capstone extends ActividadEmpresa {
    private String tipoProyecto;
    private int duracionMeses;
    private String [] carrerasNecesarias;
    private int cantidadMinEstudiantes;

    public Capstone(String id, String titulo, String descripcion, String nombreUnidad, int duracionDias, LocalDate fechaInicio, String nombreEmpresa, String nombreGuia, String tipoProyecto, int duracionMeses, String[] carrerasNecesarias, int cantidadMinEstudiantes) {
        super(id, titulo, descripcion, nombreUnidad, duracionDias, fechaInicio, nombreEmpresa, nombreGuia);
        this.tipoProyecto = tipoProyecto;
        this.duracionMeses = duracionMeses;
        this.carrerasNecesarias = carrerasNecesarias;
        this.cantidadMinEstudiantes = cantidadMinEstudiantes;
    }

    public String getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(String tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    public int getDuracionMeses() {
        return duracionMeses;
    }

    public void setDuracionMeses(int duracionMeses) {
        this.duracionMeses = duracionMeses;
    }

    public String[] getCarrerasNecesarias() {
        return carrerasNecesarias;
    }

    public void setCarrerasNecesarias(String[] carrerasNecesarias) {
        this.carrerasNecesarias = carrerasNecesarias;
    }

    public int getCantidadMinEstudiantes() {
        return cantidadMinEstudiantes;
    }

    public void setCantidadMinEstudiantes(int cantidadMinEstudiantes) {
        this.cantidadMinEstudiantes = cantidadMinEstudiantes;
    }

    @Override
    public String toString() {
        return "Capstone{" +
                "id='" + getId() + '\'' +
                ", titulo='" + getTitulo() + '\'' +
                ", descripcion='" + getDescripcion() + '\'' +
                ", nombreUnidad='" + getNombreUnidad() + '\'' +
                ", duracionDias=" + getDuracionDias() +
                ", fechaInicio=" + getFechaInicio() +
                ", nombreEmpresa='" + getNombreEmpresa() + '\'' +
                ", nombreGuia='" + getNombreGuia() + '\'' +
                ", tipoProyecto='" + tipoProyecto + '\'' +
                ", duracionMeses=" + duracionMeses +
                ", carrerasNecesarias=" + Arrays.toString(carrerasNecesarias) +
                ", cantidadMinEstudiantes=" + cantidadMinEstudiantes +
                '}';
    }
}