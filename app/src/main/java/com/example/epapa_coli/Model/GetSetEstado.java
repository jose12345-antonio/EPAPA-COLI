package com.example.epapa_coli.Model;

public class GetSetEstado {

    int id;
    String nombre_estado;

    public GetSetEstado(int id, String nombre_estado) {
        this.id = id;
        this.nombre_estado = nombre_estado;
    }

    public GetSetEstado() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_estado() {
        return nombre_estado;
    }

    public void setNombre_estado(String nombre_estado) {
        this.nombre_estado = nombre_estado;
    }

    @Override
    public String toString() {
        return nombre_estado;
    }
}
