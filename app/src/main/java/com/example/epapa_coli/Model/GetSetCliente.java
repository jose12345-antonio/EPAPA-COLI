package com.example.epapa_coli.Model;

public class GetSetCliente {

    int id_cedula, id_tipoUsuario, id_asignacion;
    String numero_cedula, nombres, apellidos, direccion, fecha_nacimiento, tipoUsuario, nombre_documento, codigo_unico, ubicacion_asignacion, latitud, longitud, codigo_medidor, marca, tipo_material, categoria;

    public GetSetCliente(int id_cedula, String numero_cedula, String nombres, String apellidos, String direccion, String fecha_nacimiento, String tipoUsuario, int id_tipoUsuario, String nombre_documento, String codigo_unico, int id_asignacion, String ubicacion_asignacion, String latitud, String longitud, String codigo_medidor, String marca, String tipo_material, String categoria) {
        this.id_cedula = id_cedula;
        this.numero_cedula = numero_cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.fecha_nacimiento = fecha_nacimiento;
        this.tipoUsuario = tipoUsuario;
        this.id_tipoUsuario = id_tipoUsuario;
        this.nombre_documento = nombre_documento;
        this.codigo_unico = codigo_unico;
        this.id_asignacion = id_asignacion;
        this.ubicacion_asignacion = ubicacion_asignacion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.codigo_medidor = codigo_medidor;
        this.marca = marca;
        this.tipo_material = tipo_material;
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombre_documento() {
        return nombre_documento;
    }

    public void setNombre_documento(String nombre_documento) {
        this.nombre_documento = nombre_documento;
    }

    public String getCodigo_unico() {
        return codigo_unico;
    }

    public void setCodigo_unico(String codigo_unico) {
        this.codigo_unico = codigo_unico;
    }

    public int getId_tipoUsuario() {
        return id_tipoUsuario;
    }

    public void setId_tipoUsuario(int id_tipoUsuario) {
        this.id_tipoUsuario = id_tipoUsuario;
    }

    public GetSetCliente() {
    }

    public int getId_asignacion() {
        return id_asignacion;
    }

    public void setId_asignacion(int id_asignacion) {
        this.id_asignacion = id_asignacion;
    }

    public String getUbicacion_asignacion() {
        return ubicacion_asignacion;
    }

    public void setUbicacion_asignacion(String ubicacion_asignacion) {
        this.ubicacion_asignacion = ubicacion_asignacion;
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


    public int getId_cedula() {
        return id_cedula;
    }

    public void setId_cedula(int id_cedula) {
        this.id_cedula = id_cedula;
    }

    public String getNumero_cedula() {
        return numero_cedula;
    }

    public void setNumero_cedula(String numero_cedula) {
        this.numero_cedula = numero_cedula;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
