package cl.ucn.disc.pa.taller4.vista;

import cl.ucn.disc.pa.taller4.servicios.SistemaOfertas;
import cl.ucn.disc.pa.taller4.servicios.SistemaOfertasUCN;
import cl.ucn.disc.pa.taller4.utilidades.ValidadorEntrada;
import ucn.StdOut;

public class VistaConsola {

    private SistemaOfertas sistema;
    private SistemaOfertasUCN sistemaUCN;

    public VistaConsola(SistemaOfertas sistema) {
        this.sistema = sistema;
        this.sistemaUCN = (SistemaOfertasUCN) sistema;
    }

    public void iniciar() {
        StdOut.println("=== SISTEMA DE OFERTAS ACADÉMICAS UCN ===");

        menuRegistroLogin();

        if (sistemaUCN.getUsuarioActual() != null) {
            menuPrincipal();
        }
    }

    private void menuRegistroLogin() {
        while (sistemaUCN.getUsuarioActual() == null) {
            try {
                StdOut.println("\n=== REGISTRO E INICIO DE SESIÓN ===");
                StdOut.println("[1] Registrar Usuario");
                StdOut.println("[2] Iniciar Sesión");
                StdOut.println("[3] Salir del Programa");

                int opcion = ValidadorEntrada.validarEnteros("Seleccione una opción");

                switch (opcion) {
                    case 1:
                        registrarUsuario();
                        break;
                    case 2:
                        iniciarSesion();
                        break;
                    case 3:
                        StdOut.println("¡Hasta luego!");
                        System.exit(0);
                        break;
                    default:
                        StdOut.println("Opción no válida. Intente nuevamente.");
                }

            } catch (Exception e) {
                StdOut.println("Error: " + e.getMessage());
            }
        }
    }

    private void registrarUsuario() {
        StdOut.println("\n=== REGISTRO DE USUARIO ===");

        String nombre = ValidadorEntrada.validarString("Ingrese su nombre");
        String rut = ValidadorEntrada.validarString("Ingrese su RUT (sin puntos ni guión)");
        String correo = ValidadorEntrada.validarString("Ingrese su correo electrónico");
        String contrasenia = ValidadorEntrada.validarString("Ingrese su contraseña");

        sistema.registrarUsuario(nombre, rut, correo, contrasenia);
    }

    private void iniciarSesion() {
        StdOut.println("\n=== INICIO DE SESIÓN ===");

        String rut = ValidadorEntrada.validarString("Ingrese su RUT");
        String contrasenia = ValidadorEntrada.validarString("Ingrese su contraseña");

        sistema.iniciarSesion(rut, contrasenia);
    }

    private void menuPrincipal() {
        while (sistemaUCN.getUsuarioActual() != null) {
            try {
                StdOut.println("\n=== MENÚ PRINCIPAL ===");
                StdOut.println("[1] Ingresar Oferta");
                StdOut.println("[2] Buscar Oferta");
                StdOut.println("[3] Ver Ofertas");
                StdOut.println("[4] Editar Oferta");
                StdOut.println("[5] Eliminar Oferta");
                StdOut.println("[6] Ver Perfil");
                StdOut.println("[7] Editar Perfil");
                StdOut.println("[8] Cerrar Sesión");

                int opcion = ValidadorEntrada.validarEnteros("Seleccione una opción");

                switch (opcion) {
                    case 1:
                        sistema.ingresarOferta();
                        break;
                    case 2:
                        sistema.buscarOferta();
                        break;
                    case 3:
                        sistema.verOfertas();
                        break;
                    case 4:
                        sistema.editarOferta();
                        break;
                    case 5:
                        sistema.eliminarOferta();
                        break;
                    case 6:
                        sistema.verPerfil();
                        break;
                    case 7:
                        sistema.editarPerfil();
                        break;
                    case 8:
                        sistema.cerrarSesion();
                        break;
                    default:
                        StdOut.println("Opción no válida. Intente nuevamente.");
                }

            } catch (Exception e) {
                StdOut.println("Error: " + e.getMessage());
            }
        }

        StdOut.println("Sesión cerrada. Volviendo al menú de registro...");
        menuRegistroLogin();
    }

    public int lecturaEnteros(String frase) {
        return ValidadorEntrada.validarEnteros(frase);
    }
}