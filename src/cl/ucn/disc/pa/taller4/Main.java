package cl.ucn.disc.pa.taller4;

import cl.ucn.disc.pa.taller4.servicios.SistemaOfertasUCN;
import cl.ucn.disc.pa.taller4.vista.VistaConsola;

/**
 * Clase principal del sistema de gestión de ofertas academicas.
 * - @author Stefant Contreras Rojas, Felipe Gaete Fernandez
 *
 */


public class Main {
    public static void main(String[] args) {
        SistemaOfertasUCN sistema = new SistemaOfertasUCN();
        VistaConsola vista = new VistaConsola(sistema);
        vista.iniciar();
    }
}

