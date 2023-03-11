package com.example.epapa_coli.Model;

public class GetSetFacturaHome {

    String id_pago, codigotransaccion, id_transaccion, id_factura, estado_pago, valor_lectura, consumo_m3, estado_lectura;
    String valorPago, fechaPago, fechaFactura, totalFactura, fecha_lectura, numero_cedula, codigo_unico, nombres;

    public GetSetFacturaHome(String id_pago, String codigotransaccion, String id_transaccion, String id_factura, String estado_pago, String valor_lectura, String consumo_m3, String estado_lectura, String valorPago, String fechaPago, String fechaFactura, String totalFactura, String fecha_lectura, String numero_cedula, String codigo_unico,String nombres) {
        this.id_pago = id_pago;
        this.codigotransaccion = codigotransaccion;
        this.id_transaccion = id_transaccion;
        this.id_factura = id_factura;
        this.estado_pago = estado_pago;
        this.valor_lectura = valor_lectura;
        this.consumo_m3 = consumo_m3;
        this.estado_lectura = estado_lectura;
        this.valorPago = valorPago;
        this.fechaPago = fechaPago;
        this.fechaFactura = fechaFactura;
        this.totalFactura = totalFactura;
        this.fecha_lectura = fecha_lectura;
        this.numero_cedula = numero_cedula;
        this.codigo_unico = codigo_unico;
        this.nombres = nombres;
    }



    public GetSetFacturaHome() {
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getId_pago() {
        return id_pago;
    }

    public void setId_pago(String id_pago) {
        this.id_pago = id_pago;
    }

    public String getCodigotransaccion() {
        return codigotransaccion;
    }

    public void setCodigotransaccion(String codigotransaccion) {
        this.codigotransaccion = codigotransaccion;
    }

    public String getId_transaccion() {
        return id_transaccion;
    }

    public void setId_transaccion(String id_transaccion) {
        this.id_transaccion = id_transaccion;
    }

    public String getId_factura() {
        return id_factura;
    }

    public void setId_factura(String id_factura) {
        this.id_factura = id_factura;
    }

    public String getEstado_pago() {
        return estado_pago;
    }

    public void setEstado_pago(String estado_pago) {
        this.estado_pago = estado_pago;
    }

    public String getValor_lectura() {
        return valor_lectura;
    }

    public void setValor_lectura(String valor_lectura) {
        this.valor_lectura = valor_lectura;
    }

    public String getConsumo_m3() {
        return consumo_m3;
    }

    public void setConsumo_m3(String consumo_m3) {
        this.consumo_m3 = consumo_m3;
    }

    public String getEstado_lectura() {
        return estado_lectura;
    }

    public void setEstado_lectura(String estado_lectura) {
        this.estado_lectura = estado_lectura;
    }

    public String getValorPago() {
        return valorPago;
    }

    public void setValorPago(String valorPago) {
        this.valorPago = valorPago;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(String totalFactura) {
        this.totalFactura = totalFactura;
    }

    public String getFecha_lectura() {
        return fecha_lectura;
    }

    public void setFecha_lectura(String fecha_lectura) {
        this.fecha_lectura = fecha_lectura;
    }

    public String getNumero_cedula() {
        return numero_cedula;
    }

    public void setNumero_cedula(String numero_cedula) {
        this.numero_cedula = numero_cedula;
    }

    public String getCodigo_unico() {
        return codigo_unico;
    }

    public void setCodigo_unico(String codigo_unico) {
        this.codigo_unico = codigo_unico;
    }
}
