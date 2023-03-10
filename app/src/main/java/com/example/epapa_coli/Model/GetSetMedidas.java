package com.example.epapa_coli.Model;

public class GetSetMedidas {

    int id;
    String nombre;

    public GetSetMedidas(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public GetSetMedidas() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
