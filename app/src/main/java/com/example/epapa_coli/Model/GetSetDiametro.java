package com.example.epapa_coli.Model;

public class GetSetDiametro {

    int id;
    String nombre;

    public GetSetDiametro(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public GetSetDiametro() {
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
