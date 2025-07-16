package com.ebac.modulo59;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

public class Contexto {
    public void consultarPasajeros() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Obtener conexión
            connection = MysqlConnection.getConnection();

            // Crear statement
            statement = connection.createStatement();

            // Ejecutar consulta SELECT
            String sql = "SELECT * FROM pasajeros";
            resultSet = statement.executeQuery(sql);

            // Procesar resultados
            System.out.println("=== LISTADO DE PASAJEROS ===");
            System.out.println("--------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-12s | %-20s | %-20s | %-15s | %-6s | %-15s | %-20s |\n",
                    "ID", "NOMBRE", "APELLIDO", "FECHA NAC.", "GÉNERO", "NACIONALIDAD", "PASAAPORTE");
            System.out.println("--------------------------------------------------------------------------------------------------------");

            while (resultSet.next()) {
                int id = resultSet.getInt("id_pasajero");
                String pasaporte = resultSet.getString("numero_pasaporte");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String fechaNac = resultSet.getDate("fecha_nacimiento").toLocalDate()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String genero = resultSet.getString("genero");
                String nacionalidad = resultSet.getString("nacionalidad");

                System.out.printf("| %-12d | %-20s | %-20s | %-15s | %-6s | %-15s | %-20s |\n",
                        id, nombre, apellido, fechaNac, genero, nacionalidad, pasaporte);
            }
            System.out.println("--------------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.err.println("Error al ejecutar consulta: " + e.getMessage());
        } finally {
            // Cerrar recursos
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                MysqlConnection.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
}