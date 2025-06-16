package org.example.dao;

import org.example.database.DBconnection;
import org.example.model.Usuario;
import org.example.model.UsuarioNormal;
import org.example.model.Administrador;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // CREATE - crear

    public void crearUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (username, correoUsuario, contrasenaUsuario, biografiaUsuario, fecha_registro, rolUsuario) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBconnection.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getCorreoUsuario());
            stmt.setString(3, usuario.getContrasenaUsuario());
            stmt.setString(4, usuario.getBiografiaUsuario());
            stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now())); // Fecha automática
            stmt.setString(6, usuario.getRolUsuario());

            stmt.executeUpdate();
            System.out.println("✅👤 Usuario agregado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("🟥 Error al insertar usuario: " + e.getMessage());
        }
    }

    // READ - obtener

    public Usuario obtenerUsuarioPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE idUsuario = ?";
        Usuario usuario = null;

        try (Connection conn = DBconnection.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String rol = rs.getString("rolUsuario");

                if (rol.equals("Administrador")) {
                    usuario = new Administrador(
                            rs.getString("username"),
                            rs.getInt("idUsuario"),
                            rs.getTimestamp("fecha_registro").toLocalDateTime(),
                            rs.getString("correoUsuario"),
                            rs.getString("contrasenaUsuario"),
                            rs.getString("biografiaUsuario"),
                            rol
                    );
                } else {
                    usuario = new UsuarioNormal(
                            rs.getString("username"),
                            rs.getInt("idUsuario"),
                            rs.getTimestamp("fecha_registro").toLocalDateTime(),
                            rs.getString("correoUsuario"),
                            rs.getString("contrasenaUsuario"),
                            rs.getString("biografiaUsuario"),
                            rol
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("🟥 Error al obtener el usuario: " + e.getMessage());
        }
        return usuario;
    }

    // READ - obtener TODOS

    public List<Usuario> obtenerTodosLosUsuarios() {
        String sql = "SELECT * FROM usuarios";
        List<Usuario> listaUsuarios = new ArrayList<>();

        try (Connection conn = DBconnection.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String rol = rs.getString("rolUsuario");
                Usuario usuario;

                if (rol.equals("Administrador")) {
                    usuario = new Administrador(
                            rs.getString("username"),
                            rs.getInt("idUsuario"),
                            rs.getTimestamp("fecha_registro").toLocalDateTime(),
                            rs.getString("correoUsuario"),
                            rs.getString("contrasenaUsuario"),
                            rs.getString("biografiaUsuario"),
                            rol
                    );
                } else {
                    usuario = new UsuarioNormal(
                            rs.getString("username"),
                            rs.getInt("idUsuario"),
                            rs.getTimestamp("fecha_registro").toLocalDateTime(),
                            rs.getString("correoUsuario"),
                            rs.getString("contrasenaUsuario"),
                            rs.getString("biografiaUsuario"),
                            rol
                    );
                }
                listaUsuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("🟥 Error al obtener todos los usuarios: " + e.getMessage());
        }
        return listaUsuarios;
    }

    // UPDATE - actualizar

    public void actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET username = ?, correoUsuario = ?, contrasenaUsuario = ?, biografiaUsuario = ?, rolUsuario = ? WHERE idUsuario = ?";

        try (Connection conn = DBconnection.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getCorreoUsuario());
            stmt.setString(3, usuario.getContrasenaUsuario());
            stmt.setString(4, usuario.getBiografiaUsuario());
            stmt.setString(5, usuario.getRolUsuario());
            stmt.setInt(6, usuario.getIdUsuario());

            stmt.executeUpdate();
            System.out.println("✅🆕 Usuario actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("🟥 Error al actualizar usuario: " + e.getMessage());
        }
    }

    // DELETE - eliminar

    public void eliminarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE idUsuario = ?";

        try (Connection conn = DBconnection.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("✅🆑 Usuario eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("🟥 Error al eliminar usuario: " + e.getMessage());
        }
    }

    // metodo para AUTENTICAR usuarios

    public boolean usernameExiste(String username) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE username = ?";

        try (Connection conn = DBconnection.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // si COUNT(*) es mayor que 0, el usuario ya existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("🟥 Error al verificar username: " + e.getMessage());
        }
        return false;
    }

    public Usuario autenticarUsuario(String username, String contrasena) {
        String sql = "SELECT * FROM usuarios WHERE username = ?";
        Usuario usuario = null;

        try (Connection conn = DBconnection.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String contrasenaGuardada = rs.getString("contrasenaUsuario");

                // verificar que el usuario existe en la BD
                //System.out.println("🔎 Usuario encontrado en la BD: " + rs.getString("username") + " | Rol: " + rs.getString("rolUsuario"));

                if (contrasenaGuardada.equals(contrasena)) { // verificar que los datos de login coinciden con los de la BD
                    String rol = rs.getString("rolUsuario");

                    if (rol.equalsIgnoreCase("Administrador")) {
                        usuario = new Administrador(
                                rs.getString("username"),
                                rs.getInt("idUsuario"),
                                rs.getTimestamp("fecha_registro").toLocalDateTime(),
                                rs.getString("correoUsuario"),
                                contrasenaGuardada,
                                rs.getString("biografiaUsuario"),
                                rol
                        );
                    } else {
                        usuario = new UsuarioNormal(
                                rs.getString("username"),
                                rs.getInt("idUsuario"),
                                rs.getTimestamp("fecha_registro").toLocalDateTime(),
                                rs.getString("correoUsuario"),
                                contrasenaGuardada,
                                rs.getString("biografiaUsuario"),
                                rol
                        );
                    }
                } else {
                    System.out.println("🟥 Contraseña incorrecta.");
                }
            } else {
                System.out.println("🟥👤 Usuario no encontrado en BD.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("🟥 Error en autenticación: " + e.getMessage());
        }
        return usuario;
    }
}