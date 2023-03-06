package com.example.epapa_coli.Model;

public class GetSetMedidor {
    int id_medidor;
    String codigo_medidor, fecha_registro, marca, tipo_material, medidas, diametro, estado;

    public GetSetMedidor(int id_medidor, String codigo_medidor, String fecha_registro, String marca, String tipo_material, String medidas, String diametro, String estado) {
        this.id_medidor = id_medidor;
        this.codigo_medidor = codigo_medidor;
        this.fecha_registro = fecha_registro;
        this.marca = marca;
        this.tipo_material = tipo_material;
        this.medidas = medidas;
        this.diametro = diametro;
        this.estado = estado;
    }

    public GetSetMedidor() {
    }

    public int getId_medidor() {
        return id_medidor;
    }

    public void setId_medidor(int id_medidor) {
        this.id_medidor = id_medidor;
    }

    public String getCodigo_medidor() {
        return codigo_medidor;
    }

    public void setCodigo_medidor(String codigo_medidor) {
        this.codigo_medidor = codigo_medidor;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
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

    public String getMedidas() {
        return medidas;
    }

    public void setMedidas(String medidas) {
        this.medidas = medidas;
    }

    public String getDiametro() {
        return diametro;
    }

    public void setDiametro(String diametro) {
        this.diametro = diametro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return codigo_medidor+"-"+marca ;
    }


}
