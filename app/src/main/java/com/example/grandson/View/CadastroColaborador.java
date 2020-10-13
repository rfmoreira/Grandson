package com.example.grandson.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grandson.Api.RetrofitClientCEP;
import com.example.grandson.Model.Cep;
import com.example.grandson.Services.RetrofitServiceCEP;
import com.example.grandson.Utils.MetodosCadastro;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.example.grandson.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class CadastroColaborador extends AppCompatActivity {



    private EditText editTextNome,editTextMail, editTextTelefone,editTextCep,editTextEndereco,editTextSenha1,editTextSenha2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_colaborador);

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
                }else {
                    String cep = editTextCep.getText().toString();
                    cep = MetodosCadastro.unMask(cep);
                    if (MetodosCadastro.isCEP(cep)){
                    consultarCEP(cep);
                    }else {
                        editTextCep.setError("CEP Invalido");
                    }
                }
            }
        });


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
        Intent intent = new Intent(getApplicationContext(),DadosBancarioColaborador.class);
        startActivity(intent);
    };


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
                    Cep cep = response.body();
                    if (!cep.getErro()){
                    editTextEndereco.setText(cep.getLogradouro() + " " + cep.getBairro() +" "+ cep.getLocalidade());
                    Toast.makeText(getApplicationContext(), "CEP consultado com sucesso" , Toast.LENGTH_LONG).show();

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