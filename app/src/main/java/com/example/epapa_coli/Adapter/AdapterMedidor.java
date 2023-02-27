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
import com.example.epapa_coli.Model.GetSetMedidor;
import com.example.epapa_coli.R;
import com.example.epapa_coli.Registro_Lectura;

import java.util.ArrayList;
import java.util.List;

public class AdapterMedidor extends RecyclerView.Adapter<AdapterMedidor.ViewHolder>{

    private LayoutInflater layoutInflater;
    private static List<GetSetMedidor> data;
    private Context context;
    int id_cliente, id_tipoUsuario,id_asignacion;

    public AdapterMedidor(List<GetSetMedidor> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public void filtrar(ArrayList<GetSetMedidor> filtroContenido) {
        this.data = filtroContenido;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_medido, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GetSetMedidor pago = data.get(position);
        holder.txt_nombre.setText("N° Medidor: "+pago.getCodigo_medidor());
        holder.txt_marca.setText("Marca: "+pago.getMarca());
        holder.txt_tipo.setText("Material: "+pago.getTipo_material());
        holder.estadoMedidor.setText(pago.getEstado());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_nombre, txt_marca, txt_tipo, estadoMedidor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nombre = itemView.findViewById(R.id.nombreMedidor);
            txt_marca = itemView.findViewById(R.id.marcaMedidor);
            txt_tipo = itemView.findViewById(R.id.tipoMaterial);
            estadoMedidor = itemView.findViewById(R.id.estadoMedidor);
        }
    }


}
