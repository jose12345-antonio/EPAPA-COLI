package com.example.epapa_coli.Model;

public class GetSetMedidorCliente {
    int id_asignacion;
    String ubicacion, latitud, longitud, codigo_medidor, marca, tipo_material;

    public GetSetMedidorCliente(int id_asignacion, String ubicacion, String latitud, String longitud, String codigo_medidor, String marca, String tipo_material) {
        this.id_asignacion = id_asignacion;
        this.ubicacion = ubicacion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.codigo_medidor = codigo_medidor;
        this.marca = marca;
        this.tipo_material = tipo_material;

    }

    public GetSetMedidorCliente() {
    }

    public int getId_asignacion() {
        return id_asignacion;
    }

    public void setId_asignacion(int id_asignacion) {
        this.id_asignacion = id_asignacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getCodigo_medidor() {
        return codigo_medidor;
    }

    public void setCodigo_medidor(String codigo_medidor) {
        this.codigo_medidor = codigo_medidor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipo_material() {
        return tipo_material;
    }

    public void setTipo_material(String tipo_material) {
        this.tipo_material = tipo_material;
    }


    @Override
    public String toString() {
        return codigo_medidor+"-"+marca ;
    }


}
