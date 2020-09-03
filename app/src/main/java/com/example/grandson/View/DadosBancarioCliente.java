package com.example.grandson.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grandson.R;
import com.example.grandson.Utils.MetodosCadastro;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class DadosBancarioCliente extends AppCompatActivity {

    private TextView textViewNome;
    private EditText editTextCpf, editTextNomeCartao, editTextNumCartao, editTextCodSegCartao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_bancario_cliente);

        editTextCpf = (EditText) findViewById(R.id.editTextCpf);
        editTextNomeCartao = (EditText) findViewById(R.id.editTextNomeCartao);
        editTextNumCartao = (EditText) findViewById(R.id.editTextNumCartao);
        editTextCodSegCartao = (EditText) findViewById(R.id.editTextCodSegCartao);


        // MASCARA CPF
        SimpleMaskFormatter simpleMaskCpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher maskCpf = new MaskTextWatcher(editTextCpf, simpleMaskCpf);
        editTextCpf.addTextChangedListener(maskCpf);



    }


    public void onClickSalvar(View v){

        String cpf = MetodosCadastro.unMask(editTextCpf.getText().toString());
        String nomeCartao = MetodosCadastro.unMask(editTextNomeCartao.getText().toString());
        String numCartao = MetodosCadastro.unMask(editTextNumCartao.getText().toString());
        String codSegCartao = MetodosCadastro.unMask(editTextCodSegCartao.getText().toString());
        if(MetodosCadastro.isCampoVazio(cpf)){
            editTextCpf.setError("Campo  Vazio !");
        }else{
        if(MetodosCadastro.isCPF(cpf)){

            if(MetodosCadastro.isCampoVazio(nomeCartao)){
                editTextNomeCartao.setError("Campo  Vazio !");
            }else{
                if (MetodosCadastro.isCampoVazio(numCartao)){
                    editTextNumCartao.setError("Campo  Vazio !");
                }else {
                    if (MetodosCadastro.isCampoVazio(codSegCartao)){
                        editTextCodSegCartao.setError("Campo  Vazio !");
                    }else {
                        Intent intent = new Intent(getApplicationContext(),Grandson.class);
                        startActivity(intent);;
                    }
                }
            }
            }else {
                editTextCpf.setError("CPF Invalido");
            }
        }
        }
    }





