package com.example.epapa_coli.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.example.epapa_coli.DetallePago;
import com.example.epapa_coli.ListaMedidor;
import com.example.epapa_coli.Model.GetSetCliente;
import com.example.epapa_coli.Model.GetSetEstado;
import com.example.epapa_coli.Model.GetSetMedidor;
import com.example.epapa_coli.Model.GetSetTipoUsuario;
import com.example.epapa_coli.R;
import com.example.epapa_coli.Registro_Lectura;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdapterMedidor extends RecyclerView.Adapter<AdapterMedidor.ViewHolder>{

    private LayoutInflater layoutInflater;
    private static List<GetSetMedidor> data;
    private ListaMedidor context;
    int id_cliente, id_tipoUsuario,id_asignacion, id_estadoMedidor, idmedidor;

    public AdapterMedidor(List<GetSetMedidor> data, ListaMedidor context) {
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

        holder.lyMedidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteTextView estadomedidor;
                Button btn;

                idmedidor = pago.getId_medidor();

                View alertCustomDialog = LayoutInflater.from(context).inflate(R.layout.dialog_estado_medidor, null);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                alertDialog.setView(alertCustomDialog);

                estadomedidor = alertCustomDialog.findViewById(R.id.estadoMedidorModal);
                btn = alertCustomDialog.findViewById(R.id.btnActualizarMedidor);

                String URL = "https://epapa-coli.es/tesis-epapacoli/listar_estado.php";
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            final ArrayList<GetSetEstado> listRol = new ArrayList<GetSetEstado>();

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("estado");
                                System.out.println(jsonArray);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                GetSetEstado p = new GetSetEstado();
                                p.setId(jsonObject1.getInt("id_estado"));
                                p.setNombre_estado(jsonObject1.getString("nombre_medidor"));
                                listRol.add(p);

                            }
                            ArrayAdapter<GetSetEstado> tipoRol = new ArrayAdapter<GetSetEstado>(context, android.R.layout.simple_dropdown_item_1line, listRol);
                            estadomedidor.setAdapter(tipoRol);
                            estadomedidor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    id_estadoMedidor = listRol.get(i).getId();
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final RequestQueue queue = Volley.newRequestQueue(context);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://epapa-coli.es/tesis-epapacoli/update_estadoMedidor.php?estado="+id_estadoMedidor+"&id="+idmedidor, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(context, "Actualizado con éxito", Toast.LENGTH_SHORT).show();
                                context.finish();
                                //sendMail();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Toast.makeText(getApplicationContext(), "No se pudo registrar", Toast.LENGTH_SHORT).show();
                            }
                        });
                        queue.add(stringRequest);
                        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
                            @Override
                            public void onRequestFinished(Request<Object> request) {
                                queue.getCache().clear();
                            }
                        });
                    }
                });

                final RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
                requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
                    @Override
                    public void onRequestFinished(Request<Object> request) {
                        requestQueue.getCache().clear();
                    }
                });

                final AlertDialog alertDialog1 = alertDialog.create();
                alertDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog1.show();

            }

        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_nombre, txt_marca, txt_tipo, estadoMedidor;
        LinearLayout lyMedidor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nombre = itemView.findViewById(R.id.nombreMedidor);
            txt_marca = itemView.findViewById(R.id.marcaMedidor);
            txt_tipo = itemView.findViewById(R.id.tipoMaterial);
            estadoMedidor = itemView.findViewById(R.id.estadoMedidor);
            lyMedidor = itemView.findViewById(R.id.lyMedidor);
        }
    }


}
