package com.example.epapa_coli;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.epapa_coli.ui.dashboard.DashboardFragment;
import com.example.epapa_coli.ui.home.HomeFragment;
import com.example.epapa_coli.ui.notifications.NotificationsFragment;
import com.example.epapa_coli.ui.profile.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.epapa_coli.databinding.ActivityHomeUserBinding;
import com.google.android.material.navigation.NavigationBarView;

public class HomeUser extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment inicio = new HomeFragment();
    DashboardFragment dashboard = new DashboardFragment();
    NotificationsFragment notificaciones = new NotificationsFragment();
    Profile configuracion = new Profile();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);

        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.nav_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, inicio).commit();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.inicio:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, inicio).commit();
                        return true;

                    case R.id.facturas:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, dashboard).commit();
                        return true;

                    case R.id.informacion:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, notificaciones).commit();
                        return true;

                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, configuracion).commit();
                        return true;
                }
                return false;
            }
        });


    }

}