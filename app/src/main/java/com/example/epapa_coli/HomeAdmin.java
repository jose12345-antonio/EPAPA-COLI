package com.example.epapa_coli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.epapa_coli.FragmentAdmin.Clientes;
import com.example.epapa_coli.FragmentAdmin.InicioAdmin;
import com.example.epapa_coli.FragmentAdmin.Pago;
import com.example.epapa_coli.ui.dashboard.DashboardFragment;
import com.example.epapa_coli.ui.home.HomeFragment;
import com.example.epapa_coli.ui.notifications.NotificationsFragment;
import com.example.epapa_coli.ui.profile.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeAdmin extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    InicioAdmin inicio = new InicioAdmin();
    Clientes cliente = new Clientes();
    Pago pago = new Pago();
    Profile configuracion = new Profile();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        getSupportActionBar().hide();
        bottomNavigationView = findViewById(R.id.nav_viewadmin);
        getSupportFragmentManager().beginTransaction().replace(R.id.containeradmin, inicio).commit();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.inicioAdmin:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containeradmin, inicio).commit();
                        return true;

                    case R.id.clientes:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containeradmin, cliente).commit();
                        return true;

                    case R.id.pagos:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containeradmin, pago).commit();
                        return true;

                    case R.id.profileAdmin:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containeradmin, configuracion).commit();
                        return true;
                }
                return false;
            }
        });


    }
}