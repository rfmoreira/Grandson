package com.example.grandson.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.grandson.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeCliente extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cliente);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.bt_home); // Setando botao Home como selecionado
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNaviSelected); // Setando funcionalidade click nos botoes
       getSupportFragmentManager().beginTransaction().replace(R.id.frame_home, new HomeClienteFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNaviSelected = new
            BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;
                    switch (item.getItemId()){
                        case R.id.bt_perf:
                            fragment = new PerfilClienteFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,fragment).commit();
                            break;
                        case R.id.bt_home:
                            fragment = new HomeClienteFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,fragment).commit();
                            break;
                        case R.id.bt_servAgendado:
                            fragment = new ServicosSolicitadosFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,fragment).commit();
                            break;
                        case R.id.bt_servConcluido:
                            fragment = new ServicoConcluidoFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,fragment).commit();
                            break;
                    }

                    return true;

                }
            };


}