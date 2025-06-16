package org.example.dao;

import org.example.model.Reporte;
import org.example.database.DBconnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReporteDAO {

    // CREATE - insertar

    public void crearReporte(Reporte reporte) {
        String sql = "INSERT INTO reportes (motivoReporte, fechaReporte, estadoReporte, idUsuario, idArticulo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBconnection.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, reporte.getMotivoReporte());
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now())); // Fecha automática
            stmt.setString(3, reporte.getEstadoReporte());
            stmt.setInt(4, reporte.getIdUsuario());
            stmt.setInt(5, reporte.getIdArticulo());

            stmt.executeUpdate();
            System.out.println("✅🚩 Reporte agregado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("🟥 Error al insertar reporte: " + e.getMessage());
        }
    }

    // READ - obtener

    public Reporte obtenerReportePorId(int id) {
        String sql = "SELECT * FROM reportes WHERE idReporte = ?";
        Reporte reporte = null;

        try (Connection conn = DBconnection.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                reporte = new Reporte(
                        rs.getInt("idReporte"),
                        rs.getString("motivoReporte"),
                        rs.getTimestamp("fechaReporte").toLocalDateTime(), // Convertir TIMESTAMP a LocalDateTime
                        rs.getString("estadoReporte"),
                        rs.getInt("idUsuario"),
                        rs.getInt("idArticulo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("🟥 Error al obtener el reporte: " + e.getMessage());
        }
        return reporte;
    }

    // READ - listar ALL

    public List<Reporte> obtenerTodosLosReportes() {
        String sql = "SELECT * FROM reportes";
        List<Reporte> listaReportes = new ArrayList<>();

        try (Connection conn = DBconnection.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Reporte reporte = new Reporte(
                        rs.getInt("idReporte"),
                        rs.getString("motivoReporte"),
                        rs.getTimestamp("fechaReporte").toLocalDateTime(),
                        rs.getString("estadoReporte"),
                        rs.getInt("idUsuario"),
                        rs.getInt("idArticulo")
                );
                listaReportes.add(reporte);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("🟥 Error al obtener todos los reportes: " + e.getMessage());
        }
        return listaReportes;
    }

    // UPDATE - actualizar

    public void actualizarReporte(Reporte reporte) {
        String sql = "UPDATE reportes SET motivoReporte = ?, estadoReporte = ?, idUsuario = ?, idArticulo = ? WHERE idReporte = ?";

        try (Connection conn = DBconnection.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, reporte.getMotivoReporte());
            stmt.setString(2, reporte.getEstadoReporte());
            stmt.setInt(3, reporte.getIdUsuario());
            stmt.setInt(4, reporte.getIdArticulo());
            stmt.setInt(5, reporte.getIdReporte());

            stmt.executeUpdate();
            System.out.println("✅🆕 Reporte actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("🟥 Error al actualizar reporte: " + e.getMessage());
        }
    }

    // DELETE - eliminar

    public void eliminarReporte(int id) {
        String sql = "DELETE FROM reportes WHERE idReporte = ?";

        try (Connection conn = DBconnection.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("✅🔴 Reporte eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("🟥 Error al eliminar reporte: " + e.getMessage());
        }
    }
}
