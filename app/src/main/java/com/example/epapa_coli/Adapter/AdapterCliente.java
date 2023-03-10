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
import com.example.epapa_coli.AsigMedidor;
import com.example.epapa_coli.ListaMedidor;
import com.example.epapa_coli.Lista_medidores;
import com.example.epapa_coli.Model.GetSetCliente;
import com.example.epapa_coli.Model.GetSetPago;
import com.example.epapa_coli.Model.GetSetTipoUsuario;
import com.example.epapa_coli.R;
import com.example.epapa_coli.RegisterCode;
import com.example.epapa_coli.RegisterUsers;
import com.example.epapa_coli.Registro_Lectura;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdapterCliente extends RecyclerView.Adapter<AdapterCliente.ViewHolder>{

    private LayoutInflater layoutInflater;
    private static List<GetSetCliente> data;
    private Context context;
    int id_cliente, id_cliente2, id_tipoUsuario,id_asignacion;
    String nombres, categoria, nombredocumento, tipoUsuario, ubicacion;
    int id;

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
                String nombre2 = pago.getNombres()+" "+pago.getApellidos();
                String cedula2 = pago.getNumero_cedula();
                Bundle code = new Bundle();
                code.putString("id", String.valueOf(id_asignacion));
                code.putString("id_tipoUsuario", String.valueOf(id_tipoUsuario));
                code.putString("nombres", nombre2);
                code.putString("nombres", cedula2);
                Intent i = new Intent(context, Registro_Lectura.class);
                i.putExtras(code);
                context.startActivity(i);
            }
        });

        holder.lnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView txtnombres, txttipoDocumento, txttipoCategoria, txttipoUsuario ;
                Button txtMedidor;
                AutoCompleteTextView tipoUserModal;
                Button btn;

                View alertCustomDialog = LayoutInflater.from(context).inflate(R.layout.dialog_cliente_medidor, null);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                id_cliente = pago.getId_cedula();
                nombres = pago.getNombres()+" "+pago.getApellidos();
                nombredocumento = pago.getNumero_cedula();
                tipoUsuario = pago.getTipoUsuario();
                categoria = pago.getCategoria();
                //ubicacion = "La dirección del medidor está en "+pago.getUbicacion_asignacion()+"\nSus coordenadas son "+pago.getLatitud()+","+pago.getLongitud()+"" +
                       // "\nMarca del medidor "+pago.getMarca()+" con medidas de "+pago.getMedidas();
                alertDialog.setView(alertCustomDialog);

                txtnombres = alertCustomDialog.findViewById(R.id.nombreClienteDialog);
                txtnombres.setText(nombres);
                txttipoDocumento = alertCustomDialog.findViewById(R.id.tipoDocumentoDialog);
                txttipoDocumento.setText(nombredocumento);
                txttipoCategoria = alertCustomDialog.findViewById(R.id.categoriaDialog);
                txttipoCategoria.setText(categoria);
                txttipoUsuario = alertCustomDialog.findViewById(R.id.tipoUsuarioDialog);
                txttipoUsuario.setText(tipoUsuario);
                txtMedidor = alertCustomDialog.findViewById(R.id.ubicacionDialog);
                txtMedidor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle code = new Bundle();
                        code.putString("id", String.valueOf(id_cliente));
                        code.putString("nombres", String.valueOf(nombres));
                        code.putString("cedula", String.valueOf(nombredocumento));
                        Intent i = new Intent(context, Lista_medidores.class);
                        i.putExtras(code);
                        context.startActivity(i);
                    }
                });
                //txtMedidor.setText(ubicacion);
                tipoUserModal = alertCustomDialog.findViewById(R.id.tipoUserModal);
                String URL = "https://epapa-coli.es/tesis-epapacoli/tipoUsuario.php";
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            final ArrayList<GetSetTipoUsuario> listRol = new ArrayList<GetSetTipoUsuario>();

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("tipoUsuario");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                GetSetTipoUsuario p = new GetSetTipoUsuario();
                                p.setId_tipoUsuario(jsonObject1.getInt("id_tipoUsuario"));
                                p.setNombre_tipoUsuario(jsonObject1.getString("nombre_tipoUsuario"));
                                listRol.add(p);

                            }
                            ArrayAdapter<GetSetTipoUsuario> tipoRol = new ArrayAdapter<GetSetTipoUsuario>(context, android.R.layout.simple_dropdown_item_1line, listRol);
                            tipoUserModal.setAdapter(tipoRol);
                            tipoUserModal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    id = listRol.get(i).getId_tipoUsuario();
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

                final RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
                requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
                    @Override
                    public void onRequestFinished(Request<Object> request) {
                        requestQueue.getCache().clear();
                    }
                });
                btn = alertCustomDialog.findViewById(R.id.btnActualizarTipo);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final RequestQueue queue = Volley.newRequestQueue(context);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://epapa-coli.es/tesis-epapacoli/update_tipoUsuario.php?tipo="+id+"&id="+id_cliente, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(context, "Actualizado con éxito", Toast.LENGTH_SHORT).show();

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
                final AlertDialog alertDialog1 = alertDialog.create();
                alertDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog1.show();
            }
        });
        holder.registrarNuevMedida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_cliente2 = pago.getId_cedula();
                Bundle code = new Bundle();
                code.putString("id", String.valueOf(id_cliente2));
                Intent i = new Intent(context, AsigMedidor.class);
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
        TextView txt_nombres, txt_cedula, txt_direccion, registrarLectura, registrarNuevMedida;
        LinearLayout lnCliente;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_cedula = itemView.findViewById(R.id.cedulaCliente);
            txt_nombres = itemView.findViewById(R.id.nombreCliente);
            txt_direccion = itemView.findViewById(R.id.direccionCliente);
            registrarLectura = itemView.findViewById(R.id.registrarLectura);
            registrarNuevMedida = itemView.findViewById(R.id.registrarNuevMedida);
            lnCliente = itemView.findViewById(R.id.lnCliente);
        }
    }



}
