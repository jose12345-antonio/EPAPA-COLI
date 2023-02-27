package com.example.epapa_coli.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.epapa_coli.Model.GetSetCliente;
import com.example.epapa_coli.Model.GetSetPago;
import com.example.epapa_coli.R;
import com.example.epapa_coli.RegisterCode;
import com.example.epapa_coli.RegisterUsers;
import com.example.epapa_coli.Registro_Lectura;

import java.util.ArrayList;
import java.util.List;

public class AdapterCliente extends RecyclerView.Adapter<AdapterCliente.ViewHolder>{

    private LayoutInflater layoutInflater;
    private static List<GetSetCliente> data;
    private Context context;
    int id_cliente, id_tipoUsuario,id_asignacion;

    public AdapterCliente(List<GetSetCliente> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public void filtrar(ArrayList<GetSetCliente> filtroContenido) {
        this.data = filtroContenido;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_cliente, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GetSetCliente pago = data.get(position);
        holder.txt_cedula.setText("N° Cédula: "+pago.getNumero_cedula());
        holder.txt_direccion.setText("Dirección: "+pago.getDireccion());
        holder.txt_nombres.setText(pago.getNombres()+" "+pago.getApellidos());
        holder.registrarLectura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_cliente = pago.getId_cedula();
                id_asignacion = pago.getId_asignacion();
                id_tipoUsuario = pago.getId_tipoUsuario();
                Bundle code = new Bundle();
                code.putString("id", String.valueOf(id_asignacion));
                code.putString("id_tipoUsuario", String.valueOf(id_tipoUsuario));
                Intent i = new Intent(context, Registro_Lectura.class);
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
        TextView txt_nombres, txt_cedula, txt_direccion, registrarLectura;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_cedula = itemView.findViewById(R.id.cedulaCliente);
            txt_nombres = itemView.findViewById(R.id.nombreCliente);
            txt_direccion = itemView.findViewById(R.id.direccionCliente);
            registrarLectura = itemView.findViewById(R.id.registrarLectura);
        }
    }


}
