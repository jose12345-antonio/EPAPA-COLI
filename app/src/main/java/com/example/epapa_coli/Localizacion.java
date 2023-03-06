package com.example.epapa_coli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Localizacion extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener{

    GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacion);
        getSupportActionBar().hide();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(Localizacion.this);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        this.mMap.setOnMapClickListener(this);
        this.mMap.setOnMapLongClickListener(this);

        LatLng mexico = new LatLng(-1.5460857, -80.0094081);
        mMap.addMarker(new MarkerOptions().position(mexico).title("Empresa Pública de Agua Potable y Alcantarillado Colimes"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mexico));
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        mMap.clear();
        LatLng colimes = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(colimes).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(colimes));
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

        mMap.clear();
        LatLng colimes = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(colimes).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(colimes));
    }
}