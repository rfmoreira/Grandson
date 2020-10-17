package com.example.grandson.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.grandson.Api.RetrofitClientGrandson;
import com.example.grandson.Model.FormEditarSenha;
import com.example.grandson.Model.Resposta;
import com.example.grandson.R;
import com.example.grandson.Services.RetrofitServiceGrandson;
import com.example.grandson.Utils.MetodosCadastro;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarSenha extends AppCompatActivity {

    private TextInputLayout textInputConfSenha,textInputNovaSenha,textInputSenha;
    private Button bt_salvarSenha;
    private String auth;
    private FormEditarSenha formEditarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_senha);

        //TOKEN
        final SharedPreferences pref = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        auth = pref.getString("token","");

        //ETRADAS
        textInputSenha = (TextInputLayout) findViewById(R.id.textInputSenha);
        textInputNovaSenha = (TextInputLayout) findViewById(R.id.textInputNovaSenha);
        textInputConfSenha = (TextInputLayout) findViewById(R.id.textInputConfSenha);

        //BOTAO
        bt_salvarSenha = (Button) findViewById(R.id.bt_salvarSenha);


        bt_salvarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarSenha();
            }
        });

    }

    private void salvarSenha() {

        String senhaAtual = textInputSenha.getEditText().getText().toString();
        String senhaNova = textInputNovaSenha.getEditText().getText().toString();
        String confSenha = textInputConfSenha.getEditText().getText().toString();

        if(MetodosCadastro.isCampoVazio(textInputSenha.getEditText().getText().toString())){
            textInputSenha.setError("Campo Vazio");
        }else if(MetodosCadastro.isCampoVazio(textInputNovaSenha.getEditText().getText().toString())){
            textInputNovaSenha.setError("Campo Vazio");
            textInputSenha.setError(null);
        }else if(MetodosCadastro.isCampoVazio(textInputConfSenha.getEditText().getText().toString())){
            textInputConfSenha.setError("Campo Vazio");
            textInputNovaSenha.setError(null);
        }else
            if(senhaNova.equals(confSenha) ){
            formEditarSenha = new FormEditarSenha(senhaNova, senhaAtual);
            //Instanciando a interface
            RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
            //Passando os dados para consulta
            Call<Resposta> call = restService.alterarSenha("Bearer "+auth, formEditarSenha);

            call.enqueue(new Callback<Resposta> () {
                @Override
                public void onResponse(Call<Resposta>  call, Response<Resposta>  response) {

                    if(response.isSuccessful()){

                        Toast.makeText(EditarSenha.this, response.body().getMensagem(), Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Log.i("Erro", response.message());
                    }

                }

                @Override
                public void onFailure(Call<Resposta>  call, Throwable t) {
                    Log.i("Falha", t.getMessage());
                }
            });
        }else {
            textInputNovaSenha.setError("Senhas não coincidem");
            textInputSenha.setError(null);
            textInputConfSenha.setError(null);
        }
    }
}