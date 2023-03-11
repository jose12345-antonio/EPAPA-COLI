package com.example.epapa_coli.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epapa_coli.DetallePago;
import com.example.epapa_coli.Facturas_Generadas;
import com.example.epapa_coli.Model.GetSetFactura;
import com.example.epapa_coli.Model.GetSetPago;
import com.example.epapa_coli.R;
import com.example.epapa_coli.Registro_Lectura;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterListFactura extends RecyclerView.Adapter<AdapterListFactura.ViewHolder>{

    private LayoutInflater layoutInflater;
    private static List<GetSetFactura> data;
    private Facturas_Generadas context;

    public AdapterListFactura(List<GetSetFactura> data, Facturas_Generadas context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.lista_facturas, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GetSetFactura pago = data.get(position);

        holder.txt_codigoFactu.setText("Nro°. "+pago.getNumero_medidor());
        holder.txt_valorFactu.setText("$ "+obtieneDosDecimales(Double.parseDouble(pago.getTotal())));
        holder.txt_fechaFactu.setText(pago.getFecha());
        holder.ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = pago.getId();
                Bundle code = new Bundle();
                code.putString("id", String.valueOf(id));

                Intent i = new Intent(context, DetallePago.class);
                i.putExtras(code);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_codigoFactu, txt_fechaFactu, txt_valorFactu;
        LinearLayout ln;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_codigoFactu = itemView.findViewById(R.id.codigoFactura);
            txt_fechaFactu = itemView.findViewById(R.id.fechaFactu);
            txt_valorFactu = itemView.findViewById(R.id.totalFactu);
            ln = itemView.findViewById(R.id.ln);
        }
    }

    private String obtieneDosDecimales(double valor){
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(2); //Define 2 decimales.
        return format.format(valor);
    }
}
