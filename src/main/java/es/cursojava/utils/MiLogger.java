package es.cursojava.utils;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MiLogger {

    private static final String RUTA_LOG = "app.log";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

 // Método privado y estático que obtiene el nombre simple de la clase que llamó al método 'log' (u otro método)
 // Es útil para fines de registro o debug, para saber quién hizo la llamada.
    private static String obtenerClaseLlamante() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (StackTraceElement e : stack) {
            String cn = e.getClassName();
            if (!cn.equals(MiLogger.class.getName()) && !cn.equals(Thread.class.getName())) {
                int p = cn.lastIndexOf('.');
                return (p >= 0) ? cn.substring(p + 1) : cn;
            }
        }
        return "Desconocida";
    }

    public static void log(String nivel, String mensaje) {
        String timestamp = LocalDateTime.now().format(FORMATO_FECHA);
        String clase = obtenerClaseLlamante();
        String logEntry = String.format("%s [%s] (%s): %s", timestamp, nivel, clase, mensaje);

        System.out.println(logEntry);

        try (FileWriter fw = new FileWriter(RUTA_LOG, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(logEntry);
        } catch (IOException e) {
            System.err.println("Error al escribir el log: " + e.getMessage());
        }
    }

    public static void info(String mensaje) {
        log("INFO", mensaje);
    }

    public static void warning(String mensaje) {
        log("WARNING", mensaje);
    }

    public static void error(String mensaje) {
        log("ERROR", mensaje);
    }
    
 // Variantes con formato tipo printf
    public static void infof(String fmt, Object... args) {
        log("INFO", String.format(fmt, args));
    }
    public static void warningf(String fmt, Object... args) {
        log("WARNING", String.format(fmt, args));
    }
    public static void errorf(String fmt, Object... args) {
        log("ERROR", String.format(fmt, args));
    }
}


