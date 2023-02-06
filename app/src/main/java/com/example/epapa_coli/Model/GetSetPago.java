package com.example.epapa_coli.Model;

import android.accessibilityservice.GestureDescription;

public class GetSetPago {

    int id_pago;
    Double valorPago;
    String codigo_transaccion, fecha_pago, fecha_factura, razon_social;

    public GetSetPago(int id_pago, Double valorPago, String codigo_transaccion, String fecha_pago, String fecha_factura, String razon_social) {
        this.id_pago = id_pago;
        this.valorPago = valorPago;
        this.codigo_transaccion = codigo_transaccion;
        this.fecha_pago = fecha_pago;
        this.fecha_factura = fecha_factura;
        this.razon_social = razon_social;
    }

    public GetSetPago() {
    }

    public int getId_pago() {
        return id_pago;
    }

    public void setId_pago(int id_pago) {
        this.id_pago = id_pago;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    public String getCodigo_transaccion() {
        return codigo_transaccion;
    }

    public void setCodigo_transaccion(String codigo_transaccion) {
        this.codigo_transaccion = codigo_transaccion;
    }

    public String getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(String fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public String getFecha_factura() {
        return fecha_factura;
    }

    public void setFecha_factura(String fecha_factura) {
        this.fecha_factura = fecha_factura;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }
}
