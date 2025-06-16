package org.example.model;

import java.time.LocalDateTime;

public class Reporte {
    // ATRIBUTOS
    private int idReporte;
    private String motivoReporte;
    private LocalDateTime fechaReporte;
    private String estadoReporte;
    private int idUsuario;
    private int idArticulo;

    // CONSTRUCTORES

    public Reporte() {
    }

    public Reporte(int idReporte, String motivoReporte, LocalDateTime fechaReporte, String estadoReporte, int idUsuario, int idArticulo) {
        this.idReporte = idReporte;
        this.motivoReporte = motivoReporte;
        this.fechaReporte = fechaReporte;
        this.estadoReporte = estadoReporte;
        this.idUsuario = idUsuario;
        this.idArticulo = idArticulo;
    }

    // GETTERS & SETTERS

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getEstadoReporte() {
        return estadoReporte;
    }

    public void setEstadoReporte(String estadoReporte) {
        this.estadoReporte = estadoReporte;
    }

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public String getMotivoReporte() {
        return motivoReporte;
    }

    public void setMotivoReporte(String motivoReporte) {
        this.motivoReporte = motivoReporte;
    }

    public LocalDateTime getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(LocalDateTime fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    // MÉTODOS

    public void mostrarReporte() {
        System.out.println("Reporte ID: " + idReporte);
        System.out.println("Motivo: " + motivoReporte);
        System.out.println("Fecha de Reporte: " + fechaReporte);
        System.out.println("Estado: " + estadoReporte);
    }
}