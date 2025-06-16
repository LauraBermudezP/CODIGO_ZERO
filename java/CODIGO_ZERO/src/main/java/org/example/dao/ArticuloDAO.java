package org.example.dao;

import org.example.model.Articulo;
import org.example.database.DBconnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDAO {

    // CREATE - insertar

    public void crearArticulo(Articulo articulo) {
        String sql = "INSERT INTO articulos (titulo, contenidoArticulo, tipoArticulo, idAutor, idCategoria, fechaCreacionArticulo) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBconnection.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, articulo.getTitulo());
            stmt.setString(2, articulo.getContenidoArticulo());
            stmt.setString(3, articulo.getTipoArticulo());
            stmt.setInt(4, articulo.getIdAutor());
            stmt.setInt(5, articulo.getIdCategoria());
            stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));

            stmt.executeUpdate();
            System.out.println("✅📚 Artículo agregado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("🟥 Error al insertar artículo: " + e.getMessage());
        }
    }

    // READ - obtener

    public Articulo obtenerArticuloPorId(int id) {
        String sql = "SELECT * FROM articulos WHERE idArticulo = ?";
        Articulo articulo = null;

        try (Connection conn = DBconnection.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                articulo = new Articulo(
                        rs.getInt("idArticulo"),
                        rs.getString("titulo"),
                        rs.getString("contenidoArticulo"),
                        rs.getString("tipoArticulo"),
                        rs.getInt("idAutor"),
                        rs.getInt("idCategoria"),
                        rs.getTimestamp("fechaCreacionArticulo").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("🟥 Error al obtener el artículo: " + e.getMessage());
        }
        return articulo;
    }

    // READ - listar TODOS

    public List<Articulo> obtenerTodosLosArticulos() {
        String sql = "SELECT * FROM articulos";
        List<Articulo> listaArticulos = new ArrayList<>();

        try (Connection conn = DBconnection.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Articulo articulo = new Articulo(
                        rs.getInt("idArticulo"),
                        rs.getString("titulo"),
                        rs.getString("contenidoArticulo"),
                        rs.getString("tipoArticulo"),
                        rs.getInt("idAutor"),
                        rs.getInt("idCategoria"),
                        rs.getTimestamp("fechaCreacionArticulo").toLocalDateTime()
                );
                listaArticulos.add(articulo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("🟥 Error al obtener todos los artículos: " + e.getMessage());
        }
        return listaArticulos;
    }

    // UPDATE - actualizar

    public void actualizarArticulo(Articulo articulo) {
        String sql = "UPDATE articulos SET titulo = ?, contenidoArticulo = ?, tipoArticulo = ?, idAutor = ?, idCategoria = ? WHERE idArticulo = ?";

        try (Connection conn = DBconnection.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, articulo.getTitulo());
            stmt.setString(2, articulo.getContenidoArticulo());
            stmt.setString(3, articulo.getTipoArticulo());
            stmt.setInt(4, articulo.getIdAutor());
            stmt.setInt(5, articulo.getIdCategoria());
            stmt.setInt(6, articulo.getIdArticulo());

            stmt.executeUpdate();
            System.out.println("✅🆕 Artículo actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("🟥 Error al actualizar artículo: " + e.getMessage());
        }
    }

    // DELETE - eliminar

    public void eliminarArticulo(int id) {
        String sql = "DELETE FROM articulos WHERE idArticulo = ?";

        try (Connection conn = DBconnection.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("✅🔴 Artículo eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("🟥 Error al eliminar artículo: " + e.getMessage());
        }
    }
}
