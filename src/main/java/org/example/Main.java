package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        // Datos de conexión a la base de datos
        String url = "jdbc:mysql://host:port/db";
        String username = "";  // Cambia el usuario según tu configuración
        String password = "";  // Cambia la contraseña según tu configuración

        // Ruta del archivo Excel
        String filePath = "src\\main\\java\\org\\example\\a.xlsx";  // Cambia a la ruta de tu archivo

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Abrir el archivo Excel
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);  // Suponiendo que los datos están en la primera hoja

            // Iterar sobre las filas del archivo Excel
            for (Row row : sheet) {
                // Saltar la fila de encabezado (si la hay)
                if (row.getRowNum() == 0) {
                    continue;
                }

                // Obtener los valores de las columnas ENTIDAD, MUNICIPIO, PLANTEL
                String entidad = row.getCell(0).getStringCellValue();
                String municipio = row.getCell(1).getStringCellValue();
                String plantel = row.getCell(3).getStringCellValue();

                // Insertar en la tabla "estado" si no existe
                int estadoId = insertEstadoIfNotExists(connection, entidad);

                // Insertar en la tabla "municipio" si no existe
                int municipioId = insertMunicipioIfNotExists(connection, municipio, estadoId);

                // Insertar en la tabla "prepa"
                insertPrepaIfNotExists(connection, plantel, municipioId);
            }

            workbook.close();
            System.out.println("Datos insertados correctamente.");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Insertar entidad en la tabla "estado" si no existe
    private static int insertEstadoIfNotExists(Connection connection, String entidad) throws SQLException {
        String selectQuery = "SELECT id_estado FROM estado WHERE estado = ?";
        try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
            selectStmt.setString(1, entidad);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_estado");  // Ya existe, retornar el id
            } else {
                // Insertar nueva entidad
                String insertQuery = "INSERT INTO estado (estado,status) VALUES (?,1)";
                try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                    insertStmt.setString(1, entidad);
                    insertStmt.executeUpdate();

                    // Obtener el ID generado
                    ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        }
        return -1;  // Si no se encuentra, retornamos un valor por defecto
    }

    // Insertar municipio en la tabla "municipio" si no existe
    private static int insertMunicipioIfNotExists(Connection connection, String municipio, int estadoId) throws SQLException {
        String selectQuery = "SELECT id_municipio FROM municipio WHERE municipio = ? AND estado_id = ?";
        try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
            selectStmt.setString(1, municipio);
            selectStmt.setInt(2, estadoId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_municipio");  // Ya existe, retornar el id
            } else {
                // Insertar nuevo municipio
                String insertQuery = "INSERT INTO municipio (municipio, estado_id, status) VALUES (?, ?, 1)";
                try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                    insertStmt.setString(1, municipio);
                    insertStmt.setInt(2, estadoId);
                    insertStmt.executeUpdate();

                    // Obtener el ID generado
                    ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        }
        return -1;  // Si no se encuentra, retornamos un valor por defecto
    }

    // Insertar plantel en la tabla "prepa"
    // Método para insertar el plantel si no existe, asociado con el municipio
    public static void insertPrepaIfNotExists(Connection connection, String plantel, int municipioId) throws SQLException {
        String query = "SELECT id_prepa FROM prepa WHERE prepa = ? AND municipio_id = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(query)) {
            selectStatement.setString(1, plantel);
            selectStatement.setInt(2, municipioId);
            ResultSet resultSet = selectStatement.executeQuery();
            if (!resultSet.next()) {
                // Si no existe, inserta el plantel
                query = "INSERT INTO prepa (prepa, municipio_id, status) VALUES (?, ?, 1)";
                try (PreparedStatement insertStatement = connection.prepareStatement(query)) {
                    insertStatement.setString(1, plantel);
                    insertStatement.setInt(2, municipioId);
                    insertStatement.executeUpdate();
                }
            }
        }
    }
}