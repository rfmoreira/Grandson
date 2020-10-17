package com.example.grandson.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.grandson.Api.RetrofitClientGrandson;
import com.example.grandson.Model.CartaoCredito;
import com.example.grandson.Model.Cliente;
import com.example.grandson.Model.FormEditarCartao;
import com.example.grandson.R;
import com.example.grandson.Services.RetrofitServiceGrandson;
import com.example.grandson.Utils.MetodosCadastro;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    private FormEditarCartao formEditarCartao;
    private ProgressDialog progressDialog;

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

        bt_salvar_cartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCampos();
            }
        });



    }

    private void validarCampos() {

        formEditarCartao = new FormEditarCartao();

        if (MetodosCadastro.isCampoVazio(editTextNomeCartao.getEditText().getText().toString())) {
            editTextNomeCartao.getEditText().setError("Campo  Vazio !");
        } else {
            formEditarCartao.setNomeCartao(editTextNomeCartao.getEditText().getText().toString());

            if (MetodosCadastro.isCampoVazio(MetodosCadastro.unMask(editTextNumCartao.getEditText().getText().toString()))) {
                editTextNumCartao.getEditText().setError("Campo  Vazio !");
            } else {
                formEditarCartao.setNumeroCartao(MetodosCadastro.unMask(editTextNumCartao.getEditText().getText().toString()));

                if (MetodosCadastro.isCampoVazio(editTextValidade.getEditText().getText().toString())) {
                    editTextValidade.getEditText().setError("Campo  Vazio !");
                } else {
                    if (isDate(editTextValidade.getEditText().getText().toString())) {
                        editTextValidade.getEditText().setError("Data Inválida");
                    } else {
                        if (editTextValidade.getEditText().getText().toString().length() < 3) {
                            editTextValidade.getEditText().setError("Data Inválida");
                        } else {
                            formEditarCartao.setDataValidade(formatDate(editTextValidade.getEditText().getText().toString()));

                            if (MetodosCadastro.isCampoVazio(editTextCodSegCartao.getEditText().getText().toString())) {
                                editTextCodSegCartao.getEditText().setError("Campo  Vazio !");
                            } else {

                                formEditarCartao.setCvv(Integer.parseInt(MetodosCadastro.unMask(editTextCodSegCartao.getEditText().getText().toString())));

                                //Inicializando progress bar
                                progressDialog = new ProgressDialog(EditarCartaoCredito.this);
                                // Apresentando
                                progressDialog.show();
                                progressDialog.setContentView(R.layout.progress_dialog);
                                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                salvarAlteracao();
                            }
                        }
                    }
                }
            }
        }
    }

    private void salvarAlteracao() {
        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<CartaoCredito> call = restService.alterarCartao("Bearer "+auth,formEditarCartao);

        call.enqueue(new Callback<CartaoCredito>() {
            @Override
            public void onResponse(Call<CartaoCredito> call, Response<CartaoCredito> response) {
                if(response.isSuccessful()){
                    cartaoCredito = response.body();

                    Toast.makeText(EditarCartaoCredito.this, "Cartão Alterado com Sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                    progressDialog.dismiss();
                }else {
                    Toast.makeText(EditarCartaoCredito.this, "Erro ao alterar Cartão", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<CartaoCredito> call, Throwable t) {
                Toast.makeText(EditarCartaoCredito.this, "Erro Servidor", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
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


    public String formatDate(String data){

        String[] dados = data.split("/");
        if(data.isEmpty()){
            return "";
        }else {

            data = dados[1]+"-"+ dados[0] +"-01";
            return  data;
        }

    }

    public boolean isDate(String data){

        if(data.length() > 3) {
            String[] dados = data.split("/");
            SimpleDateFormat formatterAno = new SimpleDateFormat("yyyy");
            SimpleDateFormat formatterMes = new SimpleDateFormat("MM");
            Date date = new Date(System.currentTimeMillis());
            int ano = Integer.parseInt(formatterAno.format(date));
            int mes = Integer.parseInt(formatterMes.format(date));

            Log.i("Data", dados[1]);

            if (Integer.parseInt(dados[0]) > 12) {
                Log.i("Data Erro", "");
                return true;
            } else if (Integer.parseInt(dados[1]) < ano) {
                Log.i("Data Erro", "");
                return true;
            } else if (Integer.parseInt(dados[1]) == ano && Integer.parseInt(dados[0]) <= mes) {
                return true;
            } else {
                return false;
            }
        }else {
            return false;
        }

    }

}