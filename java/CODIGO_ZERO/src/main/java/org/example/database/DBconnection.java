package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    private static final String url = "jdbc:mysql://localhost:3306/codigo_zero";
    private static final String user = "root";
    private static final String password = "";

    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Carga el driver
            return DriverManager.getConnection(url, user, password); // Retorna la conexión válida
        } catch (ClassNotFoundException e) {
            System.out.println("🟥 Error: Driver no encontrado - " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("🟥 Error de conexión: " + e.getMessage());
        }
        return null; // Retorna null si algo falla en la conexión
    }
}