package org.example.model;

import java.time.LocalDateTime;

public class Administrador extends Usuario implements IEditable {

    // COSTRUCTORES
    public Administrador() {
    }

    public Administrador(String username, int idUsuario, LocalDateTime fechaRegistro, String correoUsuario, String contrasenaUsuario, String biografiaUsuario, String rolUsuario) {
        super(username, idUsuario, fechaRegistro, correoUsuario, contrasenaUsuario, biografiaUsuario, rolUsuario);
    }

    @Override
    public void mostrarPerfil() {
        System.out.println(" - - - MOSTRANDO PERFIL DE ADMINISTRADOR - - - ");
        System.out.println(username + "\n👤 Rol: " + rolUsuario);
    }

    // MÉTODOS DE INTERFAZ

    @Override
    public void editarArticulo(Articulo articulo, String nuevoTitulo, String nuevoContenido) {
        articulo.setTitulo(nuevoTitulo);
        articulo.setContenidoArticulo(nuevoContenido);
        System.out.println("🖌️ Artículo editado por ADMIN " + username);
    }

}

