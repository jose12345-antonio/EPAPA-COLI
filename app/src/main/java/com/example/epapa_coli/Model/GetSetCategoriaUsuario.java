package com.example.epapa_coli.Model;

public class GetSetCategoriaUsuario {
    int id_categoriaUsuario;
    String nombre_categoriaUsuario;

    public GetSetCategoriaUsuario(int id_categoriaUsuario, String nombre_categoriaUsuario) {
        this.id_categoriaUsuario = id_categoriaUsuario;
        this.nombre_categoriaUsuario = nombre_categoriaUsuario;
    }

    public GetSetCategoriaUsuario() {
    }

    public int getId_categoriaUsuario() {
        return id_categoriaUsuario;
    }

    public void setId_categoriaUsuario(int id_categoriaUsuario) {
        this.id_categoriaUsuario = id_categoriaUsuario;
    }

    public String getNombre_categoriaUsuario() {
        return nombre_categoriaUsuario;
    }

    public void setNombre_categoriaUsuario(String nombre_categoriaUsuario) {
        this.nombre_categoriaUsuario = nombre_categoriaUsuario;
    }

    @Override
    public String toString() {
        return nombre_categoriaUsuario;
    }
}
