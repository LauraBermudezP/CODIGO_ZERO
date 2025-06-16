package org.example.model;

import java.time.LocalDateTime;

public class Articulo implements IVotable {
    private int idArticulo;
    private String titulo;
    private String contenidoArticulo;
    private String tipoArticulo;
    private int idAutor;
    private int idCategoria;
    private LocalDateTime fechaCreacionArticulo;

    // CONSTRUCTORES

    public Articulo() {
    }

    public Articulo(int idArticulo, String titulo, String tipoArticulo, String contenidoArticulo, int idAutor, int idCategoria, LocalDateTime fechaCreacionArticulo) {
        this.idArticulo = idArticulo;
        this.titulo = titulo;
        this.contenidoArticulo = contenidoArticulo;
        this.tipoArticulo = tipoArticulo;
        this.idAutor = idAutor;
        this.idCategoria = idCategoria;
        this.fechaCreacionArticulo = fechaCreacionArticulo;
    }

    // GETTERS & SETTERS

    public String getTipoArticulo() {
        return tipoArticulo;
    }

    public void setTipoArticulo(String tipoArticulo) {
        this.tipoArticulo = tipoArticulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getContenidoArticulo() {
        return contenidoArticulo;
    }

    public void setContenidoArticulo(String contenidoArticulo) {
        this.contenidoArticulo = contenidoArticulo;
    }

    public LocalDateTime getFechaCreacionArticulo() {
        return fechaCreacionArticulo;
    }

    public void setFechaCreacionArticulo(LocalDateTime fechaCreacionArticulo) {
        this.fechaCreacionArticulo = fechaCreacionArticulo;
    }

    // MÉTODOS
    public void mostrarArticulo() {
        System.out.println("Título: " + titulo);
        System.out.println("Contenido: " + contenidoArticulo);
        System.out.println("Tipo: " + tipoArticulo);
        System.out.println("Fecha de Creación: " + fechaCreacionArticulo);
        System.out.println("Autor ID: " + idAutor);
        System.out.println("Categoría ID: " + idCategoria);
    }

    // MÉTODOS DE INTERFAZ

    @Override
    public void recibirVoto() {
        System.out.println("👍🏻 Ha 118 personas les ha gustado esta publicación."); // cambiará segun tipo articul??
    }
}
