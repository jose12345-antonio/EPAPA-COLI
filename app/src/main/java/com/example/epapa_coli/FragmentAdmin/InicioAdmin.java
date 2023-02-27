package com.example.epapa_coli.FragmentAdmin;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Align;
import com.anychart.enums.Anchor;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.epapa_coli.ListaMedidor;
import com.example.epapa_coli.Preferences;
import com.example.epapa_coli.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class InicioAdmin extends Fragment {

    AnyChartView anyChartView, anyChartViewLine;
    TextView txtNombre;
    String user;
    CardView cardMedidor;
    TextView txtTotal;
    Double total;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio_admin, container, false);
        user =  Preferences.obtenerPreferenceString(getContext(), Preferences.PREFERENCE_USUARIO_LOGIN);
        txtNombre = view.findViewById(R.id.nombreAdmin);
        txtTotal = view.findViewById(R.id.txtTotal);
        //anyChartView = view.findViewById(R.id.any_chart_view);
        anyChartViewLine = view.findViewById(R.id.any_chart_view_line);
        obtenerUsuario();
        ObtenerTotal();
        Pie pie = AnyChart.pie();
        Cartesian cartesian = AnyChart.line();
        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(getContext(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        //ChartCircle(pie);
        ChartLine(cartesian);

        cardMedidor = view.findViewById(R.id.cardMedidor);
        cardMedidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ListaMedidor.class));
            }
        });

        return view;
    }
/*
    public void ChartCircle(Pie pie){
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Apples", 6371664));
        data.add(new ValueDataEntry("Pears", 789622));
        data.add(new ValueDataEntry("Bananas", 7216301));
        data.add(new ValueDataEntry("Grapes", 1486621));
        data.add(new ValueDataEntry("Oranges", 1200000));

        pie.data(data);

        pie.title("Fruits imported in 2015 (in kg)");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Retail channels")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);
    }
*/
    public void ChartLine(Cartesian cartesian){
        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("PAGOS GENERADOS");

        cartesian.yAxis(0).title("Valores");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();
        seriesData.add(new CustomDataEntry("1", 3.6));
        seriesData.add(new CustomDataEntry("2", 7.1));
        seriesData.add(new CustomDataEntry("3", 8.5));
        seriesData.add(new CustomDataEntry("4", 9.2));
        seriesData.add(new CustomDataEntry("5", 10.1));
        seriesData.add(new CustomDataEntry("6", 11.6));
        seriesData.add(new CustomDataEntry("7", 16.4));
        seriesData.add(new CustomDataEntry("8", 18.0));
        seriesData.add(new CustomDataEntry("9", 13.2));
        seriesData.add(new CustomDataEntry("10", 12.0));
        seriesData.add(new CustomDataEntry("11", 3.2));
        seriesData.add(new CustomDataEntry("12", 4.1));
        seriesData.add(new CustomDataEntry("13", 6.3));
        seriesData.add(new CustomDataEntry("14", 9.4));
        seriesData.add(new CustomDataEntry("15", 11.5));
        seriesData.add(new CustomDataEntry("16", 13.5));
        seriesData.add(new CustomDataEntry("17", 14.8));
        seriesData.add(new CustomDataEntry("18", 16.6));
        seriesData.add(new CustomDataEntry("19", 18.1));
        seriesData.add(new CustomDataEntry("20", 17.0));
        seriesData.add(new CustomDataEntry("21", 16.6));
        seriesData.add(new CustomDataEntry("22", 14.1));
        seriesData.add(new CustomDataEntry("23", 15.7));
        seriesData.add(new CustomDataEntry("24", 12.0));

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Pagos");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);
        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartViewLine.setChart(cartesian);
    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value) {
            super(x, value);
        }

    }

    private void obtenerUsuario() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://epapa-coli.es/tesis-epapacoli/listarPerfilUsuario.php?correo="+user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("usuario");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        txtNombre.setText("Hola, "+jsonObject1.getString("nombres"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
            }
        });
    }
    private void ObtenerTotal() {
        String URL3 = "https://epapa-coli.es/tesis-epapacoli/ObtenerTotalPagoDiario.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("totalDia");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        total = jsonObject1.getDouble("total");
                        System.out.println("$ "+obtieneDosDecimales(total));
                    }
                    txtTotal.setText("$ "+obtieneDosDecimales(total));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
            }
        });
    }

    private String obtieneDosDecimales(double valor){
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(2); //Define 2 decimales.
        return format.format(valor);
    }

}

