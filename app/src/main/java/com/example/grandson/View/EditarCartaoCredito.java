package com.example.grandson.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.example.grandson.Api.RetrofitClientGrandson;
import com.example.grandson.Model.CartaoCredito;
import com.example.grandson.Model.Cliente;
import com.example.grandson.R;
import com.example.grandson.Services.RetrofitServiceGrandson;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarCartaoCredito extends AppCompatActivity {

    private TextInputLayout editTextCpf, editTextNomeCartao
            ,editTextNumCartao, editTextCodSegCartao
            ,editTextValidade;
    private Button bt_edit_cartao,bt_salvar_cartao;
    private String auth;

    private CartaoCredito cartaoCredito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cartao_credito);

        //TOKEN
        SharedPreferences pref = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        auth = pref.getString("token","");


        //DADOS PAGAMENTO
        editTextNomeCartao = (TextInputLayout) findViewById(R.id.editTextNomeCartao);
        editTextNumCartao = (TextInputLayout) findViewById(R.id.editTextNumCartao);
        editTextCodSegCartao = (TextInputLayout) findViewById(R.id.editTextCodSegCartao);
        editTextValidade = (TextInputLayout) findViewById(R.id.editTextValidade);

        //BOTOES
        bt_edit_cartao = (Button) findViewById(R.id.bt_edit_cartao);
        bt_salvar_cartao = (Button) findViewById(R.id.bt_salvar_cartao);



        // MASCARA CARTAO CREDITO
       /* SimpleMaskFormatter simpleMaskCreditCard= new SimpleMaskFormatter("NNNN NNNN NNNN NNNN");
        MaskTextWatcher maskCreditCard = new MaskTextWatcher(editTextNumCartao.getEditText(), simpleMaskCreditCard);
        editTextNumCartao.getEditText().addTextChangedListener(maskCreditCard);*/

        // MASCARA DATA VALIDADE
        SimpleMaskFormatter simpleMaskValidate= new SimpleMaskFormatter("NN/NNNN");
        MaskTextWatcher maskValidate = new MaskTextWatcher(editTextValidade.getEditText(), simpleMaskValidate);
        editTextValidade.getEditText().addTextChangedListener(maskValidate);

        getCarteira();


        bt_edit_cartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleMaskFormatter simpleMaskCreditCard= new SimpleMaskFormatter("NNNN NNNN NNNN NNNN");
                MaskTextWatcher maskCreditCard = new MaskTextWatcher(editTextNumCartao.getEditText(), simpleMaskCreditCard);
                editTextNumCartao.getEditText().addTextChangedListener(maskCreditCard);

                editTextNomeCartao.getEditText().setFocusableInTouchMode(true);
                editTextNomeCartao.getEditText().requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editTextNomeCartao.getEditText(), InputMethodManager.SHOW_IMPLICIT);

                editTextNumCartao.getEditText().setFocusableInTouchMode(true);
                editTextNumCartao.getEditText().setText(cartaoCredito.getNumeroDoCartao());
                editTextCodSegCartao.getEditText().setFocusableInTouchMode(true);
                editTextValidade.getEditText().setFocusableInTouchMode(true);
                editTextCodSegCartao.getEditText().setText("");
                editTextNumCartao.getEditText().setText("");

                bt_edit_cartao.setEnabled(false);
                bt_salvar_cartao.setEnabled(true);
            }
        });



    }


    private void getCarteira(){

        // Instanciando cliente WS
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<CartaoCredito> call = restService.getCarteira("Bearer "+auth);

        call.enqueue(new Callback<CartaoCredito>() {
            @Override
            public void onResponse(Call<CartaoCredito> call, Response<CartaoCredito> response) {

                if(response.isSuccessful()){
                    cartaoCredito = response.body();

                    editTextNomeCartao.getEditText().setText(cartaoCredito.getNomeNoCartao());
                    String t = cartaoCredito.getNumeroDoCartao().substring
                            (cartaoCredito.getNumeroDoCartao().length() - 4);

                    editTextNumCartao.getEditText().setText("**** **** **** "+ t);
                    String[] validade = cartaoCredito.getDataDeVencimento().split("-");
                    editTextValidade.getEditText().setText(validade[1]+validade[0]);
                    editTextCodSegCartao.getEditText().setText("***");

                }else {

                }
            }
            @Override
            public void onFailure(Call<CartaoCredito> call, Throwable t) {

            }
        });

    }

}