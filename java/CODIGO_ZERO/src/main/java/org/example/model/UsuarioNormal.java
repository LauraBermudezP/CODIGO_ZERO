package org.example.model;

import java.time.LocalDateTime;

public class UsuarioNormal extends Usuario {

    // COSTRUCTORES
    public UsuarioNormal() {
    }

    public UsuarioNormal(String username, int idUsuario, LocalDateTime fechaRegistro, String correoUsuario, String contrasenaUsuario, String biografiaUsuario, String rolUsuario) {
        super(username, idUsuario, fechaRegistro, correoUsuario, contrasenaUsuario, biografiaUsuario, rolUsuario);
    }

    @Override
    public void mostrarPerfil() {
        System.out.println(" - - - MOSTRANDO PERFIL DE USUARIO - - - ");
        System.out.println(username + "\n👤 Rol: " + rolUsuario);
    }
}
