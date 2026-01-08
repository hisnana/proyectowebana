package es.cursojava.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {
    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";
        String user = "getafe";
        String password = "password";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Conectado con éxito a Oracle");
            conn.close();
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}