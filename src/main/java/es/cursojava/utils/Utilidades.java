package es.cursojava.utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Utilidades {
	private static final Scanner sc = new Scanner(System.in); // único Scanner

	//metodo calcularEdad al cual le pasamos un año y le devolvemos la edad apatir de ese año.
	
	public static int calcularEdad (int ano) {
		
		int añoActual = 2025;
		int resultado = añoActual - ano;
		return resultado;
		
	}
	
	public static void pintaMenu (String[] arrayStrings) {
		
		for (String palabra : arrayStrings) {
			System.out.println(palabra);
		}
		System.out.println("Introduce una opción");
		
	}
	public static void pintaMenu (String[] arrayStrings,String texto) {
		
		for (String palabra : arrayStrings) {
			System.out.println(palabra);
		}
		System.out.println(texto);
		
	}
	
	public static int pideDatoNumerico(String texto) {
		System.out.println(texto);
		Scanner scan = new Scanner(System.in);
		int num = scan.nextInt();
		return num;
	}
	
	public static String pideDatoString(String texto){
	    boolean ok = false;
	    Scanner sc = new Scanner(System.in);
	    String s = "";
	    while (!ok) {
	        MiLogger.info(texto);
	        s = sc.nextLine().trim();
	        if (s.isEmpty()) {
	            MiLogger.info("No puede estar vacío. Intenta de nuevo.");
	        } else if (s.matches("\\d+")) { // solo dígitos
	            MiLogger.info("No introduzcas solo números. Intenta de nuevo.");
	        } else {
	            ok = true;
	        }
	    }
	    MiLogger.info("→ " + s);
	    return s;
		
	}
	
    public static String pedirDato(String prompt) {
        MiLogger.info(prompt);
        Scanner scan = new Scanner(System.in);
        String s = sc.nextLine().trim();
        MiLogger.info("→ " + s);
        return s;
    }

    public static int pedirEntero(String prompt) {
        boolean ok = false;
        int n = 0;

        while (!ok) {
            MiLogger.info(prompt);
            
            try {
                n = sc.nextInt();
                ok = true;                    // salida del bucle
            } catch (NumberFormatException e) {
                MiLogger.info("Número inválido. Intenta de nuevo.");
            }
        }
        MiLogger.info("→ " + n);
        return n;
    }
    
    public static double pedirDecimal(String prompt) {
        boolean ok = false;
        double n = 0.0;
        Scanner sc = new Scanner(System.in);
        while (!ok) {
            MiLogger.info(prompt);
            try {
                n = sc.nextDouble();     // depende del locale
                ok = true;
            } catch (InputMismatchException e) {
                MiLogger.info("Número inválido. Intenta de nuevo.");
                sc.next();               // descarta el token incorrecto
            } finally {
                if (sc.hasNextLine()) sc.nextLine(); // limpia el fin de línea
            }
        }
        MiLogger.info("→ " + n);
        return n;
    }
    // Pregunta hasta recibir s/n válidos
    public static boolean pedirConfirmacion(String prompt) {
        while (true) {
            String r = pedirDato(prompt + " (s/n): ");
            String t = r.trim().toLowerCase();
            if (t.equals("s") || t.equals("si") || t.equals("sí")) return true;
            if (t.equals("n") || t.equals("no")) return false;
            MiLogger.info("Responde con 's' o 'n', por favor.");
        }
    }

	
}


