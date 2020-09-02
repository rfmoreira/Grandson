package com.example.grandson.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.grandson.R;

public class Grandson extends AppCompatActivity {

    private Button btLogin, btCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btLogin = (Button) findViewById(R.id.btLogin);
        btCadastro = (Button) findViewById(R.id.btCadastro);
    }

    public void onClickCadastro(View V){
        Intent intent = new Intent(getApplicationContext(),Cadastro.class);
        startActivity(intent);
    }
}