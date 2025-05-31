package cl.ucn.disc.pa.taller3.utilidades;

import ucn.StdIn;
import ucn.StdOut;

public class ValidadorEntrada {

    public static int validarEnteros(String mensaje) {
        StdOut.print(mensaje + ": ");
        String input = StdIn.readString();

        while (!esEntero(input)) {
            StdOut.println("Error: Debe ingresar un numero entero.");
            StdOut.print(mensaje + ": ");
            input = StdIn.readString();
        }

        return Integer.parseInt(input);
    }

    public static double validarDouble(String mensaje) {
        StdOut.print(mensaje + ": ");
        String input = StdIn.readString();

        while (!esDouble(input)) {
            StdOut.println("Error: Debe ingresar un numero decimal.");
            StdOut.print(mensaje + ": ");
            input = StdIn.readString();
        }

        return Double.parseDouble(input);
    }

    public static String validarString(String mensaje) {
        StdOut.print(mensaje + ": ");
        String input = StdIn.readString().trim();
        while (input.isEmpty()) {
            StdOut.println("Error: No puede estar vacio.");
            StdOut.print(mensaje + ": ");
            input = StdIn.readString().trim();
        }
        return input;
    }

    // Método auxiliar para verificar si un string es un entero
    private static boolean esEntero(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Método auxiliar para verificar si un string es un double
    private static boolean esDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validarRut(String rut) {
        if (rut == null || rut.length() != 9) {
            return false;
        }

        try {
            String numero = rut.substring(0, 8);
            char dv = rut.charAt(8);

            // Verificar que los primeros 8 caracteres sean dígitos
            for (char c : numero.toCharArray()) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }

            int suma = 0;
            int multiplicador = 2;

            for (int i = numero.length() - 1; i >= 0; i--) {
                suma += Character.getNumericValue(numero.charAt(i)) * multiplicador;
                multiplicador = multiplicador == 7 ? 2 : multiplicador + 1;
            }

            int resto = suma % 11;
            char dvCalculado = resto == 0 ? '0' : resto == 1 ? 'K' : (char) ('0' + (11 - resto));

            return Character.toUpperCase(dv) == Character.toUpperCase(dvCalculado);

        } catch (Exception e) {
            return false;
        }
    }

    public static boolean validarCorreo(String correo) {
        return correo.contains("@") && correo.contains(".");
    }

    public static boolean validarTexto(String texto, int minLength, int maxLength) {
        return texto != null && texto.length() >= minLength && texto.length() <= maxLength;
    }

    public static boolean validarPromedio(double promedio) {
        return promedio >= 4.0 && promedio <= 7.0;
    }

    public static boolean validarHoras(int horas) {
        return horas > 2;
    }

    public static boolean validarDuracion(int dias) {
        return dias > 1;
    }

    public static boolean validarDuracionMeses(int meses) {
        return meses == 6 || meses == 12 || meses == 18;
    }

    public static boolean validarCantidadEstudiantes(int cantidad) {
        return cantidad >= 2 && cantidad <= 5;
    }

    public static boolean validarTipoAyudantia(String tipo) {
        return tipo.equalsIgnoreCase("taller") || tipo.equalsIgnoreCase("ayuda en clases");
    }

    public static boolean validarTipoProyecto(String tipo) {
        return tipo.equalsIgnoreCase("capstone") ||
                tipo.equalsIgnoreCase("investigacion") ||
                tipo.equalsIgnoreCase("memoria");
    }
}