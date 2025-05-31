package cl.ucn.disc.pa.taller3;

import cl.ucn.disc.pa.taller3.servicios.SistemaOfertasUCN;
import cl.ucn.disc.pa.taller3.vista.VistaConsola;

public class Main {
    public static void main(String[] args) {
        SistemaOfertasUCN sistema = new SistemaOfertasUCN();
        VistaConsola vista = new VistaConsola(sistema);
        vista.iniciar();
    }
}