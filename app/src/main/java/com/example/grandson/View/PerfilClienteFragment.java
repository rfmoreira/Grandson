package com.example.grandson.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grandson.Api.RetrofitClientGrandson;
import com.example.grandson.Model.Cliente;
import com.example.grandson.Model.Comentario;
import com.example.grandson.Model.FormEditarCliente;
import com.example.grandson.Model.Foto;
import com.example.grandson.R;
import com.example.grandson.Services.RetrofitServiceGrandson;
import com.example.grandson.Utils.AdapterListViewComentario;
import com.example.grandson.Utils.FileUtil;
import com.example.grandson.Utils.MetodosCadastro;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class PerfilClienteFragment extends Fragment {

    private Button bt_edit_credCard,bt_edit_senha,bt_salvar_cadastro,bt_edit_cadastro,bt_sair,bt_salvar_foto;
    private ListView listViewComentarios;
    private ArrayList<Comentario> listaCometarios;
    private FloatingActionButton bt_edit_image;
    private Uri imagenUri;
    private CircleImageView imgPerf;
    private TextInputLayout textInputNome,textInputMail,textInputTelefone
            ,textInputCep,textLogradouro
            ,textInputNumero,textInputComplemento
            ,textInputBairro,textInputEstado,editTextCpf;
    private TextView txtNotaPerf,nomeCliente;

    private byte[] arrayBytes;
    private Bitmap imgbtmap;

    private String auth;
    private Cliente cliente;

    private File file;

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TOKEN
        final SharedPreferences pref = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        auth = pref.getString("token","");

        //Associando entradas da tela
        listViewComentarios =(ListView) view.findViewById(R.id.listViewComentarios);
        bt_edit_image = (FloatingActionButton) view.findViewById(R.id.bt_edit_image);
        imgPerf = (CircleImageView) view.findViewById(R.id.imgPerfil);
        txtNotaPerf = (TextView) view.findViewById(R.id.txtNotaPerf);
        nomeCliente = (TextView) view.findViewById(R.id.nomeCliente);


        //DADOS PESSOAIS
        textInputNome = (TextInputLayout) view.findViewById(R.id.textInputNome);
        textInputMail = (TextInputLayout) view.findViewById(R.id.textInputMail);
        textInputTelefone = (TextInputLayout) view.findViewById(R.id.textInputTelefone);
        editTextCpf = (TextInputLayout) view.findViewById(R.id.editTextCpf);

        //ENDEREÇO
        textInputCep = (TextInputLayout) view.findViewById(R.id.textInputCep);
        textLogradouro = (TextInputLayout) view.findViewById(R.id.textLogradouro);
        textInputNumero = (TextInputLayout) view.findViewById(R.id.textInputNumero);
        textInputComplemento = (TextInputLayout) view.findViewById(R.id.textInputComplemento);
        textInputBairro = (TextInputLayout) view.findViewById(R.id.textInputBairro);
        textInputEstado = (TextInputLayout) view.findViewById(R.id.textInputEstado);

        //BOTÕES
        bt_edit_credCard = (Button) view.findViewById(R.id.bt_edit_credCard);
        bt_edit_senha = (Button) view.findViewById(R.id.bt_edit_senha);
        bt_salvar_cadastro = (Button) view.findViewById(R.id.bt_salvar_cadastro);
        bt_edit_cadastro = (Button) view.findViewById(R.id.bt_edit_cadastro);
        bt_sair = (Button) view.findViewById(R.id.bt_sair);
        bt_salvar_foto = (Button) view.findViewById(R.id.bt_salvar_foto);

        //DADOS PAGAMENTO
        /*editTextCpf = (TextInputLayout) view.findViewById(R.id.editTextCpf);
        editTextNomeCartao = (TextInputLayout) view.findViewById(R.id.editTextNomeCartao);
        editTextNumCartao = (TextInputLayout) view.findViewById(R.id.editTextNumCartao);
        editTextCodSegCartao = (TextInputLayout) view.findViewById(R.id.editTextCodSegCartao);
        editTextValidade = (TextInputLayout) view.findViewById(R.id.editTextValidade);*/

        //Preenchendo Lista de comentarios
        /*listaCometarios = preencherList();

        // Verificando se lista esta vazia
        if (listaCometarios.isEmpty()){
            AdapterListViewComentario adapter = new AdapterListViewComentario(this.getActivity(),null);
        }else {
            // Chamando Adaptador para preenchimento do list View
            AdapterListViewComentario adapter = new AdapterListViewComentario(this.getContext(),listaCometarios);
            // Setenado adptador no list view
            listViewComentarios.setAdapter(adapter);
        }*/

        //Botao de editar imagem de Perfil
        bt_edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                    String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permission,1001);
                }else{
                    procurarImagem();
                }

            }
        });


        //Botao de editar cartao de credito
        bt_edit_credCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),EditarCartaoCredito.class);
                startActivity(intent);
            }
        });

        // Botao Sair
        bt_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),LoginGrandson.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                pref.edit().clear().commit();

            }
        });

        //Botao Editar Senha
        bt_edit_senha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),EditarSenha.class);
                startActivity(intent);
            }
        });

        bt_edit_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habilitarCampos();
            }
        });

        bt_salvar_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desabilitarCampos();
            }
        });

        bt_salvar_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarFoto();
            }
        });


        // MASCARA TELEFONE
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(textInputTelefone.getEditText(), simpleMaskTelefone);
        textInputTelefone.getEditText().addTextChangedListener(maskTelefone);

        // MASCARA CPF
        SimpleMaskFormatter simpleMaskCpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher maskCpf = new MaskTextWatcher(editTextCpf.getEditText(), simpleMaskCpf);
        editTextCpf.getEditText().addTextChangedListener(maskCpf);

        //MASCARA CEP
        SimpleMaskFormatter simpleMaskCep = new SimpleMaskFormatter("NN.NNN-NNN");
        MaskTextWatcher maskCep = new MaskTextWatcher(textInputCep.getEditText(), simpleMaskCep);
        textInputCep.getEditText().addTextChangedListener(maskCep);

        getPerfil();

    }

    //Metodo para edicao de cadastro
    private void habilitarCampos() {
        //DADOS PESSOAIS
        textInputNome.getEditText().setFocusableInTouchMode(true);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(textInputNome.getEditText(), InputMethodManager.SHOW_IMPLICIT);
        editTextCpf.setEnabled(false);
        textInputMail.setEnabled(false);
        textInputTelefone.getEditText().setFocusableInTouchMode(true);

        //ENDEREÇO
        textInputCep.getEditText().setFocusableInTouchMode(true);
        textLogradouro.getEditText().setFocusableInTouchMode(true);
        textInputNumero.getEditText().setFocusableInTouchMode(true);
        textInputComplemento.getEditText().setFocusableInTouchMode(true);
        textInputBairro.getEditText().setFocusableInTouchMode(true);
        textInputEstado.getEditText().setFocusableInTouchMode(true);

        bt_edit_cadastro.setEnabled(false);
        bt_salvar_cadastro.setEnabled(true);

    }

    private void desabilitarCampos(){

        String nome = textInputNome.getEditText().getText().toString();
        String telefone = MetodosCadastro.unMask(textInputTelefone.getEditText().getText().toString());
        int cep = Integer.parseInt(MetodosCadastro.unMask(textInputCep.getEditText().getText().toString()));
        String logradouro = textLogradouro.getEditText().getText().toString();
        int numero = Integer.parseInt(textInputNumero.getEditText().getText().toString());
        String complemento = textInputComplemento.getEditText().getText().toString();
        String bairro = textInputBairro.getEditText().getText().toString();
        String estado = textInputEstado.getEditText().getText().toString();

        FormEditarCliente formEditarCliente = new FormEditarCliente(cep,bairro,complemento,logradouro,estado,nome,numero,telefone);

        salavarCadastro(formEditarCliente);
        //DADOS PESSOAIS
        textInputNome.getEditText().setFocusableInTouchMode(false);
        editTextCpf.setEnabled(false);
        textInputMail.setEnabled(false);
        textInputTelefone.getEditText().setFocusableInTouchMode(false);

        //ENDEREÇO
        textInputCep.getEditText().setFocusableInTouchMode(false);
        textLogradouro.getEditText().setFocusableInTouchMode(false);
        textInputNumero.getEditText().setFocusableInTouchMode(false);
        textInputComplemento.getEditText().setFocusableInTouchMode(false);
        textInputBairro.getEditText().setFocusableInTouchMode(false);
        textInputEstado.getEditText().setFocusableInTouchMode(false);

        bt_edit_cadastro.setEnabled(true);
        bt_salvar_cadastro.setEnabled(false);
    }

    //Editar imagem de Perfil
    private void procurarImagem() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"Selecione Imagem"), 1);

    }


    // Setando imagem de Perfil
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            imagenUri = data.getData();

            bt_salvar_foto.setVisibility(View.VISIBLE);
            bt_salvar_foto.setEnabled(true);

            //File file = data.get
            try {
                //Capturando a Imagem
                InputStream inputStream = getActivity().getContentResolver().openInputStream(imagenUri);
                //Convertendo para Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                //Convertendo Imagem para Byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                arrayBytes = stream.toByteArray();

                //Convertendo de Byte para Bitmap
                imgbtmap = BitmapFactory.decodeByteArray(arrayBytes,0,arrayBytes.length);

                //Setando imagem
                imgPerf.setImageBitmap(imgbtmap);

                //Codificando para enviar via JSON
                String imagemCodificada = Base64.encodeToString(arrayBytes, Base64.DEFAULT);

                //Decondificando imagem recebida do JSON
                byte[]  stringDecodificada = Base64.decode(imagemCodificada, Base64.DEFAULT);
                imgbtmap = BitmapFactory.decodeByteArray(stringDecodificada, 0, stringDecodificada.length);

                //Log.i("Imagem ", imagemCodificada);
                //imgPerf.setImageBitmap(bitmap);
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void salvarFoto(){
        file = FileUtil.getFile(getContext(),imagenUri);
        long size = file.length();

        if(size <= 2097152){
            final RequestBody requestBody = RequestBody.create(MediaType.parse(getContext().getContentResolver().getType(imagenUri)),file);
            final MultipartBody.Part body = MultipartBody.Part.createFormData("foto",file.getName(),requestBody);

            //Instanciando a interface
            RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();

            //RequestBody bodyId = RequestBody.create(MediaType.parse("path"), String.valueOf(id));
            //Passando os dados para consulta
            Call<Foto> call = restService.alterarFotoCliente("Bearer "+ auth,body);

            //Gson gson = new Gson();
            //String json = gson.toJson(body);

            //Log.i("Json",json);
            //Log.i("GEt Name",file.getName());
            //Log.i("type",getContext().getContentResolver().getType(imagenUri));

            call.enqueue(new Callback<Foto>() {
                @Override
                public void onResponse(Call<Foto> call, Response<Foto> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getContext(), "Foto Alterada com sucesso", Toast.LENGTH_SHORT).show();
                        bt_salvar_foto.setVisibility(View.INVISIBLE);
                        bt_salvar_foto.setEnabled(false);
                    }else {
                        Toast.makeText(getContext(), "Erro" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Foto> call, Throwable t) {
                    Toast.makeText(getContext(), "Erro Interno", Toast.LENGTH_SHORT).show();
                    Log.i("Falha",t.toString());
                }
            });
        }else {
            Toast.makeText(getContext(), "Imagem Muito Grande !", Toast.LENGTH_SHORT).show();
        }
    }

    // Metodo para Preencher ListView
    private ArrayList<Comentario> preencherList() {
        ArrayList<Comentario> list = new ArrayList<Comentario>();
        return list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil_cliente, container, false);
    }

    // Metodo para preenchimento dos dados de perfil
    private void getPerfil(){
        Log.i("Auth ",auth);
        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<Cliente> call = restService.getPerfilCliente("Bearer "+auth);
       call.enqueue(new Callback<Cliente>() {
           @SuppressLint("ResourceAsColor")
           @Override
           public void onResponse(Call<Cliente> call, Response<Cliente> response) {

               if(response.isSuccessful()){
                   cliente = response.body();
                   String[] nome = cliente.getNome().split(" ");
                   nomeCliente.setText(nome[0]);

                   String v = cliente.getNota();
                   if (v.length() == 1){
                       txtNotaPerf.setText(cliente.getNota()+",0");
                   }else {
                       txtNotaPerf.setText(cliente.getNota());
                   }

                   textInputNome.getEditText().setText(cliente.getNome());
                   //textInputNome.getEditText().setTextColor(R.color.black);
                   textInputMail.getEditText().setText(cliente.getEmail());
                   textInputTelefone.getEditText().setText(cliente.getTelefone());
                   textInputCep.getEditText().setText(String.valueOf(cliente.getEndereco().getCep()));
                   textLogradouro.getEditText().setText(cliente.getEndereco().getEndereco());
                   textInputBairro.getEditText().setText(cliente.getEndereco().getCidade());
                   textInputEstado.getEditText().setText(cliente.getEndereco().getEstado());
                   textInputNumero.getEditText().setText(String.valueOf(cliente.getEndereco().getNumero()));
                   textInputComplemento.getEditText().setText(cliente.getEndereco().getComplemento());

                   editTextCpf.getEditText().setText(cliente.getCpf());

                   //Preenchendo Lista de comentarios
                   listaCometarios = new ArrayList<>(cliente.getComentarios());

                   // Verificando se lista esta vazia
                   if (listaCometarios.isEmpty()){
                       //  AdapterListViewComentario adapter = new AdapterListViewComentario(this.getActivity(),null);
                   }else {
                       // Chamando Adaptador para preenchimento do list View
                       AdapterListViewComentario adapter = new AdapterListViewComentario(getContext(),listaCometarios);
                       // Setenado adptador no list view
                       listViewComentarios.setAdapter(adapter);
                       View current = getActivity().getCurrentFocus();
                       if (current != null) current.clearFocus();
                   }


                   getFoto();
                   //editTextNomeCartao.getEditText().setText(cliente.get);

               }else {
                   Log.i("Erro Requisição",response.message());
               }
           }

           @Override
           public void onFailure(Call<Cliente> call, Throwable t) {

               Log.i("Erro Servidor",t.getMessage());

           }
       });


    }

    // Buscar Foto de Perfil
    private void getFoto(){

        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<Foto> call = restService.getFoto("Bearer "+auth);

        call.enqueue(new Callback<Foto>() {
            @Override
            public void onResponse(Call<Foto> call, Response<Foto> response) {

                if(response.isSuccessful()){

                    Foto foto = response.body();
                    if(foto.getData() != null){
                        //Decondificando imagem recebida do JSON
                        byte[]  stringDecodificada = Base64.decode(foto.getData(), Base64.DEFAULT);
                        imgbtmap = BitmapFactory.decodeByteArray(stringDecodificada, 0, stringDecodificada.length);

                        imgPerf.setImageBitmap(imgbtmap);
                    }

                }else {
                    Toast.makeText(getContext(), "Foto não cadastrada", Toast.LENGTH_SHORT).show();
                    Log.i("Erro:  ",response.message());
                }
            }

            @Override
            public void onFailure(Call<Foto> call, Throwable t) {
                Toast.makeText(getContext(), "Erro", Toast.LENGTH_SHORT).show();
                Log.i("Falha:  ",t.getMessage());
            }
        });


    }

    private void salavarCadastro(FormEditarCliente formEditarCliente){
        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<Cliente> call = restService.alterarCliente("Bearer "+auth, formEditarCliente);

        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "Alterado com Sucesso", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Erro ao alterar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Toast.makeText(getContext(), "Falha no servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}