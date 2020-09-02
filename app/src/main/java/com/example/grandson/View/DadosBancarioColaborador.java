package com.example.grandson.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grandson.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class DadosBancarioColaborador extends AppCompatActivity {
    private TextView textViewNome;
    private EditText editTextCpf, editTextAgencia, editTextConta, editTextBanco;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
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

        // MASCARA CPF
        SimpleMaskFormatter simpleMaskCpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher maskCpf = new MaskTextWatcher(editTextCpf, simpleMaskCpf);
        editTextCpf.addTextChangedListener(maskCpf);


    }
    public void checkRadioButton(View v){
        radioButton =  findViewById(radioGroup.getCheckedRadioButtonId());
        radioCheck = radioButton.getText();
        Toast.makeText(this, "Radio Button: " + radioCheck, Toast.LENGTH_SHORT).show();
    }
}

