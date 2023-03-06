package com.example.epapa_coli.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.epapa_coli.ListaMedidor;
import com.example.epapa_coli.Model.GetSetEstado;
import com.example.epapa_coli.Model.GetSetMedidor;
import com.example.epapa_coli.Model.GetSetMedidorCliente;
import com.example.epapa_coli.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdapterMedidorCliente extends RecyclerView.Adapter<AdapterMedidorCliente.ViewHolder>{

    private LayoutInflater layoutInflater;
    private static List<GetSetMedidorCliente> data;
    private Context context;

    public AdapterMedidorCliente(List<GetSetMedidorCliente> data, Context context) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_medidor_cliente, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GetSetMedidorCliente pago = data.get(position);
        holder.txt_nombre.setText("N° Medidor: "+pago.getCodigo_medidor());
        holder.txt_marca.setText("Marca: "+pago.getMarca());
        holder.txt_tipo.setText("Material: "+pago.getTipo_material());
        holder.txt_ubicacion.setText("Ubicación: "+pago.getUbicacion());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_nombre, txt_marca, txt_tipo, txt_ubicacion;
        LinearLayout lyMedidor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nombre = itemView.findViewById(R.id.nombreMedidor);
            txt_marca = itemView.findViewById(R.id.marcaMedidor);
            txt_tipo = itemView.findViewById(R.id.tipoMaterial);
            txt_ubicacion = itemView.findViewById(R.id.ubicacion);
            lyMedidor = itemView.findViewById(R.id.lyMedidor);
        }
    }


}
