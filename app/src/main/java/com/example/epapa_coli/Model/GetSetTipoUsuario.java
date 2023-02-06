package com.example.epapa_coli.Model;

public class GetSetTipoUsuario {
    int id_tipoUsuario;
    String nombre_tipoUsuario;

    public GetSetTipoUsuario(int id_tipoUsuario, String nombre_tipoUsuario) {
        this.id_tipoUsuario = id_tipoUsuario;
        this.nombre_tipoUsuario = nombre_tipoUsuario;
    }

    public GetSetTipoUsuario() {
    }

    public int getId_tipoUsuario() {
        return id_tipoUsuario;
    }

    public void setId_tipoUsuario(int id_tipoUsuario) {
        this.id_tipoUsuario = id_tipoUsuario;
    }

    public String getNombre_tipoUsuario() {
        return nombre_tipoUsuario;
    }

    public void setNombre_tipoUsuario(String nombre_tipoUsuario) {
        this.nombre_tipoUsuario = nombre_tipoUsuario;
    }

    @Override
    public String toString() {
        return nombre_tipoUsuario;
    }
}
