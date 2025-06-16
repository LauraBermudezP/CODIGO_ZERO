package org.example.model;

public class Categoria {
    // ATRIBUTOS
    private int idCategoria;
    private String nombreCategoria;
    private String descripcion;

    // CONSTRUCTORES

    public Categoria() {
    }

    public Categoria(int idCategoria, String nombreCategoria, String descripcion) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.descripcion = descripcion;
    }

    // GETTERS & SETTERS

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }


    // MÉTODOS

    public void mostrarCategoria() {
        System.out.println("Categoría: " + nombreCategoria + " - " + descripcion);
    }
}
