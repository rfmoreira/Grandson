package com.example.grandson.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.grandson.R;

public class Cadastro extends AppCompatActivity {

    private Button btCliente, btColaborador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        btColaborador = (Button) findViewById(R.id.btColaborador);


    }
    public void onClickCadColab(View v){
        Intent intent = new Intent(getApplicationContext(),CadastroColaborador.class);
        startActivity(intent);
    }



}