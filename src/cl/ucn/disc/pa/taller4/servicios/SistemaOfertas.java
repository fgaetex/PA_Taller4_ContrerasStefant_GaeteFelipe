package cl.ucn.disc.pa.taller4.servicios;

import java.io.IOException;

public interface SistemaOfertas {

    boolean registrarUsuario(String nombre, String rut, String correo, String contrasenia);

    boolean iniciarSesion(String rut, String contrasenia);

    void ingresarOferta() throws IOException;

    void buscarOferta() throws IOException;

    void verOfertas() throws IOException;

    void editarOferta() throws IOException;

    void eliminarOferta() throws IOException;

    void verPerfil() throws IOException;

    void editarPerfil() throws IOException;

    void cerrarSesion() throws IOException;
}