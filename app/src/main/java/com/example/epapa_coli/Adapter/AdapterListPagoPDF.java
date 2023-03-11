package com.example.epapa_coli.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epapa_coli.CardVerFactura;
import com.example.epapa_coli.Model.GetSetPago;
import com.example.epapa_coli.R;

import java.util.List;

public class AdapterListPagoPDF extends RecyclerView.Adapter<AdapterListPagoPDF.ViewHolder>{

    private LayoutInflater layoutInflater;
    private static List<GetSetPago> data;
    private Context context;

    public AdapterListPagoPDF(List<GetSetPago> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.lista_pago_pdf, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GetSetPago pago = data.get(position);

        holder.txt_codigoPago.setText("Código de transacción: "+pago.getCodigo_transaccion());
        holder.txt_valorPago.setText("$ "+pago.getValorPago());
        holder.txt_fechaPago.setText(pago.getFecha_pago());
        holder.btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetSetPago pago = data.get(position);
                String id = String.valueOf(pago.getId_pago());
                //Toast.makeText(context, ""+id, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, CardVerFactura.class);
                i.putExtra("id", id);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_codigoPago, txt_fechaPago, txt_valorPago;
        ImageView btnVer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_codigoPago = itemView.findViewById(R.id.codigoPagoPDF);
            txt_fechaPago = itemView.findViewById(R.id.fechaPagoPDF);
            txt_valorPago = itemView.findViewById(R.id.totalPagoPDF);
            btnVer = itemView.findViewById(R.id.btnVer);
        }
    }


}
