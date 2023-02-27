package com.example.epapa_coli.Model;

public class GetSetDocumento {

    int id_documento;

    String nombreDocumento;

    public GetSetDocumento(int id_documento, String nombreDocumento) {
        this.id_documento = id_documento;
        this.nombreDocumento = nombreDocumento;
    }

    public GetSetDocumento() {
    }

    public int getId_documento() {
        return id_documento;
    }

    public void setId_documento(int id_documento) {
        this.id_documento = id_documento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    @Override
    public String toString() {
        return nombreDocumento;
    }
}
