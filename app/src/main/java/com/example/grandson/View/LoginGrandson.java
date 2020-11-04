package com.example.grandson.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.grandson.Api.RetrofitClientGrandson;
import com.example.grandson.Model.Auth;
import com.example.grandson.Model.FormLogin;
import com.example.grandson.R;
import com.example.grandson.Services.RetrofitServiceGrandson;
import com.example.grandson.Utils.MetodosCadastro;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginGrandson extends AppCompatActivity {

    private Button btLogin;
    private Auth auth;
    private boolean isLogin = false;
    private TextInputLayout editTextUsuario, editTextSenha;
    private FormLogin formLogin;
    private ProgressDialog progressDialog;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_grandson);

        btLogin = (Button) findViewById(R.id.btLogin);
        editTextUsuario = (TextInputLayout) findViewById(R.id.editTextUsuario);
        editTextSenha = (TextInputLayout) findViewById(R.id.editTextSenha);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent intent = new Intent(LoginGrandson.this, HomeCliente.class);
                //intent.putExtra("nome", cliente.getNome());
                //startActivity(intent);
                //getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                verificaLogin();
                //handler.postDelayed(executa(),1000);
            }
        });
    }

    public void verificaLogin(){

        if(MetodosCadastro.isCampoVazio(editTextUsuario.getEditText().getText().toString())){
            editTextUsuario.getEditText().setError("Campo  Vazio !");
            editTextUsuario.getEditText().requestFocus();
        }else {

            if(!MetodosCadastro.isEmail(editTextUsuario.getEditText().getText().toString())){
                editTextUsuario.getEditText().setError("Usuário Inválido !");
                editTextUsuario.getEditText().requestFocus();
            }else {
                if(MetodosCadastro.isCampoVazio(editTextSenha.getEditText().getText().toString())){
                    editTextSenha.setError("Campo Vazio !");
                    editTextSenha.getEditText().requestFocus();
                }else {
                    formLogin =new FormLogin(editTextUsuario.getEditText().getText().toString()
                            ,editTextSenha.getEditText().getText().toString());
                    //Inicializando progress bar
                    progressDialog = new ProgressDialog(LoginGrandson.this);
                    // Apresentando
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    fazerLogin(formLogin);

                }
            }
        }
    }

    private void fazerLogin(FormLogin formLogin){

        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<Auth> call = restService.loginCliente(formLogin);

        call.enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {

                if(response.isSuccessful()){

                    auth = response.body();
                    Log.i("Sucesso",auth.getToken());
                    SharedPreferences prefs = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = prefs.edit();
                    ed.putString("token",auth.getToken());
                    ed.putString("nome",auth.getNome());
                    ed.apply();
                    Intent intent = new Intent(LoginGrandson.this, HomeCliente.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    progressDialog.dismiss();

                }else {
                    Toast.makeText(LoginGrandson.this, "Usuário ou Senha Inválido", Toast.LENGTH_SHORT).show();
                    Log.i("Erro:  ",response.message());
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t) {
                Toast.makeText(LoginGrandson.this, "Erro", Toast.LENGTH_SHORT).show();
                Log.i("Falha:  ",t.getMessage());
                progressDialog.dismiss();

            }
        });


    }


}