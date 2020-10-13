package com.example.grandson.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grandson.Api.RetrofitClientGrandson;
import com.example.grandson.Model.Cliente;
import com.example.grandson.Model.FormCadastroCliente;
import com.example.grandson.Model.ImageInfo;
import com.example.grandson.R;
import com.example.grandson.Services.RetrofitServiceGrandson;
import com.example.grandson.Utils.FileUtil;
import com.example.grandson.Utils.MetodosCadastro;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DadosBancarioCliente extends AppCompatActivity {

    private TextView textViewNome;
    private TextInputLayout editTextCpf, editTextNomeCartao, editTextNumCartao, editTextCodSegCartao,editTextValidade;
    private FormCadastroCliente formCadastroCliente;
    private Cliente cliente;
    private ProgressDialog progressDialog;
    private Uri imagenUri;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_bancario_cliente);

        formCadastroCliente = getIntent().getExtras().getParcelable("cliente");
        imagenUri = Uri.parse(formCadastroCliente.getUriFoto());

        editTextCpf = (TextInputLayout) findViewById(R.id.editTextCpf);
        editTextNomeCartao = (TextInputLayout) findViewById(R.id.editTextNomeCartao);
        editTextNumCartao = (TextInputLayout) findViewById(R.id.editTextNumCartao);
        editTextCodSegCartao = (TextInputLayout) findViewById(R.id.editTextCodSegCartao);
        editTextValidade = (TextInputLayout) findViewById(R.id.editTextValidade);


        // MASCARA CPF
        SimpleMaskFormatter simpleMaskCpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher maskCpf = new MaskTextWatcher(editTextCpf.getEditText(), simpleMaskCpf);
        editTextCpf.getEditText().addTextChangedListener(maskCpf);

        // MASCARA DATA VALIDADE
        SimpleMaskFormatter simpleMaskCreditCard= new SimpleMaskFormatter("NNNN NNNN NNNN NNNN");
        MaskTextWatcher maskCreditCard = new MaskTextWatcher(editTextNumCartao.getEditText(), simpleMaskCreditCard);
        editTextNumCartao.getEditText().addTextChangedListener(maskCreditCard);

        // MASCARA DATA VALIDADE
        SimpleMaskFormatter simpleMaskValidate= new SimpleMaskFormatter("NN/NNNN");
        MaskTextWatcher maskValidate = new MaskTextWatcher(editTextValidade.getEditText(), simpleMaskValidate);
        editTextValidade.getEditText().addTextChangedListener(maskValidate);


    }


    public void onClickSalvar(View v){

        //salvarFoto();
        //formCadastroCliente.setFoto(foto);

        if(MetodosCadastro.isCampoVazio(MetodosCadastro.unMask(editTextCpf.getEditText().getText().toString()))){
            editTextCpf.getEditText().setError("Campo  Vazio !");
        }else{
        if(MetodosCadastro.isCPF(MetodosCadastro.unMask(editTextCpf.getEditText().getText().toString()))){
            formCadastroCliente.setCpf(MetodosCadastro.unMask(editTextCpf.getEditText().getText().toString()));

            if(MetodosCadastro.isCampoVazio(editTextNomeCartao.getEditText().getText().toString())){
                editTextNomeCartao.getEditText().setError("Campo  Vazio !");
            }else{
                formCadastroCliente.setNomeCartao(editTextNomeCartao.getEditText().getText().toString());

                if (MetodosCadastro.isCampoVazio(MetodosCadastro.unMask(editTextNumCartao.getEditText().getText().toString()))){
                    editTextNumCartao.getEditText().setError("Campo  Vazio !");
                }else {
                    formCadastroCliente.setNumeroCartao(MetodosCadastro.unMask(editTextNumCartao.getEditText().getText().toString()));

                   if(MetodosCadastro.isCampoVazio(editTextValidade.getEditText().getText().toString())){
                        editTextValidade.getEditText().setError("Campo  Vazio !");
                   }else{
                        if(isDate(editTextValidade.getEditText().getText().toString())){
                            editTextValidade.getEditText().setError("Data Inválida");
                        }else{
                            if(editTextValidade.getEditText().getText().toString().length() < 3){
                                editTextValidade.getEditText().setError("Data Inválida");
                            }else {
                                formCadastroCliente.setDataValidade(formatDate(editTextValidade.getEditText().getText().toString()));

                                if (MetodosCadastro.isCampoVazio(editTextCodSegCartao.getEditText().getText().toString())){
                                    editTextCodSegCartao.getEditText().setError("Campo  Vazio !");
                                }else {

                                    formCadastroCliente.setCvv(Integer.parseInt(MetodosCadastro.unMask(editTextCodSegCartao.getEditText().getText().toString())));

                                    //Inicializando progress bar
                                    progressDialog = new ProgressDialog(DadosBancarioCliente.this);
                                    // Apresentando
                                    progressDialog.show();
                                    progressDialog.setContentView(R.layout.progress_dialog);
                                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    salvarCadastro(formCadastroCliente);
                                }
                            }
                        }
                    }
                }
            }
            }else {
                editTextCpf.getEditText().setError("CPF Invalido");
            }
        }
    }


    private void salvarCadastro(FormCadastroCliente formCadastroCliente){

            //Instanciando a interface
            RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
            //Passando os dados para consulta
            Call<Cliente> call = restService.cadastrarCliente(formCadastroCliente);

            call.enqueue(new Callback<Cliente>() {
                @Override
                public void onResponse(Call<Cliente> call, Response<Cliente> response) {

                    if(response.isSuccessful()){
                        cliente = response.body();
                        Log.i("Sucesso",imagenUri.toString());
                        //Toast.makeText(DadosBancarioCliente.this, "Salvo com Sucesso", Toast.LENGTH_SHORT).show();
                        if(imagenUri.toString().isEmpty()){
                            Log.i("Foto : ","Sem Foto");
                        }else {
                            salvarFoto(cliente.getId());
                        }
                        Intent intent = new Intent(DadosBancarioCliente.this,ApresentacaoGrandson.class);
                        startActivity(intent);
                        progressDialog.dismiss();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }else {
                        Toast.makeText(DadosBancarioCliente.this, "Erro Cadastro", Toast.LENGTH_SHORT).show();
                        Log.i("Erro:  ",response.message());
                        progressDialog.dismiss();
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    }
                }

                @Override
                public void onFailure(Call<Cliente> call, Throwable t) {
                    Toast.makeText(DadosBancarioCliente.this, "Login Inválido", Toast.LENGTH_SHORT).show();
                    Log.i("Falha:  ",t.getMessage());
                    progressDialog.dismiss();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            });


        }

    // Post Multipart para Salvar Imagem
    private void salvarFoto(int id){
        file = FileUtil.getFile(this,imagenUri);
        final RequestBody requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(imagenUri)),file);
        final MultipartBody.Part body = MultipartBody.Part.createFormData("file",file.getName(),requestBody);

        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();

        //RequestBody bodyId = RequestBody.create(MediaType.parse("path"), String.valueOf(id));

        //Passando os dados para consulta
        Call<ImageInfo> call = restService.uploadImagem(body,id);

        Gson gson = new Gson();
        String json = gson.toJson(body);

        /*Log.i("Json",json);
        Log.i("GEt Name",call.toString());
        Log.i("requestBody",requestBody.toString());*/
        call.enqueue(new Callback<ImageInfo>() {
            @Override
            public void onResponse(Call<ImageInfo> call, Response<ImageInfo> response) {
                if(response.isSuccessful()){
                    ImageInfo imageInfo = response.body();
                    Log.i("Sucesso",imageInfo.getData());

                }else {
                    //Toast.makeText(DadosBancarioCliente.this, "Erro"+response.message(), Toast.LENGTH_SHORT).show();
                    Log.i("Erro 1",response.message());
                }
            }

            @Override
            public void onFailure(Call<ImageInfo> call, Throwable t) {
                //Toast.makeText(DadosBancarioCliente.this, "Erro 2"+ t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Erro 2",t.getMessage());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}





