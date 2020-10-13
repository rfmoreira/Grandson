package com.example.grandson.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.grandson.Api.RetrofitClientCEP;
import com.example.grandson.Model.Cep;
import com.example.grandson.Model.FormCadastroCliente;
import com.example.grandson.Model.Foto;
import com.example.grandson.R;
import com.example.grandson.Services.RetrofitServiceCEP;
import com.example.grandson.Utils.MetodosCadastro;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroCliente2 extends AppCompatActivity {

    private TextInputLayout textInputCep,textLogradouro
            ,textInputNumero,textInputComplemento
            ,textInputBairro,textInputEstado;
    private FormCadastroCliente formCadastroCliente;
    private Cep cep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente2);

        formCadastroCliente = getIntent().getExtras().getParcelable("cliente");

        textInputCep = (TextInputLayout) findViewById(R.id.textInputCep);
        textLogradouro = (TextInputLayout) findViewById(R.id.textLogradouro);
        textInputNumero = (TextInputLayout) findViewById(R.id.textInputNumero);
        textInputComplemento = (TextInputLayout) findViewById(R.id.textInputComplemento);
        textInputBairro = (TextInputLayout) findViewById(R.id.textInputBairro);
        textInputEstado = (TextInputLayout) findViewById(R.id.textInputEstado);


        //Verficacao do foco do campo CEP para fazer consulta
        textInputCep.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){

                }else {
                    String cep = textInputCep.getEditText().getText().toString();
                    cep = MetodosCadastro.unMask(cep);
                    if (MetodosCadastro.isCEP(cep)){
                        consultarCEP(cep);
                    }else {
                        textInputCep.getEditText().setError("CEP Invalido");
                        textInputCep.requestFocus();
                    }
                }
            }
        });


        //MASCARA CEP
        SimpleMaskFormatter simpleMaskCep = new SimpleMaskFormatter("NN.NNN-NNN");
        MaskTextWatcher maskCep = new MaskTextWatcher(textInputCep.getEditText(), simpleMaskCep);
        textInputCep.getEditText().addTextChangedListener(maskCep);
    }


    public void onClickAvancar(View v){

        if(MetodosCadastro.isCampoVazio(textInputCep.getEditText().getText().toString())){
            textInputCep.getEditText().setError("Campo Vazio!");
            textInputCep.getEditText().requestFocus();
        }else{
            formCadastroCliente.setCep(Integer.parseInt(MetodosCadastro.unMask(cep.getCep())));
            if(MetodosCadastro.isCampoVazio(textLogradouro.getEditText().getText().toString())){
                textLogradouro.getEditText().setError("Campo Vazio!");
                textLogradouro.getEditText().requestFocus();
            }else {
                formCadastroCliente.setEndereco(textLogradouro.getEditText().getText().toString());
                if(MetodosCadastro.isCampoVazio(textInputBairro.getEditText().getText().toString())){
                    textInputBairro.getEditText().setError("Campo Vazio!");
                    textInputBairro.getEditText().requestFocus();
                }else {
                    if(MetodosCadastro.isCampoVazio(textInputEstado.getEditText().getText().toString())){
                        textInputEstado.getEditText().setError("Campo Vazio!");
                        textInputEstado.getEditText().requestFocus();
                    }else {
                        if(MetodosCadastro.isCampoVazio(textInputNumero.getEditText().getText().toString())){
                            textInputNumero.getEditText().setError("Campo Vazio!");
                            textInputNumero.getEditText().requestFocus();
                        }else {
                            int end = Integer.parseInt(textInputNumero.getEditText().getText().toString());
                            formCadastroCliente.setNumero(end);
                            if(MetodosCadastro.isCampoVazio(textInputComplemento.getEditText().getText().toString())){
                                textInputComplemento.getEditText().setError("Campo Vazio!");
                                textInputComplemento.getEditText().requestFocus();
                            }else {
                                formCadastroCliente.setComplemento(textInputComplemento.getEditText().getText().toString());
                                Intent intent = new Intent(getApplicationContext(),DadosBancarioCliente.class);
                                intent.putExtra("cliente",formCadastroCliente);
                                startActivity(intent);
                            }
                        }
                    }
                }
            }
        }

        //Log.i("Logradouro",);
        /*int end = Integer.parseInt(textInputNumero.getEditText().getText().toString());

        formCadastroCliente.setCep(Integer.parseInt(MetodosCadastro.unMask(cep.getCep())));
        formCadastroCliente.setNumero(end);
        formCadastroCliente.setComplemento(textInputComplemento.getEditText().getText().toString());
        formCadastroCliente.setEndereco(textLogradouro.getEditText().getText().toString());


        Log.i("Logradouro",formCadastroCliente.getEndereco());

        Intent intent = new Intent(getApplicationContext(),DadosBancarioCliente.class);
        intent.putExtra("cliente",formCadastroCliente);
        startActivity(intent);*/
    }

    private void consultarCEP(String sCep) {

        //instanciando a interface
        RetrofitServiceCEP restService = RetrofitClientCEP.getService();

        //passando os dados para consulta
        Call<Cep> call = restService.consultarCEP(sCep);

        //colocando a requisição na fila para execução
        call.enqueue(new Callback<Cep>() {
            @Override
            public void onResponse(Call<Cep> call, Response<Cep> response) {
                if (response.isSuccessful()) {
                     cep = response.body();
                    if (!cep.getErro()){

                        textLogradouro.getEditText().setText(cep.getLogradouro());
                        textInputComplemento.getEditText().setText(cep.getComplemento());
                        textInputBairro.getEditText().setText(cep.getBairro());
                        textInputEstado.getEditText().setText(cep.getLocalidade()+ " - "+cep.getUf());


                        Toast.makeText(getApplicationContext(), "CEP consultado com sucesso" , Toast.LENGTH_LONG).show();
                    }else{
                        textInputCep.getEditText().setError("CEP Invalido");
                        textInputCep.requestFocus();
                    }
                }
            }

            @Override
            public void onFailure(Call<Cep> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Ocorreu um erro ao tentar consultar o CEP. Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}