package com.example.epapa_coli.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epapa_coli.Model.GetSetCliente;
import com.example.epapa_coli.Model.GetSetFacturaHome;
import com.example.epapa_coli.R;

import java.util.List;

public class AdapterFacturaAdmin extends RecyclerView.Adapter<AdapterFacturaAdmin.ViewHolder>{

    private LayoutInflater layoutInflater;
    private static List<GetSetFacturaHome> data;
    private Context context;

    public AdapterFacturaAdmin(List<GetSetFacturaHome> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_pago, null);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GetSetFacturaHome pago = data.get(position);
        holder.txt_total.setText("Total pago: "+pago.getValorPago());
        String estado = pago.getEstado_pago();
        if(estado.equals("0")){
            holder.txt_estado.setText("Pago pendiente");
            holder.txt_estado.setTextColor(R.color.warning);
        }else if(estado.equals("1")){
            holder.txt_estado.setText("Pago realizado");
            holder.txt_estado.setTextColor(R.color.success);
        }

        holder.txt_nombres.setText(pago.getNombres());
        holder.txt_consumo.setText("Consumo m3: "+pago.getConsumo_m3());
        holder.txt_valor.setText("lectura: "+pago.getValor_lectura());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_nombres, txt_estado, txt_total, txt_consumo, txt_valor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nombres = itemView.findViewById(R.id.nombreClientePago);
            txt_consumo = itemView.findViewById(R.id.consumo_lecturam3);
            txt_valor = itemView.findViewById(R.id.valor_lecturam3);
            txt_total = itemView.findViewById(R.id.totallectura);
            txt_estado = itemView.findViewById(R.id.estado_lectura);
        }
    }


}
