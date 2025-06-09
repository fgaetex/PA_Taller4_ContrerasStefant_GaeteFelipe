package cl.ucn.disc.pa.taller3.servicios;

import cl.ucn.disc.pa.taller3.dominio.*;
import cl.ucn.disc.pa.taller3.utilidades.ValidadorEntrada;
import ucn.StdIn;
import ucn.StdOut;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SistemaOfertasUCN implements SistemaOfertas {
    private ContenedorOfertas contenedorOfertas;
    private Usuario usuarioActual;
    private Usuario[] usuarios;
    private int cantidadUsuarios;
    private String archivoOfertas;

    public SistemaOfertasUCN() {
        this.contenedorOfertas = new ContenedorOfertas(100);
        this.usuarios = new Usuario[50];
        this.cantidadUsuarios = 0;
        this.archivoOfertas = "OfertasAcademicas.txt";
        try {
            leerArchivoOfertas();
        } catch (IOException e) {
            StdOut.println("Error al cargar ofertas: " + e.getMessage());
        }
    }
    
    /**
     * Se registra las ofertas del archivo "OfertasAcademicas.txt" en el programa.
     * Si el archivo no existe se muestra un mensaje de error.
     *
     * @throws IOException Si ocurre un error al acceder o leer el archivo.
     *
     */

    // Lectura del archivo de ofertas
    private void leerArchivoOfertas() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(archivoOfertas))) {
            String linea;
            br.readLine(); // Saltar primera linea de encabezado

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos[0].equals("A")) {
                    // Ayudantia: A,001,Asistente de Laboratorio,Apoyo en el laboratorio,DIE,90,Electrotécnia,Ayudante de Laboratorio,10,5.5
                    Ayudantia ayudantia = new Ayudantia(
                            datos[1], datos[2], datos[3], datos[4],
                            Integer.parseInt(datos[5]), datos[6], datos[7],
                            Integer.parseInt(datos[8]), Double.parseDouble(datos[9]), datos[7]
                    );
                    contenedorOfertas.agregar(ayudantia);

                } else if (datos[0].equals("C")) {
                    // Capstone: C,003,Sistema de Monitoreo,Desarrollo de sensores,DISC,180,01-08-2025,EcoTech Ltda.,Dr. Ricardo,Capstone,6,"Informatica",3
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
                    // Practica: P,004,Práctica Desarrollo Web,Apoyo en desarrollo,DISC,120,01-07-2025,SoftWeb Ltda.,Dra. Elena,No
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
    
    /**
     * Registra un nuevo usuario en el sistema después de realizar una serie de validaciones sobre los datos ingresados.
     * También verifica que no exista ya un usuario con el mismo RUT o correo y que haya espacio disponible en el arreglo de usuarios.
     *
     * @param nombre       El nombre del usuario.
     * @param rut          El RUT del usuario.
     * @param correo       El correo electrónico del usuario.
     * @param contrasenia  La contraseña del usuario.
     */

    public boolean registrarUsuario(String nombre, String rut, String correo, String contrasenia) {
        // Validaciones
        if (!ValidadorEntrada.validarTexto(nombre, 3, 255)) {
            StdOut.println("Error: El nombre debe tener entre 3 y 255 caracteres.");
            return false;
        }

        // Formatear RUT (remover puntos y guión si los tiene)
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

        // Verificar que RUT y correo sean únicos
        for (int i = 0; i < cantidadUsuarios; i++) {
            if (usuarios[i].getRut().equals(rut)) {
                StdOut.println("Error: Ya existe un usuario con ese RUT.");
                return false;
            }
            if (usuarios[i].getCorreo().equals(correo)) {
                StdOut.println("Error: Ya existe un usuario con ese correo.");
                return false;
            }
        }

        if (cantidadUsuarios >= usuarios.length) {
            StdOut.println("Error: No se pueden registrar más usuarios.");
            return false;
        }

        usuarios[cantidadUsuarios] = new Usuario(nombre, rut, correo, contrasenia);
        cantidadUsuarios++;
        StdOut.println("Usuario registrado exitosamente.");
        return true;
    }
    
    /**
     * Verifica el inicio de sesión mediante la comprobación de su rut y contraseña y retorna el perfil en caso de existir
     *
     * @param rut El rut del usuario.
     * @param contrasenia La contraseña del usuario.
     * @return true Si el usuario es correcto.
     */

    public boolean iniciarSesion(String rut, String contrasenia) {
        // Formatear RUT
        rut = rut.replace(".", "").replace("-", "");

        for (int i = 0; i < cantidadUsuarios; i++) {
            if (usuarios[i].getRut().equals(rut) && usuarios[i].verificarContrasenia(contrasenia)) {
                usuarioActual = usuarios[i];
                StdOut.println("Sesión iniciada exitosamente. Bienvenido " + usuarioActual.getNombre());
                return true;
            }
        }
        StdOut.println("Error: RUT o contraseña incorrectos.");
        return false;
    }
    /**
     * Se ingresa una nueva oferta académica que puede ser Ayudantía, Capstone o Practica pre-profesional.
     * Si el ID existe se muestra un mensaje de error.
     *
     * @throws IOException Si ocurre un error al interactuar con el usuario.
     *
     */

    @Override
    public void ingresarOferta() throws IOException {
        StdOut.println("\n=== INGRESAR NUEVA OFERTA ===");
        StdOut.println("[1] Ayudantía");
        StdOut.println("[2] Capstone");
        StdOut.println("[3] Práctica pre-profesional");

        int tipoOferta = ValidadorEntrada.validarEnteros("Seleccione el tipo de oferta");

        // Verificar que el ID no exista
        String id;
        do {
            id = ValidadorEntrada.validarString("Ingrese ID de la oferta");
            if (contenedorOfertas.buscarPorId(id) != -1) {
                StdOut.println("Error: Ya existe una oferta con ese ID. Intente con otro.");
            }
        } while (contenedorOfertas.buscarPorId(id) != -1);

        String titulo = ValidadorEntrada.validarString("Ingrese título");
        String descripcion = ValidadorEntrada.validarString("Ingrese descripción");
        String nombreUnidad = ValidadorEntrada.validarString("Ingrese nombre de la unidad");
        int duracionDias = ValidadorEntrada.validarEnteros("Ingrese duración en días");

        // Validar datos comunes
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

                // Validaciones específicas de ayudantía
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

                // Validaciones específicas de capstone
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

     /**
     * Busca una oferta académica usando el ID o el título.
     * @throws IOException Si no se encuentra la fecha.
     *
     */

    @Override
    public void buscarOferta() throws IOException {
        StdOut.println("\n=== BUSCAR OFERTA ===");
        StdOut.println("[1] Buscar por ID");
        StdOut.println("[2] Buscar por título");

        int opcion = ValidadorEntrada.validarEnteros("Seleccione opción");
        int posicion = -1;

        switch (opcion) {
            case 1:
                String id = ValidadorEntrada.validarString("Ingrese ID de la oferta");
                posicion = contenedorOfertas.buscarPorId(id);
                break;
            case 2:
                String titulo = ValidadorEntrada.validarString("Ingrese título de la oferta");
                posicion = contenedorOfertas.buscarPorTitulo(titulo);
                break;
            default:
                StdOut.println("Opción no válida.");
                return;
        }

        if (posicion != -1) {
            Oferta oferta = contenedorOfertas.obtener(posicion);
            StdOut.println("\n=== OFERTA ENCONTRADA ===");
            StdOut.println(oferta.toString());
        } else {
            StdOut.println("No se encontró la oferta.");
        }
    }
    
    /**
     * Muestras todas las ofertas académicas registradas en el sistema.
     *
     * @throws IOException si ocurre un error de entrada/salida al mostrar los datos.
     *
     */

    @Override
    public void verOfertas() throws IOException {
        StdOut.println("\n=== LISTADO DE OFERTAS ===");

        if (contenedorOfertas.getCantidadActual() == 0) {
            StdOut.println("No hay ofertas registradas.");
            return;
        }

        for (int i = 0; i < contenedorOfertas.getCantidadActual(); i++) {
            Oferta oferta = contenedorOfertas.obtener(i);
            StdOut.println("\n--- Oferta " + (i + 1) + " ---");
            StdOut.println("ID: " + oferta.getId());
            StdOut.println("Título: " + oferta.getTitulo());
            StdOut.println("Tipo: " + oferta.getClass().getSimpleName());
            StdOut.println("Descripción: " + oferta.getDescripcion());
            StdOut.println("Unidad: " + oferta.getNombreUnidad());
            StdOut.println("Duración: " + oferta.getDuracionDias() + " días");
        }
    }
    
    /**
     * Permite editar una oferta académica a partir de su ID.
     *
     * @throws IOException Sí ocurre un error de entrada/salida al editar una oferta.
     *
     */

    @Override
    public void editarOferta() throws IOException {
        StdOut.println("\n=== EDITAR OFERTA ===");
        String id = ValidadorEntrada.validarString("Ingrese ID de la oferta a editar");

        int posicion = contenedorOfertas.buscarPorId(id);
        if (posicion == -1) {
            StdOut.println("No se encontró la oferta con ID: " + id);
            return;
        }

        Oferta ofertaActual = contenedorOfertas.obtener(posicion);
        StdOut.println("Oferta actual: " + ofertaActual.toString());

        // Eliminar la oferta actual
        contenedorOfertas.eliminar(id);

        // Crear nueva oferta con datos actualizados
        StdOut.println("\nIngrese los nuevos datos:");
        ingresarOferta();
    }
    
    /**
     * Permite eliminar una oferta académica a partir de su ID.
     *
     * @throws IOException Sí ocurre un error de entrada/salida al eliminar una oferta.
     *
     */

    @Override
    public void eliminarOferta() throws IOException {
        StdOut.println("\n=== ELIMINAR OFERTA ===");
        String id = ValidadorEntrada.validarString("Ingrese ID de la oferta a eliminar");

        int posicion = contenedorOfertas.buscarPorId(id);
        if (posicion == -1) {
            StdOut.println("No se encontró la oferta con ID: " + id);
            return;
        }

        Oferta oferta = contenedorOfertas.obtener(posicion);
        StdOut.println("Oferta a eliminar: " + oferta.toString());

        StdOut.println("¿Está seguro que desea eliminar esta oferta? [1] Sí [2] No");
        int confirmacion = ValidadorEntrada.validarEnteros("Seleccione opción");

        if (confirmacion == 1) {
            if (contenedorOfertas.eliminar(id)) {
                StdOut.println("Oferta eliminada exitosamente.");
            } else {
                StdOut.println("Error al eliminar la oferta.");
            }
        } else {
            StdOut.println("Eliminación cancelada.");
        }
    }
    
    /**
     * Despliega la información del usuario mostrando el nombre, rut y correo.
     *
     * @throws IOException Sí ocurre un error al mostrar el perfil.
     *
     */

    @Override
    public void verPerfil() throws IOException {
        StdOut.println("\n=== PERFIL DE USUARIO ===");
        StdOut.println("Nombre: " + usuarioActual.getNombre());
        StdOut.println("RUT: " + usuarioActual.getRut());
        StdOut.println("Correo: " + usuarioActual.getCorreo());
    }

     /**
     * Permite editar el perfil del usuario actual cambiando su nombre o correo.
     *
     * @throws IOException Sí ocurre un error de entrada/salida al editar el perfil.
     *
     */

    @Override
    public void editarPerfil() throws IOException {
        StdOut.println("\n=== EDITAR PERFIL ===");

        String nuevoNombre = ValidadorEntrada.validarString("Ingrese nuevo nombre");
        String nuevoCorreo = ValidadorEntrada.validarString("Ingrese nuevo correo");

        // Validaciones
        if (!ValidadorEntrada.validarTexto(nuevoNombre, 3, 255)) {
            StdOut.println("Error: El nombre debe tener entre 3 y 255 caracteres.");
            return;
        }

        if (!ValidadorEntrada.validarCorreo(nuevoCorreo)) {
            StdOut.println("Error: Correo inválido.");
            return;
        }

        // Verificar que el correo sea único (excepto el actual)
        for (int i = 0; i < cantidadUsuarios; i++) {
            if (!usuarios[i].getRut().equals(usuarioActual.getRut()) &&
                    usuarios[i].getCorreo().equals(nuevoCorreo)) {
                StdOut.println("Error: Ya existe un usuario con ese correo.");
                return;
            }
        }

        usuarioActual.setNombre(nuevoNombre);
        usuarioActual.setCorreo(nuevoCorreo);
        StdOut.println("Perfil actualizado exitosamente.");
    }
    
     /**
     * Se cierra la sesión actual.
     *
     * @throws IOException Sí ocurre un error al cerrar.
     *
     */


    @Override
    public void cerrarSesion() throws IOException {
        usuarioActual = null;
        StdOut.println("Sesión cerrada exitosamente.");
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
}
