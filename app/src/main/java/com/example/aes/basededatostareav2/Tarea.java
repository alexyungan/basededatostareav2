package com.example.aes.basededatostareav2;

/**
 * Created by Aes on 29/5/2016.
 */
public class Tarea {
    private int _id;
    private  String descripcion;
    private  String hora;
    private  int estado;
    private  int estado_sincronizacion;

    public Tarea(int _id, String descripcion, String hora, int estado, int estado_sincronizacion) {
        this._id = _id;
        this.descripcion = descripcion;
        this.hora = hora;
        this.estado = estado;
        this.estado_sincronizacion = estado_sincronizacion;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getEstado_sincronizacion() {
        return estado_sincronizacion;
    }

    public void setEstado_sincronizacion(int estado_sincronizacion) {
        this.estado_sincronizacion = estado_sincronizacion;
    }
}
