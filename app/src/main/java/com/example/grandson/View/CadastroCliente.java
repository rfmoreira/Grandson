package com.example.grandson.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grandson.Model.Cep;
import com.example.grandson.R;
import com.example.grandson.Services.APIRetrofitService;
import com.example.grandson.Utils.MetodosCadastro;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CadastroCliente extends AppCompatActivity {




    private Retrofit retrofitCEP;
    private EditText editTextNome,editTextMail, editTextTelefone,editTextCep,editTextEndereco,editTextSenha1,editTextSenha2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextMail = (EditText) findViewById(R.id.editTextMail);
        editTextTelefone = (EditText) findViewById(R.id.editTextTelefone);
        editTextCep = (EditText) findViewById(R.id.editTextCep);
        editTextEndereco = (EditText) findViewById(R.id.editTextEndereco);
        editTextSenha1 = (EditText) findViewById(R.id.editTextSenha1);
        editTextSenha2 = (EditText) findViewById(R.id.editTextSenha2);

        //Verficacao do foco do campo CEP para fazer consulta
        editTextCep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    Log.i("Foco","Focado");
                }else {
                    String cep = editTextCep
                            .getText()
                            .toString()
                            .trim()
                            .replaceAll("[.-]","");
                    Log.i("CEP",cep);
                    if (MetodosCadastro.isCEP(cep)){
                    consultarCEP(cep);
                    }else {
                        editTextCep.setError("CEP Invalido");
                    }
                }
            }
        });

        //Declaracão deo Retrofit para consultar CEP
        retrofitCEP = new Retrofit.Builder()
                .baseUrl(APIRetrofitService.BASE_URL_CEP)           //endereço do webservice Consulta CEP
                .addConverterFactory(GsonConverterFactory.create()) //conversor
                .build();

        // MASCARA TELEFONE
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(editTextTelefone, simpleMaskTelefone);
        editTextTelefone.addTextChangedListener(maskTelefone);

        //MASCARA CEP
        SimpleMaskFormatter simpleMaskCep = new SimpleMaskFormatter("NN.NNN-NNN");
        MaskTextWatcher maskCep = new MaskTextWatcher(editTextCep, simpleMaskCep);
        editTextCep.addTextChangedListener(maskCep);

    }


    public void onClickAvancar(View v){

        /*if(!MetodosCadastro.validarEmail(editTextMail.getText().toString())){
            editTextMail.setError("E-mail Invalido");
            editTextMail.requestFocus();
        }else{
            Toast.makeText(this, "E-mail Valido", Toast.LENGTH_SHORT).show();
        }*/
        Intent intent = new Intent(getApplicationContext(),DadosBancarioCliente.class);
        startActivity(intent);
    };


    private void consultarCEP(String sCep) {
        //String sCep = txtCEP.getText().toString().trim();

        //removendo o ponto e o traço do padrão CEP
        //sCep = sCep.replaceAll("[.-]+", "");

        //instanciando a interface
        APIRetrofitService restService = retrofitCEP.create(APIRetrofitService.class);

        //passando os dados para consulta
        Call<Cep> call = restService.consultarCEP(sCep);

        //exibindo a progressbar
        //progressBarCEP.setVisibility(View.VISIBLE);

        //colocando a requisição na fila para execução
        call.enqueue(new Callback<Cep>() {
            @Override
            public void onResponse(Call<Cep> call, Response<Cep> response) {
                if (response.isSuccessful()) {

                    Cep cep = response.body();
                    if (!cep.getErro()){
                    editTextEndereco.setText(cep.getLogradouro() + " " + cep.getBairro() +" "+ cep.getLocalidade());

                    Toast.makeText(getApplicationContext(), "CEP consultado com sucesso" , Toast.LENGTH_LONG).show();
                    Log.i("Resposta ", cep.toString() );
                    }else{
                        editTextCep.setError("CEP Invalido");
                        editTextCep.requestFocus();
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