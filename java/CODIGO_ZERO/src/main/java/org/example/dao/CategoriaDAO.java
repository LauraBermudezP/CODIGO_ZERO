package org.example.dao;

import org.example.model.Categoria;
import org.example.database.DBconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    // CREATE - insertar

    public void crearCategoria(Categoria categoria) {
        String sql = "INSERT INTO categorias (nombreCategoria, descripcion) VALUES (?, ?)";

        try (Connection conn = DBconnection.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNombreCategoria());
            stmt.setString(2, categoria.getDescripcion());

            stmt.executeUpdate();
            System.out.println("✅📚 Categoría agregada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("🟥 Error al insertar categoría: " + e.getMessage());
        }
    }

    // READ - obtener

    public Categoria obtenerCategoriaPorId(int id) {
        String sql = "SELECT * FROM categorias WHERE idCategoria = ?";
        Categoria categoria = null;

        try (Connection conn = DBconnection.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                categoria = new Categoria(
                        rs.getInt("idCategoria"),
                        rs.getString("nombreCategoria"),
                        rs.getString("descripcion")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("🟥 Error al obtener la categoría: " + e.getMessage());
        }
        return categoria;
    }

    // READ - listar TODAS

    public List<Categoria> obtenerTodasLasCategorias() {
        String sql = "SELECT * FROM categorias";
        List<Categoria> listaCategorias = new ArrayList<>();

        try (Connection conn = DBconnection.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Categoria categoria = new Categoria(
                        rs.getInt("idCategoria"),
                        rs.getString("nombreCategoria"),
                        rs.getString("descripcion")
                );
                listaCategorias.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("🟥 Error al obtener todas las categorías: " + e.getMessage());
        }
        return listaCategorias;
    }

    // UPDATE - actualizar

    public void actualizarCategoria(Categoria categoria) {
        String sql = "UPDATE categorias SET nombreCategoria = ?, descripcion = ? WHERE idCategoria = ?";

        try (Connection conn = DBconnection.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNombreCategoria());
            stmt.setString(2, categoria.getDescripcion());
            stmt.setInt(3, categoria.getIdCategoria());

            stmt.executeUpdate();
            System.out.println("✅🆕 Categoría actualizada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("🟥 Error al actualizar categoría: " + e.getMessage());
        }
    }

    // DELETE - eliminar

    public void eliminarCategoria(int id) {
        String sql = "DELETE FROM categorias WHERE idCategoria = ?";

        try (Connection conn = DBconnection.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("✅🔴 Categoría eliminada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("🟥 Error al eliminar categoría: " + e.getMessage());
        }
    }
}
