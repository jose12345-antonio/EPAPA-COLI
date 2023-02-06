package com.example.epapa_coli.Model;

public class GetSetFacturaHome {

    int id_factura;
    String fecha_factura, total_factura, estado_pago, valor_lectura, consumo_m3, fecha_lectura, estado_lectura;

    public GetSetFacturaHome(int id_factura, String fecha_factura, String total_factura, String estado_pago, String valor_lectura, String consumo_m3, String fecha_lectura, String estado_lectura) {
        this.id_factura = id_factura;
        this.fecha_factura = fecha_factura;
        this.total_factura = total_factura;
        this.estado_pago = estado_pago;
        this.valor_lectura = valor_lectura;
        this.consumo_m3 = consumo_m3;
        this.fecha_lectura = fecha_lectura;
        this.estado_lectura = estado_lectura;
    }

    public GetSetFacturaHome() {
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public String getFecha_factura() {
        return fecha_factura;
    }

    public void setFecha_factura(String fecha_factura) {
        this.fecha_factura = fecha_factura;
    }

    public String getTotal_factura() {
        return total_factura;
    }

    public void setTotal_factura(String total_factura) {
        this.total_factura = total_factura;
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

    public String getFecha_lectura() {
        return fecha_lectura;
    }

    public void setFecha_lectura(String fecha_lectura) {
        this.fecha_lectura = fecha_lectura;
    }

    public String getEstado_lectura() {
        return estado_lectura;
    }

    public void setEstado_lectura(String estado_lectura) {
        this.estado_lectura = estado_lectura;
    }
}
