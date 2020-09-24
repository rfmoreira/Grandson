package com.example.grandson.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.grandson.Utils.MetodosCadastro;
import com.example.grandson.R;
import com.example.grandson.Utils.MetodosCadastro;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.ArrayList;
import java.util.List;

public class DadosBancarioColaborador extends AppCompatActivity {
    private TextView textViewNome;
    private EditText editTextCpf, editTextAgencia, editTextConta, editTextBanco;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Spinner spinnerBancos;
    private Object radioCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_bancario_colaborador);

        editTextCpf = (EditText) findViewById(R.id.editTextCpf);
        editTextAgencia = (EditText) findViewById(R.id.editTextAgencia);
        editTextConta = (EditText) findViewById(R.id.editTextConta);
        editTextBanco = (EditText) findViewById(R.id.editTextBanco);
        radioGroup = (RadioGroup) findViewById(R.id.radioGrupTipo);
        spinnerBancos = (Spinner) findViewById(R.id.spinnerBancos);
        List<String> bancos = new ArrayList<>();
        bancos.add("Banco 1");
        bancos.add("Banco 2");
        bancos.add("Banco 3");



        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,bancos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBancos.setAdapter(adapter);

        // MASCARA CPF
        SimpleMaskFormatter simpleMaskCpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher maskCpf = new MaskTextWatcher(editTextCpf, simpleMaskCpf);
        editTextCpf.addTextChangedListener(maskCpf);



    }
    public void checkRadioButton(View v){
        radioButton =  findViewById(radioGroup.getCheckedRadioButtonId());
        radioCheck = radioButton.getText();
    }

    public void onClickSalvar(View v){

        String cpf = MetodosCadastro.unMask(editTextCpf.getText().toString());
        String agencia = MetodosCadastro.unMask(editTextAgencia.getText().toString());
        String conta = MetodosCadastro.unMask(editTextConta.getText().toString());
        String banco = MetodosCadastro.unMask(editTextBanco.getText().toString());
        if(MetodosCadastro.isCampoVazio(cpf)){
            editTextCpf.setError("Campo  Vazio !");
        }else{
        if(MetodosCadastro.isCPF(cpf)){

            if(MetodosCadastro.isCampoVazio(agencia)){
                editTextAgencia.setError("Campo  Vazio !");
            }else{
                if (MetodosCadastro.isCampoVazio(conta)){
                    editTextConta.setError("Campo  Vazio !");
                }else {
                    if (MetodosCadastro.isCampoVazio(banco)){
                        editTextBanco.setError("Campo  Vazio !");
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





