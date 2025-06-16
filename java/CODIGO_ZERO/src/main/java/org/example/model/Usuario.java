package org.example.model;

import java.time.LocalDateTime;

public abstract class Usuario {
    // ATRIBUTOS
    public int idUsuario;
    public String username;
    public String correoUsuario;
    public String contrasenaUsuario;
    public String biografiaUsuario;
    public LocalDateTime fechaRegistro;
    public String rolUsuario;

    // CONSTRUCTORES

    public Usuario() {
    }

    /*public Usuario(int idUsuario, String username, String correoUsuario,
                    String contrasenaUsuario, LocalDateTime fechaRegistro,
                    String rolUsuario, String biografiaUsuario,
                    String avatarUsuarioUrl) {

        this.username = Objects.requireNonNull(username, "🟥 El nombre de usuario no puede ser nulo.");
        if (this.username.trim().isEmpty()) {
            throw new IllegalArgumentException(" 🟥 El nombre de usuario no puede estar vacío.");
        }

        this.correoUsuario = Objects.requireNonNull(correoUsuario, "🟥 El correo del usuario no puede ser nulo.");
        if (this.correoUsuario.trim().isEmpty()) {
            throw new IllegalArgumentException("🟥 El correo del usuario no puede estar vacío.");
        }

        this.contrasenaUsuario = Objects.requireNonNull(contrasenaUsuario, "🟥 La contraseña del usuario no puede ser nula.");
        if (this.contrasenaUsuario.trim().isEmpty()) {
            throw new IllegalArgumentException("🟥 La contraseña del usuario no puede estar vacía.");
        }

        this.fechaRegistro = Objects.requireNonNull(fechaRegistro, "🟥 La fecha de registro no puede ser nula.");

        this.rolUsuario = Objects.requireNonNull(rolUsuario, "🟥 El rol del usuario no puede ser nulo.");
        if (!isValidRolUsuario(rolUsuario)) {
            throw new IllegalArgumentException("🟥 El rol de usuario no es válido: " + rolUsuario);
        }

        // ASIGNACIÓN DE ATRIBUTOS
        this.idUsuario = idUsuario;
        this.biografiaUsuario = biografiaUsuario;
    }

    private boolean isValidRolUsuario(String rol) {
        return rol.equals("Usuario") || rol.equals("Administrador");
    }*/

    public Usuario(String username, int idUsuario, LocalDateTime fechaRegistro, String correoUsuario, String contrasenaUsuario, String biografiaUsuario, String rolUsuario) {
        this.username = username;
        this.idUsuario = idUsuario;
        this.fechaRegistro = fechaRegistro;
        this.correoUsuario = correoUsuario;
        this.contrasenaUsuario = contrasenaUsuario;
        this.biografiaUsuario = biografiaUsuario;
        this.rolUsuario = rolUsuario;
    }

    // GETTERS & SETTERS

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getContrasenaUsuario() {
        return contrasenaUsuario;
    }

    public void setContrasenaUsuario(String contrasenaUsuario) {
        this.contrasenaUsuario = contrasenaUsuario;
    }

    public String getBiografiaUsuario() {
        return biografiaUsuario;
    }

    public void setBiografiaUsuario(String biografiaUsuario) {
        this.biografiaUsuario = biografiaUsuario;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    // MÉTODOS ABSTRACTOS

    public abstract void mostrarPerfil();

    // METODOS PROPIOS & ToString

    /*public void mostrarPerfil() {
        System.out.println("👤 Mostrando perfil de " + getUsername() + ":");
        System.out.println("   ID: " + getIdUsuario());
        System.out.println("   Correo: " + getCorreoUsuario());
        System.out.println("   Biografía: " + (getBiografiaUsuario() != null ? getBiografiaUsuario() : "N/A"));
        System.out.println("   Fecha de Registro: " + getFechaRegistro());
    }*/

    @Override
    public String toString() {
        return "📄 Usuario: " + username + " | ID: " + idUsuario + " | Rol: " + rolUsuario + ")";
    }
}
