package com.example.epapa_coli.Model;

public class GetSetFactura {

    int id;
    String fecha, total, estado_pago, numero_medidor;

    public GetSetFactura(int id, String fecha, String total, String estado_pago, String numero_medidor) {
        this.id = id;
        this.fecha = fecha;
        this.total = total;
        this.estado_pago = estado_pago;
        this.numero_medidor = numero_medidor;
    }

    public GetSetFactura() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getEstado_pago() {
        return estado_pago;
    }

    public void setEstado_pago(String estado_pago) {
        this.estado_pago = estado_pago;
    }

    public String getNumero_medidor() {
        return numero_medidor;
    }

    public void setNumero_medidor(String numero_medidor) {
        this.numero_medidor = numero_medidor;
    }
}
