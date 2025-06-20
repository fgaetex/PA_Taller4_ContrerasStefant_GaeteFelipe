package cl.ucn.disc.pa.taller4.servicios;

import cl.ucn.disc.pa.taller4.dominio.*;
import cl.ucn.disc.pa.taller4.dominio.colecciones.ContenedorNexoDoble;
import cl.ucn.disc.pa.taller4.dominio.colecciones.ContenedorNexoSimple;
import cl.ucn.disc.pa.taller4.utilidades.ValidadorEntrada;
import ucn.StdIn;
import ucn.StdOut;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SistemaOfertasUCN implements SistemaOfertas {
    private ContenedorNexoDoble contenedorOfertas;
    private ContenedorNexoSimple contenedorUsuarios;
    private Usuario usuarioActual;
    private String archivoOfertas;
    private String archivoUsuarios;

    public SistemaOfertasUCN() {
        this.contenedorOfertas = new ContenedorNexoDoble();
        this.contenedorUsuarios = new ContenedorNexoSimple();
        this.archivoOfertas = "OfertasAcademicas.txt";
        this.archivoUsuarios = "Usuarios.txt";
        try {
            leerArchivoUsuarios();
            leerArchivoOfertas();
        } catch (IOException e) {
            StdOut.println("Error al cargar datos: " + e.getMessage());
        }
    }

    private void leerArchivoUsuarios() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(archivoUsuarios))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 4) {
                    Usuario usuario = new Usuario(datos[0], datos[1], datos[2], datos[3]);
                    contenedorUsuarios.agregar(usuario);
                }
            }
        } catch (FileNotFoundException e) {
            StdOut.println("Archivo de usuarios no encontrado. Se iniciará con usuarios vacíos.");
        } catch (Exception e) {
            StdOut.println("Error al leer archivo de usuarios: " + e.getMessage());
        }
    }

    private void leerArchivoOfertas() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(archivoOfertas))) {
            String linea;
            br.readLine(); // Saltar primera linea de encabezado

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos[0].equals("A")) {
                    Ayudantia ayudantia = new Ayudantia(
                            datos[1], datos[2], datos[3], datos[4],
                            Integer.parseInt(datos[5]), datos[6], datos[7],
                            Integer.parseInt(datos[8]), Double.parseDouble(datos[9]), datos[7]
                    );
                    contenedorOfertas.agregar(ayudantia);

                } else if (datos[0].equals("C")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate fechaInicio = LocalDate.parse(datos[6], formatter);

                    String carrerasStr = datos[11].replace("[", "").replace("]", "").replace("\"", "");
                    String[] carreras = carrerasStr.split(", ");

                    Capstone capstone = new Capstone(
                            datos[1], datos[2], datos[3], datos[4],
                            Integer.parseInt(datos[5]), fechaInicio, datos[7], datos[8],
                            datos[9], Integer.parseInt(datos[10]), carreras, Integer.parseInt(datos[12])
                    );
                    contenedorOfertas.agregar(capstone);

                } else if (datos[0].equals("P")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate fechaInicio = LocalDate.parse(datos[6], formatter);

                    boolean remuneracion = datos[9].equalsIgnoreCase("Si");

                    Practica practica = new Practica(
                            datos[1], datos[2], datos[3], datos[4],
                            Integer.parseInt(datos[5]), fechaInicio, datos[7], datos[8], remuneracion
                    );
                    contenedorOfertas.agregar(practica);
                }
            }
        } catch (FileNotFoundException e) {
            StdOut.println("Archivo de ofertas no encontrado. Se iniciará con ofertas vacías.");
        } catch (Exception e) {
            StdOut.println("Error al leer archivo de ofertas: " + e.getMessage());
        }
    }

    private void guardarArchivoUsuarios() throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivoUsuarios))) {
            for (int i = 0; i < contenedorUsuarios.tamanio(); i++) {
                Usuario usuario = (Usuario) contenedorUsuarios.obtener(i);
                pw.println(usuario.getNombre() + "," + usuario.getRut() + "," +
                        usuario.getCorreo() + "," + usuario.getContrasenia());
            }
        }
    }

    private void guardarArchivoOfertas() throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivoOfertas))) {
            pw.println("Tipo de oferta: A se refiere a Ayudantía, C a Capstone, P a Práctica");

            for (int i = 0; i < contenedorOfertas.tamanio(); i++) {
                Oferta oferta = (Oferta) contenedorOfertas.obtener(i);

                if (oferta instanceof Ayudantia) {
                    Ayudantia a = (Ayudantia) oferta;
                    pw.println("A," + a.getId() + "," + a.getTitulo() + "," + a.getDescripcion() + "," +
                            a.getNombreUnidad() + "," + a.getDuracionDias() + "," + a.getNombreAsignatura() + "," +
                            a.getRolAyudante() + "," + a.getHorasSemanales() + "," + a.getPromedioMinimo());
                } else if (oferta instanceof Capstone) {
                    Capstone c = (Capstone) oferta;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    pw.println("C," + c.getId() + "," + c.getTitulo() + "," + c.getDescripcion() + "," +
                            c.getNombreUnidad() + "," + c.getDuracionDias() + "," +
                            c.getFechaInicio().format(formatter) + "," + c.getNombreEmpresa() + "," +
                            c.getNombreGuia() + "," + c.getTipoProyecto() + "," + c.getDuracionMeses() + "," +
                            java.util.Arrays.toString(c.getCarrerasNecesarias()) + "," + c.getCantidadMinEstudiantes());
                } else if (oferta instanceof Practica) {
                    Practica p = (Practica) oferta;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    pw.println("P," + p.getId() + "," + p.getTitulo() + "," + p.getDescripcion() + "," +
                            p.getNombreUnidad() + "," + p.getDuracionDias() + "," +
                            p.getFechaInicio().format(formatter) + "," + p.getNombreEmpresa() + "," +
                            p.getNombreGuia() + "," + (p.isPoseeRemuneracion() ? "Si" : "No"));
                }
            }
        }
    }

    @Override
    public boolean registrarUsuario(String nombre, String rut, String correo, String contrasenia) {
        if (!ValidadorEntrada.validarTexto(nombre, 3, 255)) {
            StdOut.println("Error: El nombre debe tener entre 3 y 255 caracteres.");
            return false;
        }

        rut = rut.replace(".", "").replace("-", "");

        if (!ValidadorEntrada.validarRut(rut)) {
            StdOut.println("Error: RUT inválido.");
            return false;
        }

        if (!ValidadorEntrada.validarCorreo(correo)) {
            StdOut.println("Error: Correo inválido.");
            return false;
        }

        if (!ValidadorEntrada.validarTexto(contrasenia, 6, 255)) {
            StdOut.println("Error: La contraseña debe tener entre 6 y 255 caracteres.");
            return false;
        }

        Usuario nuevoUsuario = new Usuario(nombre, rut, correo, contrasenia);

        if (contenedorUsuarios.contiene(nuevoUsuario)) {
            StdOut.println("Error: Ya existe un usuario con ese RUT.");
            return false;
        }

        // Verificar correo único
        for (int i = 0; i < contenedorUsuarios.tamanio(); i++) {
            Usuario u = (Usuario) contenedorUsuarios.obtener(i);
            if (u.getCorreo().equals(correo)) {
                StdOut.println("Error: Ya existe un usuario con ese correo.");
                return false;
            }
        }

        contenedorUsuarios.agregar(nuevoUsuario);
        StdOut.println("Usuario registrado exitosamente.");
        return true;
    }

    @Override
    public boolean iniciarSesion(String rut, String contrasenia) {
        rut = rut.replace(".", "").replace("-", "");

        for (int i = 0; i < contenedorUsuarios.tamanio(); i++) {
            Usuario usuario = (Usuario) contenedorUsuarios.obtener(i);
            if (usuario.getRut().equals(rut) && usuario.verificarContrasenia(contrasenia)) {
                usuarioActual = usuario;
                StdOut.println("Sesión iniciada exitosamente. Bienvenido " + usuarioActual.getNombre());
                return true;
            }
        }
        StdOut.println("Error: RUT o contraseña incorrectos.");
        return false;
    }

    @Override
    public void ingresarOferta() throws IOException {
        StdOut.println("\n=== INGRESAR NUEVA OFERTA ===");
        StdOut.println("[1] Ayudantía");
        StdOut.println("[2] Capstone");
        StdOut.println("[3] Práctica pre-profesional");

        int tipoOferta = ValidadorEntrada.validarEnteros("Seleccione el tipo de oferta");

        String id;
        do {
            id = ValidadorEntrada.validarString("Ingrese ID de la oferta");
            if (buscarOfertaPorId(id) != null) {
                StdOut.println("Error: Ya existe una oferta con ese ID. Intente con otro.");
            }
        } while (buscarOfertaPorId(id) != null);

        String titulo = ValidadorEntrada.validarString("Ingrese título");
        String descripcion = ValidadorEntrada.validarString("Ingrese descripción");
        String nombreUnidad = ValidadorEntrada.validarString("Ingrese nombre de la unidad");
        int duracionDias = ValidadorEntrada.validarEnteros("Ingrese duración en días");

        if (!ValidadorEntrada.validarTexto(titulo, 8, 255)) {
            StdOut.println("Error: El título debe tener entre 8 y 255 caracteres.");
            return;
        }
        if (!ValidadorEntrada.validarTexto(descripcion, 8, 255)) {
            StdOut.println("Error: La descripción debe tener entre 8 y 255 caracteres.");
            return;
        }
        if (!ValidadorEntrada.validarDuracion(duracionDias)) {
            StdOut.println("Error: La duración debe ser mayor a 1 día.");
            return;
        }

        switch (tipoOferta) {
            case 1: // Ayudantía
                String asignatura = ValidadorEntrada.validarString("Ingrese nombre de la asignatura");
                String rol = ValidadorEntrada.validarString("Ingrese rol del ayudante");
                int horas = ValidadorEntrada.validarEnteros("Ingrese horas semanales");
                double promedio = ValidadorEntrada.validarDouble("Ingrese promedio mínimo");
                String tipo = ValidadorEntrada.validarString("Ingrese tipo de ayudantía (taller/ayuda en clases)");

                if (!ValidadorEntrada.validarTexto(asignatura, 6, 255) ||
                        !ValidadorEntrada.validarTexto(rol, 6, 255)) {
                    StdOut.println("Error: Asignatura y rol deben tener entre 6 y 255 caracteres.");
                    return;
                }
                if (!ValidadorEntrada.validarHoras(horas)) {
                    StdOut.println("Error: Las horas semanales deben ser mayores a 2.");
                    return;
                }
                if (!ValidadorEntrada.validarPromedio(promedio)) {
                    StdOut.println("Error: El promedio debe estar entre 4.0 y 7.0.");
                    return;
                }
                if (!ValidadorEntrada.validarTipoAyudantia(tipo)) {
                    StdOut.println("Error: El tipo debe ser 'taller' o 'ayuda en clases'.");
                    return;
                }

                Ayudantia ayudantia = new Ayudantia(id, titulo, descripcion, nombreUnidad, duracionDias,
                        asignatura, rol, horas, promedio, tipo);
                contenedorOfertas.agregar(ayudantia);
                break;

            case 2: // Capstone
                String fechaStr = ValidadorEntrada.validarString("Ingrese fecha de inicio (dd-MM-yyyy)");
                LocalDate fechaInicio;
                try {
                    fechaInicio = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                } catch (DateTimeParseException e) {
                    StdOut.println("Error: Formato de fecha inválido. Use dd-MM-yyyy");
                    return;
                }

                String empresa = ValidadorEntrada.validarString("Ingrese nombre de la empresa");
                String guia = ValidadorEntrada.validarString("Ingrese nombre del guía");
                String tipoProyecto = ValidadorEntrada.validarString("Ingrese tipo de proyecto (Capstone/Investigacion/Memoria)");
                int duracionMeses = ValidadorEntrada.validarEnteros("Ingrese duración en meses (6, 12 o 18)");
                String carrerasStr = ValidadorEntrada.validarString("Ingrese carreras (separadas por coma)");
                String[] carreras = carrerasStr.split(",");
                int cantidadMin = ValidadorEntrada.validarEnteros("Ingrese cantidad mínima de estudiantes (2-5)");

                if (!ValidadorEntrada.validarTipoProyecto(tipoProyecto)) {
                    StdOut.println("Error: Tipo de proyecto debe ser Capstone, Investigacion o Memoria.");
                    return;
                }
                if (!ValidadorEntrada.validarDuracionMeses(duracionMeses)) {
                    StdOut.println("Error: La duración debe ser 6, 12 o 18 meses.");
                    return;
                }
                if (!ValidadorEntrada.validarTexto(carrerasStr, 8, 255)) {
                    StdOut.println("Error: Las carreras deben tener entre 8 y 255 caracteres.");
                    return;
                }
                if (!ValidadorEntrada.validarCantidadEstudiantes(cantidadMin)) {
                    StdOut.println("Error: La cantidad mínima debe estar entre 2 y 5 estudiantes.");
                    return;
                }

                Capstone capstone = new Capstone(id, titulo, descripcion, nombreUnidad, duracionDias,
                        fechaInicio, empresa, guia, tipoProyecto, duracionMeses, carreras, cantidadMin);
                contenedorOfertas.agregar(capstone);
                break;

            case 3: // Práctica
                String fechaStr2 = ValidadorEntrada.validarString("Ingrese fecha de inicio (dd-MM-yyyy)");
                LocalDate fechaInicio2;
                try {
                    fechaInicio2 = LocalDate.parse(fechaStr2, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                } catch (DateTimeParseException e) {
                    StdOut.println("Error: Formato de fecha inválido. Use dd-MM-yyyy");
                    return;
                }

                String empresa2 = ValidadorEntrada.validarString("Ingrese nombre de la empresa");
                String guia2 = ValidadorEntrada.validarString("Ingrese nombre del guía");
                StdOut.println("¿Posee remuneración? [1] Sí [2] No");
                int remuneracion = ValidadorEntrada.validarEnteros("Seleccione opción");

                if (remuneracion != 1 && remuneracion != 2) {
                    StdOut.println("Error: Debe seleccionar 1 (Sí) o 2 (No).");
                    return;
                }

                boolean tieneRemuneracion = remuneracion == 1;

                Practica practica = new Practica(id, titulo, descripcion, nombreUnidad, duracionDias,
                        fechaInicio2, empresa2, guia2, tieneRemuneracion);
                contenedorOfertas.agregar(practica);
                break;

            default:
                StdOut.println("Opción no válida.");
                return;
        }

        StdOut.println("Oferta agregada exitosamente.");
    }

    private Oferta buscarOfertaPorId(String id) {
        for (int i = 0; i < contenedorOfertas.tamanio(); i++) {
            Oferta oferta = (Oferta) contenedorOfertas.obtener(i);
            if (oferta.getId().equals(id)) {
                return oferta;
            }
        }
        return null;
    }

    private Oferta buscarOfertaPorTitulo(String titulo) {
        for (int i = 0; i < contenedorOfertas.tamanio(); i++) {
            Oferta oferta = (Oferta) contenedorOfertas.obtener(i);
            if (oferta.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                return oferta;
            }
        }
        return null;
    }

    @Override
    public void buscarOferta() throws IOException {
        StdOut.println("\n=== BUSCAR OFERTA ===");
        StdOut.println("[1] Buscar por ID");
        StdOut.println("[2] Buscar por título");

        int opcion = ValidadorEntrada.validarEnteros("Seleccione opción");
        Oferta ofertaEncontrada = null;

        switch (opcion) {
            case 1:
                String id = ValidadorEntrada.validarString("Ingrese ID de la oferta");
                ofertaEncontrada = buscarOfertaPorId(id);
                break;
            case 2:
                String titulo = ValidadorEntrada.validarString("Ingrese título de la oferta");
                ofertaEncontrada = buscarOfertaPorTitulo(titulo);
                break;
            default:
                StdOut.println("Opción no válida.");
                return;
        }

        if (ofertaEncontrada != null) {
            StdOut.println("\n=== OFERTA ENCONTRADA ===");
            StdOut.println(ofertaEncontrada.toString());
        } else {
            StdOut.println("No se encontró la oferta.");
        }
    }

    @Override
    public void verOfertas() throws IOException {
        StdOut.println("\n=== LISTADO DE OFERTAS ===");

        if (contenedorOfertas.isVacia()) {
            StdOut.println("No hay ofertas registradas.");
            return;
        }

        for (int i = 0; i < contenedorOfertas.tamanio(); i++) {
            Oferta oferta = (Oferta) contenedorOfertas.obtener(i);
            StdOut.println("\n--- Oferta " + (i + 1) + " ---");
            StdOut.println("ID: " + oferta.getId());
            StdOut.println("Título: " + oferta.getTitulo());
            StdOut.println("Tipo: " + oferta.getClass().getSimpleName());
            StdOut.println("Descripción: " + oferta.getDescripcion());
            StdOut.println("Unidad: " + oferta.getNombreUnidad());
            StdOut.println("Duración: " + oferta.getDuracionDias() + " días");
        }
    }

    @Override
    public void editarOferta() throws IOException {
        StdOut.println("\n=== EDITAR OFERTA ===");
        String id = ValidadorEntrada.validarString("Ingrese ID de la oferta a editar");

        Oferta ofertaActual = buscarOfertaPorId(id);
        if (ofertaActual == null) {
            StdOut.println("No se encontró la oferta con ID: " + id);
            return;
        }

        StdOut.println("Oferta actual: " + ofertaActual.toString());

        contenedorOfertas.eliminar(ofertaActual);

        StdOut.println("\nIngrese los nuevos datos:");
        ingresarOferta();
    }

    @Override
    public void eliminarOferta() throws IOException {
        StdOut.println("\n=== ELIMINAR OFERTA ===");
        String id = ValidadorEntrada.validarString("Ingrese ID de la oferta a eliminar");

        Oferta oferta = buscarOfertaPorId(id);
        if (oferta == null) {
            StdOut.println("No se encontró la oferta con ID: " + id);
            return;
        }

        StdOut.println("Oferta a eliminar: " + oferta.toString());

        StdOut.println("¿Está seguro que desea eliminar esta oferta? [1] Sí [2] No");
        int confirmacion = ValidadorEntrada.validarEnteros("Seleccione opción");

        if (confirmacion == 1) {
            if (contenedorOfertas.eliminar(oferta)) {
                StdOut.println("Oferta eliminada exitosamente.");
            } else {
                StdOut.println("Error al eliminar la oferta.");
            }
        } else {
            StdOut.println("Eliminación cancelada.");
        }
    }

    @Override
    public void verPerfil() throws IOException {
        StdOut.println("\n=== PERFIL DE USUARIO ===");
        StdOut.println("Nombre: " + usuarioActual.getNombre());
        StdOut.println("RUT: " + usuarioActual.getRut());
        StdOut.println("Correo: " + usuarioActual.getCorreo());
    }

    @Override
    public void editarPerfil() throws IOException {
        StdOut.println("\n=== EDITAR PERFIL ===");

        String nuevoNombre = ValidadorEntrada.validarString("Ingrese nuevo nombre");
        String nuevoCorreo = ValidadorEntrada.validarString("Ingrese nuevo correo");

        if (!ValidadorEntrada.validarTexto(nuevoNombre, 3, 255)) {
            StdOut.println("Error: El nombre debe tener entre 3 y 255 caracteres.");
            return;
        }

        if (!ValidadorEntrada.validarCorreo(nuevoCorreo)) {
            StdOut.println("Error: Correo inválido.");
            return;
        }

        // Verificar que el correo sea único (excepto el actual)
        for (int i = 0; i < contenedorUsuarios.tamanio(); i++) {
            Usuario u = (Usuario) contenedorUsuarios.obtener(i);
            if (!u.getRut().equals(usuarioActual.getRut()) && u.getCorreo().equals(nuevoCorreo)) {
                StdOut.println("Error: Ya existe un usuario con ese correo.");
                return;
            }
        }

        usuarioActual.setNombre(nuevoNombre);
        usuarioActual.setCorreo(nuevoCorreo);
        StdOut.println("Perfil actualizado exitosamente.");
    }

    @Override
    public void cerrarSesion() throws IOException {
        try {
            guardarArchivoUsuarios();
            guardarArchivoOfertas();
            StdOut.println("Datos guardados exitosamente.");
        } catch (IOException e) {
            StdOut.println("Error al guardar datos: " + e.getMessage());
        }

        usuarioActual = null;
        StdOut.println("Sesión cerrada exitosamente.");
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
}
