package org.example;

import org.example.dao.ArticuloDAO;
import org.example.dao.CategoriaDAO;
import org.example.dao.ReporteDAO;
import org.example.dao.UsuarioDAO;
import org.example.database.DBconnection;
import org.example.model.*;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    // metodo para validar que las input (IDs) sean del tipo de dato esperado
    public static int obtenerNumeroValido(Scanner sc, String mensaje) {
        int numero;
        do {
            System.out.print(mensaje);
            while (!sc.hasNextInt()) {
                System.out.println("🟥 Debes ingresar un número válido que corresponda a un ID.");
                sc.next();
            }
            numero = sc.nextInt();
            sc.nextLine();

            if (numero <= 0) {
                System.out.println("🟥 El ID debe ser mayor que 0.");
            }
        } while (numero <= 0);

        return numero;
    }

    public static void main(String[] args) {
        // objetos o instancias DAO
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ArticuloDAO articuloDAO = new ArticuloDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        ReporteDAO reporteDAO = new ReporteDAO();
        // variables globales & scanner
        Integer opcion1, opcionUsuario, opcionAdministrador;
        Boolean menu1 = true, menuUsuario = true, menuAdministrador = true;
        Integer validacionLogin = 1;
        Scanner sc = new Scanner(System.in);
        // conexión & prueba de conexión para la BD
        Connection conexion = DBconnection.connect();
        /* if (conexion != null) {
            System.out.println("✅ Conexión exitosa a la base de datos.");
        } else {
            System.out.println("🟥 No se pudo establecer la conexión.");
        } */

        System.out.println("\n***** 👾 BIENVENIDO A CÓDIGO ZERO 👾 *****\n");
        while (menu1) {
            System.out.println("* 1) Iniciar sesión ");
            System.out.println("* 2) Registrarse ");
            System.out.println("* 0) Cerrar ");
            System.out.print("Por favor, selecciona una opción: \n");
            opcion1 = sc.nextInt();
            sc.nextLine();

            switch (opcion1) {
                case 1:
                    String usernameLogin, contrasenaLogin;
                    System.out.println("👾 INICIAR SESIÓN 👾");
                    System.out.print("* Ingrese su nombre de usuario: ");
                    usernameLogin = sc.nextLine();
                    System.out.print("* Ingrese su contraseña: ");
                    contrasenaLogin = sc.nextLine();

                    Usuario usuario = usuarioDAO.autenticarUsuario(usernameLogin, contrasenaLogin);

                    if (usuario != null) {
                        if (usuario.getRolUsuario().equalsIgnoreCase("administrador")) {
                            System.out.println("\n✅👾 Bienvenid@ a Código Zero, ADMIN @" + usuario.getUsername());
                            do {
                                System.out.println("=====================================");
                                System.out.println(" MENÚ DE ADMINISTRADOR ");
                                System.out.println("=====================================");
                                System.out.println("🔹 ARTÍCULOS:");
                                System.out.println("  1) Buscar un artículo en específico");
                                System.out.println("  2) Ver todos los artículos");
                                System.out.println("  3) Eliminar un artículo");
                                System.out.println("-------------------------------------");
                                System.out.println("🔹 REPORTES:");
                                System.out.println("  4) Ver un reporte en específico");
                                System.out.println("  5) Ver todos los reportes del blog");
                                System.out.println("  6) Actualizar un reporte");
                                System.out.println("  7) Eliminar un reporte");
                                System.out.println("-------------------------------------");
                                System.out.println("🔹 USUARIOS:");
                                System.out.println("  8) Ver los usuarios registrados");
                                System.out.println("  9) Buscar un usuario en específico");
                                System.out.println(" 10) Actualizar un usuario");
                                System.out.println(" 11) Eliminar un usuario");
                                System.out.println("-------------------------------------");
                                System.out.println("🔹 CATEGORÍAS:");
                                System.out.println(" 12) Crear categoría de contenido");
                                System.out.println(" 13) Ver las categorías de contenido");
                                System.out.println(" 14) Actualizar una categoría");
                                System.out.println(" 15) Eliminar una categoría");
                                System.out.println("-------------------------------------");
                                System.out.println("  0) Cerrar menú");
                                System.out.println("=====================================");
                                System.out.print("Por favor, selecciona una opción: ");
                                opcionAdministrador = sc.nextInt();
                                sc.nextLine();

                                switch (opcionAdministrador) {
                                    case 1:
                                        System.out.println("\n📄 BUSCAR ARTÍCULO 📄");

                                        int idArticulo = obtenerNumeroValido(sc, "* Ingrese el ID del artículo que desea ver: ");

                                        Articulo articuloEncontrado = articuloDAO.obtenerArticuloPorId(idArticulo);

                                        if (articuloEncontrado != null) {
                                            System.out.println("\n📌 Artículo encontrado:");
                                            System.out.println("🆔 ID: " + articuloEncontrado.getIdArticulo());
                                            System.out.println("📄 Título: " + articuloEncontrado.getTitulo());
                                            System.out.println("✍ Contenido: " + articuloEncontrado.getContenidoArticulo());
                                            System.out.println("📂 Categoría: " + articuloEncontrado.getIdCategoria());
                                            System.out.println("📆 Fecha de creación: " + articuloEncontrado.getFechaCreacionArticulo());
                                        } else {
                                            System.out.println("🟥 No se encontró ningún artículo con el ID proporcionado.");
                                        }
                                        break;
                                    case 2:
                                        System.out.println("\n📂 LISTA DE TODOS LOS ARTÍCULOS 📂");

                                        List<Articulo> listaArticulos = articuloDAO.obtenerTodosLosArticulos();

                                        if (listaArticulos.isEmpty()) {
                                            System.out.println("⚠️ No hay artículos registrados en la base de datos.");
                                        } else {
                                            for (Articulo articulo : listaArticulos) {
                                                System.out.println("=====================================");
                                                System.out.println("🆔 ID: " + articulo.getIdArticulo());
                                                System.out.println("📄 Título: " + articulo.getTitulo());
                                                System.out.println("✍ Contenido: " + articulo.getContenidoArticulo());
                                                System.out.println("📂 Tipo: " + articulo.getTipoArticulo());
                                                System.out.println("👤 Autor ID: " + articulo.getIdAutor());
                                                System.out.println("📆 Fecha de creación: " + articulo.getFechaCreacionArticulo());
                                                System.out.println("=====================================");
                                            }
                                        }
                                        break;
                                    case 3:
                                        System.out.println("\n🗑️ ELIMINAR ARTÍCULO 🗑️");
                                        int idEliminar = obtenerNumeroValido(sc, "* Ingrese el ID del artículo a eliminar: ");

                                        Articulo articuloAEliminar = articuloDAO.obtenerArticuloPorId(idEliminar);

                                        if (articuloAEliminar != null) {
                                            System.out.println("⚠️ ¿Estás seguro de que quieres eliminar este artículo? (si/no)");
                                            System.out.println("📄 Título: " + articuloAEliminar.getTitulo());

                                            String confirmacion = sc.nextLine();
                                            if (confirmacion.equalsIgnoreCase("si")) {
                                                articuloDAO.eliminarArticulo(idEliminar);
                                            } else {
                                                System.out.println("🟡 Eliminación de artículo cancelada.");
                                            }
                                        } else {
                                            System.out.println("🟥 No se encontró ningún artículo con el ID proporcionado.");
                                        }
                                        break;
                                    case 4:
                                        System.out.println("\n📄 VER REPORTE 📄");

                                        int idReporte = obtenerNumeroValido(sc, "* Ingrese el ID del reporte que desea ver: ");

                                        Reporte reporteEncontrado = reporteDAO.obtenerReportePorId(idReporte);

                                        if (reporteEncontrado != null) {
                                            System.out.println("\n📌 Reporte encontrado:");
                                            System.out.println("🆔 ID: " + reporteEncontrado.getIdReporte());
                                            System.out.println("📄 Motivo: " + reporteEncontrado.getMotivoReporte());
                                            System.out.println("📆 Fecha: " + reporteEncontrado.getFechaReporte());
                                            System.out.println("📂 Estado: " + reporteEncontrado.getEstadoReporte());
                                            System.out.println("👤 ID Usuario: " + reporteEncontrado.getIdUsuario());
                                            System.out.println("📄 ID Artículo asociado: " + reporteEncontrado.getIdArticulo());
                                        } else {
                                            System.out.println("🟥 No se encontró ningún reporte con el ID proporcionado.");
                                        }
                                        break;
                                    case 5:
                                        System.out.println("\n📑 LISTA DE TODOS LOS REPORTES 📑");

                                        List<Reporte> listaReportes = reporteDAO.obtenerTodosLosReportes();

                                        if (listaReportes.isEmpty()) {
                                            System.out.println("⚠️ No hay reportes registrados en la base de datos.");
                                        } else {
                                            for (Reporte reporte : listaReportes) {
                                                System.out.println("=====================================");
                                                System.out.println("🆔 ID: " + reporte.getIdReporte());
                                                System.out.println("📄 Motivo: " + reporte.getMotivoReporte());
                                                System.out.println("📆 Fecha de reporte: " + reporte.getFechaReporte());
                                                System.out.println("📂 Estado: " + reporte.getEstadoReporte());
                                                System.out.println("👤 ID Usuario: " + reporte.getIdUsuario());
                                                System.out.println("📄 ID Artículo reportado: " + reporte.getIdArticulo());
                                                System.out.println("=====================================");
                                            }
                                        }
                                        break;
                                    case 6:
                                        System.out.println("\n✏️ ACTUALIZAR REPORTE ✏️");

                                        int idReporteActualizar = obtenerNumeroValido(sc, "* Ingrese el ID del reporte que desea actualizar: ");

                                        Reporte reporteExistente = reporteDAO.obtenerReportePorId(idReporteActualizar);

                                        if (reporteExistente != null) {
                                            System.out.println("\n📌 Reporte encontrado:");
                                            System.out.println("📄 Motivo actual: " + reporteExistente.getMotivoReporte());
                                            System.out.println("📆 Fecha: " + reporteExistente.getFechaReporte());
                                            System.out.println("📂 Estado actual: " + reporteExistente.getEstadoReporte());

                                            System.out.print("* Ingrese el nuevo motivo del reporte: ");
                                            String nuevoMotivo = sc.nextLine();

                                            String nuevoEstado;
                                            do {
                                                System.out.print("* Estado del reporte (Pendiente / Revisado / Cerrado): ");
                                                nuevoEstado = sc.nextLine();

                                                if (!nuevoEstado.equalsIgnoreCase("Pendiente") &&
                                                        !nuevoEstado.equalsIgnoreCase("Revisado") &&
                                                        !nuevoEstado.equalsIgnoreCase("Cerrado")) {
                                                    System.out.println("🟥 Estado inválido. Debe ser 'Pendiente', 'Revisado' o 'Cerrado'.");
                                                }
                                            } while (!nuevoEstado.equalsIgnoreCase("Pendiente") &&
                                                    !nuevoEstado.equalsIgnoreCase("Revisado") &&
                                                    !nuevoEstado.equalsIgnoreCase("Cerrado"));

                                            System.out.print("* Ingrese el ID del usuario que registró el reporte: ");
                                            int idUsuario = sc.nextInt();
                                            sc.nextLine();

                                            System.out.print("* Ingrese el ID del artículo asociado al reporte: ");
                                            int idArticuloAsociado = sc.nextInt();
                                            sc.nextLine();

                                            Reporte reporteActualizado = new Reporte(
                                                    idReporteActualizar, nuevoMotivo, reporteExistente.getFechaReporte(), nuevoEstado, idUsuario, idArticuloAsociado
                                            );

                                            reporteDAO.actualizarReporte(reporteActualizado);
                                        } else {
                                            System.out.println("🟥 No se encontró ningún reporte con el ID proporcionado.");
                                        }
                                        break;
                                    case 7:
                                        System.out.println("\n🗑️ ELIMINAR REPORTE 🗑️");

                                        int idReporteEliminar = obtenerNumeroValido(sc, "* Ingrese el ID del reporte a eliminar: ");

                                        Reporte reporteAEliminar = reporteDAO.obtenerReportePorId(idReporteEliminar);

                                        if (reporteAEliminar != null) {
                                            System.out.println("\n⚠️ ¿Estás seguro de que quieres eliminar este reporte? (si/no)");
                                            System.out.println("📄 Motivo: " + reporteAEliminar.getMotivoReporte());
                                            System.out.println("📆 Fecha de reporte: " + reporteAEliminar.getFechaReporte());
                                            System.out.println("📂 Estado: " + reporteAEliminar.getEstadoReporte());

                                            String confirmacion = sc.nextLine();
                                            if (confirmacion.equalsIgnoreCase("si")) {
                                                reporteDAO.eliminarReporte(idReporteEliminar);
                                            } else {
                                                System.out.println("🟡 Eliminación cancelada.");
                                            }
                                        } else {
                                            System.out.println("🟥 No se encontró ningún reporte con el ID proporcionado.");
                                        }
                                        break;
                                    case 8:
                                        System.out.println("\n👥 LISTA DE TODOS LOS USUARIOS 👥");

                                        List<Usuario> listaUsuarios = usuarioDAO.obtenerTodosLosUsuarios();

                                        if (listaUsuarios.isEmpty()) {
                                            System.out.println("⚠️ No hay usuarios registrados en la base de datos.");
                                        } else {
                                            for (Usuario u : listaUsuarios) {
                                                System.out.println("=====================================");
                                                System.out.println("🆔 ID Usuario: " + u.getIdUsuario());
                                                System.out.println("👤 Username: " + u.getUsername());
                                                System.out.println("📧 Correo: " + u.getCorreoUsuario());
                                                System.out.println("📝 Biografía: " + u.getBiografiaUsuario());
                                                System.out.println("📂 Rol: " + u.getRolUsuario());
                                                System.out.println("📆 Fecha de registro: " + u.getFechaRegistro());
                                                System.out.println("=====================================");
                                            }
                                        }
                                        break;
                                    case 9:
                                        System.out.println("\n🔍 BUSCAR USUARIO 🔍");

                                        int idUsuarioBuscar = obtenerNumeroValido(sc, "* Ingrese el ID del usuario que desea buscar: ");

                                        Usuario usuarioEncontrado = usuarioDAO.obtenerUsuarioPorId(idUsuarioBuscar);

                                        if (usuarioEncontrado != null) {
                                            System.out.println("\n📌 Usuario encontrado:");
                                            System.out.println("🆔 ID Usuario: " + usuarioEncontrado.getIdUsuario());
                                            System.out.println("👤 Username: " + usuarioEncontrado.getUsername());
                                            System.out.println("📧 Correo: " + usuarioEncontrado.getCorreoUsuario());
                                            System.out.println("📝 Biografía: " + usuarioEncontrado.getBiografiaUsuario());
                                            System.out.println("📂 Rol: " + usuarioEncontrado.getRolUsuario());
                                            System.out.println("📆 Fecha de registro: " + usuarioEncontrado.getFechaRegistro());
                                        } else {
                                            System.out.println("🟥 No se encontró ningún usuario con el ID proporcionado.");
                                        }
                                        break;
                                    case 11:
                                        System.out.println("\n🗑️ ELIMINAR USUARIO 🗑️");

                                        int idUsuarioEliminar = obtenerNumeroValido(sc, "* Ingrese el ID del usuario que desea eliminar: ");

                                        Usuario usuarioAEliminar = usuarioDAO.obtenerUsuarioPorId(idUsuarioEliminar);

                                        if (usuarioAEliminar != null) {
                                            System.out.println("\n⚠️ ¿Estás seguro de que quieres eliminar este usuario del blog? (si/no)");
                                            System.out.println("👤 Username: " + usuarioAEliminar.getUsername());
                                            System.out.println("📧 Correo: " + usuarioAEliminar.getCorreoUsuario());
                                            System.out.println("📂 Rol: " + usuarioAEliminar.getRolUsuario());

                                            String confirmacion = sc.nextLine();
                                            if (confirmacion.equalsIgnoreCase("si")) {
                                                usuarioDAO.eliminarUsuario(idUsuarioEliminar);
                                            } else {
                                                System.out.println("🟡 Eliminación cancelada.");
                                            }
                                        } else {
                                            System.out.println("🟥 No se encontró ningún usuario con el ID proporcionado.");
                                        }
                                        break;
                                    case 12:
                                        System.out.println("\n📝 CREAR CATEGORÍA 📝");
                                        System.out.print("* Nombre de la categoría: ");
                                        String nombreCategoria = sc.nextLine();

                                        System.out.print("* Descripción corta de la categoría: ");
                                        String descripcion = sc.nextLine();

                                        Categoria nuevaCategoria = new Categoria(0, nombreCategoria, descripcion);
                                        categoriaDAO.crearCategoria(nuevaCategoria);
                                        break;
                                    case 13:
                                        System.out.println("\n📂 LISTA DE TODAS LAS CATEGORÍAS 📂");

                                        List<Categoria> listaCategorias = categoriaDAO.obtenerTodasLasCategorias();

                                        if (listaCategorias.isEmpty()) {
                                            System.out.println("⚠️ No hay categorías registradas en la base de datos.");
                                        } else {
                                            for (Categoria categoria : listaCategorias) {
                                                System.out.println("=====================================");
                                                System.out.println("🆔 ID Categoría: " + categoria.getIdCategoria());
                                                System.out.println("📌 Nombre: " + categoria.getNombreCategoria());
                                                System.out.println("📝 Descripción: " + categoria.getDescripcion());
                                                System.out.println("=====================================");
                                            }
                                        }
                                        break;
                                    case 14:
                                        System.out.println("\n✏️ ACTUALIZAR CATEGORÍA ✏️");

                                        int idCategoriaActualizar = obtenerNumeroValido(sc, "* Ingrese el ID de la categoría a actualizar: ");

                                        Categoria categoriaExistente = categoriaDAO.obtenerCategoriaPorId(idCategoriaActualizar);

                                        if (categoriaExistente != null) {
                                            System.out.println("\n📌 Categoría encontrada:");
                                            System.out.println("📌 Nombre actual: " + categoriaExistente.getNombreCategoria());
                                            System.out.println("📝 Descripción actual: " + categoriaExistente.getDescripcion());

                                            System.out.print("* Ingrese el nuevo nombre de la categoría: ");
                                            String nuevoNombre = sc.nextLine();

                                            System.out.print("* Ingrese la nueva descripción de la categoría: ");
                                            String nuevaDescripcion = sc.nextLine();

                                            Categoria categoriaActualizada = new Categoria(idCategoriaActualizar, nuevoNombre, nuevaDescripcion);
                                            categoriaDAO.actualizarCategoria(categoriaActualizada);
                                        } else {
                                            System.out.println("🟥 No se encontró ninguna categoría con el ID proporcionado.");
                                        }
                                        break;
                                    case 15:
                                        System.out.println("\n🗑️ ELIMINAR CATEGORÍA 🗑️");

                                        int idCategoriaEliminar = obtenerNumeroValido(sc, "* Ingrese el ID de la categoría que desea eliminar: ");

                                        Categoria categoriaAEliminar = categoriaDAO.obtenerCategoriaPorId(idCategoriaEliminar);

                                        if (categoriaAEliminar != null) {
                                            System.out.println("\n⚠️ ¿Estás seguro de que quieres eliminar esta categoría? (si/no)");
                                            System.out.println("📌 Nombre: " + categoriaAEliminar.getNombreCategoria());
                                            System.out.println("📝 Descripción: " + categoriaAEliminar.getDescripcion());

                                            String confirmacion = sc.nextLine();
                                            if (confirmacion.equalsIgnoreCase("si")) {
                                                categoriaDAO.eliminarCategoria(idCategoriaEliminar);
                                            } else {
                                                System.out.println("🟡 Eliminación cancelada.");
                                            }
                                        } else {
                                            System.out.println("🟥 No se encontró ninguna categoría con el ID proporcionado.");
                                        }
                                        break;
                                    case 0:
                                        System.out.println("\n👤 Cerrando menú de administrador...");
                                        menuAdministrador = false;
                                        break;
                                    default:
                                        System.out.println("\n🔴 Opción inválida. Intenta nuevamente...");
                                        break;
                                }
                            } while (menuAdministrador && opcionAdministrador != 0);
                        } else {
                            System.out.println("\n✅👾 Bienvenid@ a Código Zero, @" + usuario.getUsername());
                            do {
                                System.out.println("=====================================");
                                System.out.println(" MENÚ DE USUARIO");
                                System.out.println("=====================================");
                                System.out.println("🔹 ARTÍCULOS:");
                                System.out.println("  1) Crear artículo");
                                System.out.println("  2) Actualizar un artículo propio");
                                System.out.println("  3) Buscar un artículo en específico");
                                System.out.println("  4) Eliminar un artículo propio");
                                System.out.println("  5) Ver todos los artículos");
                                System.out.println("-------------------------------------");
                                System.out.println("🔹 TU CUENTA:");
                                System.out.println("  6) Actualizar perfil");
                                System.out.println("-------------------------------------");
                                System.out.println("🔹 REPORTES:");
                                System.out.println("  7) Generar un reporte");
                                System.out.println("-------------------------------------");
                                System.out.println("🔹 CATEGORÍAS:");
                                System.out.println("  8) Ver una categoría de contenido");
                                System.out.println("  9) Listar todas las categorías del blog");
                                System.out.println("-------------------------------------");
                                System.out.println("  0) Cerrar menú");
                                System.out.println("=====================================");
                                System.out.print("Por favor, selecciona una opción: ");
                                opcionUsuario = sc.nextInt();
                                sc.nextLine();

                                switch (opcionUsuario) {
                                    case 1:
                                        System.out.println("\n📝 CREAR ARTÍCULO 📝");
                                        System.out.print("* Ingresa el título: ");
                                        String titulo = sc.nextLine();

                                        System.out.print("* Escribe el contenido del artículo: ");
                                        String contenido = sc.nextLine();

                                        String tipo;
                                        do {
                                            System.out.print("* Tipo de publicación (Artículo / Evento / Recurso / Oferta laboral): ");
                                            tipo = sc.nextLine();

                                            if (!tipo.equals("Artículo") && !tipo.equals("Evento") && !tipo.equals("Recurso") && !tipo.equals("Oferta laboral")) {
                                                System.out.println("🟥 Tipo de artículo inválido. Debe ser 'Artículo', 'Evento', 'Recurso' o 'Oferta laboral'.");
                                            }
                                        } while (!tipo.equals("Artículo") && !tipo.equals("Evento") && !tipo.equals("Recurso") && !tipo.equals("Oferta laboral"));

                                        System.out.print("* ID de la categoría: ");
                                        int idCategoria = sc.nextInt();
                                        sc.nextLine();

                                        Articulo nuevoArticulo = new Articulo(0, titulo, contenido, tipo, usuario.getIdUsuario(), idCategoria, LocalDateTime.now());
                                        articuloDAO.crearArticulo(nuevoArticulo);
                                        break;
                                    case 2:
                                        System.out.println("\n✏️ ACTUALIZAR ARTÍCULO ✏️");

                                        int idArticuloActualizar = obtenerNumeroValido(sc, "* Ingrese el ID del artículo que desea actualizar: ");

                                        Articulo articuloExistente = articuloDAO.obtenerArticuloPorId(idArticuloActualizar);

                                        if (articuloExistente != null) {
                                            if (articuloExistente.getIdAutor() == usuario.getIdUsuario()) {
                                                System.out.println("\n📌 Artículo encontrado:");
                                                System.out.println("📄 Título actual: " + articuloExistente.getTitulo());
                                                System.out.println("✍ Contenido actual: " + articuloExistente.getContenidoArticulo());
                                                System.out.println("📂 Tipo actual: " + articuloExistente.getTipoArticulo());

                                                System.out.print("* Ingrese el nuevo título del artículo: ");
                                                String nuevoTitulo = sc.nextLine();

                                                System.out.print("* Escriba el nuevo contenido del artículo: ");
                                                String nuevoContenido = sc.nextLine();

                                                System.out.print("* Tipo de artículo (Artículo / Evento / Recurso / Oferta laboral): ");
                                                String nuevoTipo = sc.nextLine();

                                                Articulo articuloActualizado = new Articulo(
                                                        idArticuloActualizar, nuevoTitulo, nuevoContenido, nuevoTipo, usuario.getIdUsuario(),
                                                        articuloExistente.getIdCategoria(), articuloExistente.getFechaCreacionArticulo()
                                                );
                                                articuloDAO.actualizarArticulo(articuloActualizado);
                                            } else {
                                                System.out.println("🟥 No puedes actualizar un artículo que no es tuyo.");
                                            }
                                        } else {
                                            System.out.println("🟥 No se encontró ningún artículo con el ID proporcionado.");
                                        }
                                        break;
                                    case 3:
                                        System.out.println("\n🔍 BUSCAR ARTÍCULO 🔍");

                                        int idArticuloBuscar = obtenerNumeroValido(sc, "* Ingrese el ID del artículo que desea buscar: ");

                                        Articulo articuloEncontrado = articuloDAO.obtenerArticuloPorId(idArticuloBuscar);

                                        if (articuloEncontrado != null) {
                                            System.out.println("\n📌 Artículo encontrado:");
                                            System.out.println("🆔 ID: " + articuloEncontrado.getIdArticulo());
                                            System.out.println("📄 Título: " + articuloEncontrado.getTitulo());
                                            System.out.println("✍ Contenido: " + articuloEncontrado.getContenidoArticulo());
                                            System.out.println("📂 Tipo: " + articuloEncontrado.getTipoArticulo());
                                            System.out.println("👤 Autor ID: " + articuloEncontrado.getIdAutor());
                                            System.out.println("📆 Fecha de creación: " + articuloEncontrado.getFechaCreacionArticulo());
                                        } else {
                                            System.out.println("🟥 No se encontró ningún artículo con el ID proporcionado.");
                                        }
                                        break;
                                    case 4:
                                        System.out.println("\n🗑️ ELIMINAR ARTÍCULO 🗑️");

                                        int idArticuloEliminar = obtenerNumeroValido(sc, "* Ingrese el ID del artículo que desea eliminar: ");

                                        Articulo articuloAEliminar = articuloDAO.obtenerArticuloPorId(idArticuloEliminar);

                                        if (articuloAEliminar != null) {
                                            if (articuloAEliminar.getIdAutor() == usuario.getIdUsuario()) {
                                                System.out.println("\n⚠️ ¿Estás seguro de que quieres eliminar este artículo? (si/no)");
                                                System.out.println("📄 Título: " + articuloAEliminar.getTitulo());
                                                System.out.println("✍ Contenido: " + articuloAEliminar.getContenidoArticulo());

                                                String confirmacion = sc.nextLine();
                                                if (confirmacion.equalsIgnoreCase("si")) {
                                                    articuloDAO.eliminarArticulo(idArticuloEliminar);
                                                } else {
                                                    System.out.println("🟡 Eliminación cancelada.");
                                                }
                                            } else {
                                                System.out.println("🟥 No puedes eliminar un artículo que no es tuyo.");
                                            }
                                        } else {
                                            System.out.println("🟥 No se encontró ningún artículo con el ID proporcionado.");
                                        }
                                        break;
                                    case 5:
                                        System.out.println("\n📂 LISTA DE TODOS LOS ARTÍCULOS 📂");

                                        List<Articulo> listaArticulos = articuloDAO.obtenerTodosLosArticulos();

                                        if (listaArticulos.isEmpty()) {
                                            System.out.println("⚠️ No hay artículos registrados en la base de datos.");
                                        } else {
                                            for (Articulo articulo : listaArticulos) {
                                                System.out.println("=====================================");
                                                System.out.println("🆔 ID: " + articulo.getIdArticulo());
                                                System.out.println("📄 Título: " + articulo.getTitulo());
                                                System.out.println("✍ Contenido: " + articulo.getContenidoArticulo());
                                                System.out.println("📂 Tipo: " + articulo.getTipoArticulo());
                                                System.out.println("👤 Autor ID: " + articulo.getIdAutor());
                                                System.out.println("📆 Fecha de creación: " + articulo.getFechaCreacionArticulo());
                                                System.out.println("=====================================");
                                            }
                                        }
                                        break;
                                    case 6:
                                        System.out.println("\n✏️ ACTUALIZAR PERFIL ✏️");

                                        int idUsuarioActualizar = usuario.getIdUsuario();

                                        UsuarioNormal usuarioExistente = (UsuarioNormal) usuarioDAO.obtenerUsuarioPorId(idUsuarioActualizar);

                                        if (usuarioExistente != null) {
                                            System.out.println("\n📌 Perfil actual:");
                                            System.out.println("👤 Username: " + usuarioExistente.getUsername());
                                            System.out.println("📧 Correo: " + usuarioExistente.getCorreoUsuario());
                                            System.out.println("📝 Biografía: " + usuarioExistente.getBiografiaUsuario());

                                            System.out.print("* Ingrese el nuevo username: ");
                                            String nuevoUsername = sc.nextLine();

                                            System.out.print("* Ingrese el nuevo correo electrónico: ");
                                            String nuevoCorreo = sc.nextLine();

                                            System.out.print("* Ingrese la nueva contraseña: ");
                                            String nuevaContrasena = sc.nextLine();

                                            System.out.print("* Ingrese la nueva biografía: ");
                                            String nuevaBiografia = sc.nextLine();

                                            UsuarioNormal usuarioActualizado = new UsuarioNormal(
                                                    nuevoUsername, idUsuarioActualizar, usuarioExistente.getFechaRegistro(),
                                                    nuevoCorreo, nuevaContrasena, nuevaBiografia, usuarioExistente.getRolUsuario()
                                            );

                                            usuarioDAO.actualizarUsuario(usuarioActualizado);
                                        } else {
                                            System.out.println("🟥 No se encontró el perfil del usuario.");
                                        }
                                        break;
                                    case 7:
                                        System.out.println("\n🚩 GENERAR REPORTE 🚩");

                                        System.out.print("* Ingrese el motivo del reporte: ");
                                        String motivoReporte = sc.nextLine();

                                        String estadoReporte;
                                        do {
                                            System.out.print("* Estado del reporte (Pendiente / Revisado / Cerrado): ");
                                            estadoReporte = sc.nextLine();

                                            if (!estadoReporte.equalsIgnoreCase("Pendiente") &&
                                                    !estadoReporte.equalsIgnoreCase("Revisado") &&
                                                    !estadoReporte.equalsIgnoreCase("Cerrado")) {
                                                System.out.println("🟥 Estado inválido. Debe ser 'Pendiente', 'Revisado' o 'Cerrado'.");
                                            }
                                        } while (!estadoReporte.equalsIgnoreCase("Pendiente") &&
                                                !estadoReporte.equalsIgnoreCase("Revisado") &&
                                                !estadoReporte.equalsIgnoreCase("Cerrado"));

                                        int idArticuloReportado = obtenerNumeroValido(sc, "* Ingrese el ID del artículo a reportar: ");

                                        Articulo articuloAReportar = articuloDAO.obtenerArticuloPorId(idArticuloReportado);

                                        if (articuloAReportar != null) {
                                            Reporte nuevoReporte = new Reporte(0, motivoReporte, LocalDateTime.now(), estadoReporte, usuario.getIdUsuario(), idArticuloReportado);
                                            reporteDAO.crearReporte(nuevoReporte);
                                        } else {
                                            System.out.println("🟥 No se encontró el artículo con el ID ingresado.");
                                        }
                                        break;
                                    case 8:
                                        System.out.println("\n📂 VER CATEGORÍA DE CONTENIDO 📂");

                                        int idCategoriaBuscar = obtenerNumeroValido(sc, "* Ingrese el ID de la categoría que desea ver: ");

                                        Categoria categoriaEncontrada = categoriaDAO.obtenerCategoriaPorId(idCategoriaBuscar);

                                        if (categoriaEncontrada != null) {
                                            System.out.println("\n📌 Categoría encontrada:");
                                            System.out.println("🆔 ID: " + categoriaEncontrada.getIdCategoria());
                                            System.out.println("📌 Nombre: " + categoriaEncontrada.getNombreCategoria());
                                            System.out.println("📝 Descripción: " + categoriaEncontrada.getDescripcion());
                                        } else {
                                            System.out.println("🟥 No se encontró ninguna categoría con el ID proporcionado.");
                                        }
                                        break;
                                    case 9:
                                        System.out.println("\n📂 LISTA DE TODAS LAS CATEGORÍAS 📂");

                                        List<Categoria> listaCategorias = categoriaDAO.obtenerTodasLasCategorias();

                                        if (listaCategorias.isEmpty()) {
                                            System.out.println("⚠️ No hay categorías registradas en la base de datos.");
                                        } else {
                                            for (Categoria categoria : listaCategorias) {
                                                System.out.println("=====================================");
                                                System.out.println("🆔 ID Categoría: " + categoria.getIdCategoria());
                                                System.out.println("📌 Nombre: " + categoria.getNombreCategoria());
                                                System.out.println("📝 Descripción: " + categoria.getDescripcion());
                                                System.out.println("=====================================");
                                            }
                                        }
                                        break;
                                    case 0:
                                        System.out.println("\n👤 Cerrando menú de usuario...");
                                        menuUsuario = false;
                                        break;
                                    default:
                                        System.out.println("\n🔴 Opción inválida. Intenta nuevamente...");
                                        break;
                                }
                            } while (menuUsuario && opcionUsuario != 0);
                        }
                    } else {
                        do {
                            validacionLogin++;
                            System.out.println("🟥 USUARIO O CONTRASEÑA INCORRECTOS. Intente nuevamente.");

                            System.out.print("* Ingrese su nombre de usuario: ");
                            usernameLogin = sc.nextLine();
                            System.out.print("* Ingrese su contraseña: ");
                            contrasenaLogin = sc.nextLine();

                            usuario = usuarioDAO.autenticarUsuario(usernameLogin, contrasenaLogin);

                        } while (usuario == null && validacionLogin < 3);
                    }
                    break;
                case 2:
                    String username, correoUsuario, contrasenaUsuario, biografiaUsuario, rolUsuario;
                    System.out.println("\n👾 CREAR CUENTA 👾\n");
                    System.out.print("* Ingrese su nombre de usuario: ");
                    username = sc.nextLine();

                    if (usuarioDAO.usernameExiste(username)) {
                        System.out.println("🟥👥 El nombre de usuario '" + username + "' ya existe. Intenta con otro.");
                        break;
                    }

                    System.out.print("* Ingrese correo electrónico: ");
                    correoUsuario = sc.nextLine();
                    System.out.print("* Ingrese su contraseña (mínimo 8 caracteres): ");
                    contrasenaUsuario = sc.nextLine();

                    if (contrasenaUsuario.length() < 8) {
                        System.out.println("🟥🔒 La contraseña debe tener al menos 8 caracteres.");
                        break;
                    }

                    System.out.print("* Ingrese una breve biografía o descripción de usted mismo: ");
                    biografiaUsuario = sc.nextLine();
                    System.out.print("* ¿En qué rol deseas registrarte? Usuario/Administrador: ");
                    rolUsuario = sc.nextLine();

                    Usuario nuevoUsuario;

                    if (rolUsuario.equalsIgnoreCase("administrador")) {
                        nuevoUsuario = new Administrador(username, 0, LocalDateTime.now(), correoUsuario, contrasenaUsuario, biografiaUsuario, rolUsuario);
                    } else if (rolUsuario.equalsIgnoreCase("usuario")) {
                        nuevoUsuario = new UsuarioNormal(username, 0, LocalDateTime.now(), correoUsuario, contrasenaUsuario, biografiaUsuario, rolUsuario);
                    } else {
                        System.out.println("🟥👤 El Rol que ingresaste es inválido.");
                        break;
                    }

                    usuarioDAO.crearUsuario(nuevoUsuario);
                    System.out.println("🟩👤 Cuenta creada exitosamente. Ahora inicia sesión.\n");
                    break;
                case 0:
                    System.out.println("\n**** 👾 GRACIAS POR VISITAR CÓDIGO ZERO 👾 ****\n");
                    menu1 = false;
                    break;
                default:
                    System.out.println("\n🔴 UPS! Parece que seleccionaste una opción inexistente. \nIntenta nuevamente...");
                    break;
            }
        }
    }
}