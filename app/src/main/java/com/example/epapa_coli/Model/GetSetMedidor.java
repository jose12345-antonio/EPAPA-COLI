package com.example.epapa_coli.Model;

public class GetSetMedidor {
    int id_medidor, id_asignacion, tipoUsuario;
    String codigo_medidor, fecha_registro, marca, tipo_material, estado, nombres, apellidos, cedula;

    public GetSetMedidor(int id_medidor, String codigo_medidor, String fecha_registro, String marca, String tipo_material, String estado, int id_asignacion, int tipoUsuario, String nombres, String apellidos, String cedula) {
        this.id_medidor = id_medidor;
        this.codigo_medidor = codigo_medidor;
        this.fecha_registro = fecha_registro;
        this.marca = marca;
        this.tipo_material = tipo_material;
        this.estado = estado;
        this.id_asignacion = id_asignacion;
        this.tipoUsuario = tipoUsuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cedula = cedula;
    }
    public GetSetMedidor() {
    }

    public GetSetMedidor(int id, String codigo_medidor, String fecha_registro, String marca, String tipo_material, String nombre_estado) {
        this.id_medidor = id_medidor;
        this.codigo_medidor = codigo_medidor;
        this.fecha_registro = fecha_registro;
        this.marca = marca;
        this.tipo_material = tipo_material;
        this.estado = estado;

    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public int getId_asignacion() {
        return id_asignacion;
    }

    public void setId_asignacion(int id_asignacion) {
        this.id_asignacion = id_asignacion;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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
