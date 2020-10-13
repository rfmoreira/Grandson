package com.example.grandson.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grandson.Api.RetrofitClientGrandson;
import com.example.grandson.Model.Auth;
import com.example.grandson.Model.Cliente;
import com.example.grandson.Model.Comentario;
import com.example.grandson.Model.FormCadastroCliente;
import com.example.grandson.Model.Foto;
import com.example.grandson.Model.ImageInfo;
import com.example.grandson.Model.Login;
import com.example.grandson.R;
import com.example.grandson.Services.RetrofitServiceGrandson;
import com.example.grandson.Utils.AdapterListViewComentario;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class PerfilClienteFragment extends Fragment {

    private Button bt_edit_credCard,bt_edit_senha,bt_salvar_cadastro,bt_edit_cadastro,bt_sair;
    private ListView listViewComentarios;
    private ArrayList<Comentario> listaCometarios;
    private FloatingActionButton bt_edit_image;
    private Uri imagenUri;
    private CircleImageView imgPerf;
    private TextInputLayout textInputNome,textInputMail,textInputTelefone
            ,textInputCep,textLogradouro
            ,textInputNumero,textInputComplemento
            ,textInputBairro,textInputEstado,editTextCpf
            ;
    private TextView txtNotaPerf,nomeCliente;

    private byte[] arrayBytes;
    private Bitmap imgbtmap;

    private String auth;
    private Cliente cliente;

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TOKEN
        SharedPreferences pref = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
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

        //DADOS PAGAMENTO

        /*editTextCpf = (TextInputLayout) view.findViewById(R.id.editTextCpf);
        editTextNomeCartao = (TextInputLayout) view.findViewById(R.id.editTextNomeCartao);
        editTextNumCartao = (TextInputLayout) view.findViewById(R.id.editTextNumCartao);
        editTextCodSegCartao = (TextInputLayout) view.findViewById(R.id.editTextCodSegCartao);
        editTextValidade = (TextInputLayout) view.findViewById(R.id.editTextValidade);*/



        //Preenchendo Lista de comentarios
        listaCometarios = preencherList();

        // Verificando se lista esta vazia
        if (listaCometarios.isEmpty()){
            AdapterListViewComentario adapter = new AdapterListViewComentario(this.getActivity(),null);
        }else {
            // Chamando Adaptador para preenchimento do list View
            AdapterListViewComentario adapter = new AdapterListViewComentario(this.getContext(),listaCometarios);
            // Setenado adptador no list view
            listViewComentarios.setAdapter(adapter);
        }




        //Botao de editar imagem de Perfil
        bt_edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                procurarImagem();
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

    //Editar imagem de Perfil
    private void procurarImagem() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }


    // Setando imagem de perfil e Salvando
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            imagenUri = data.getData();


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



    // Metodo para Preencher ListView
    private ArrayList<Comentario> preencherList() {
        ArrayList<Comentario> list = new ArrayList<Comentario>();
        Comentario c = new Comentario(1,"Lucas Francelino","Ótima pessoa, gosteis muito da comanhia",0);
        list.add(c);
        c = new Comentario(2
                ,"Rafael Moreira"
                ,"Ótima pessoa, gosteis muito da comanhia"
                ,0);
        list.add(c);
        c = new Comentario(3
                ,"Luan Amor"
                ,"Ótimo profissional muito atencioso e dedicado, confiavel e tem um otimo papo pena que não é muito bom e jogos peder todos, kkkk"
                ,0);
        list.add(c);
        c = new Comentario(4
                ,"Ferdinando Garcia"
                ,"Ótima pessoa, gosteis muito da comanhia"
                ,0);
        return list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil_cliente, container, false);
    }

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
                   txtNotaPerf.setText(cliente.getNota()+",0");

                   textInputNome.getEditText().setText(cliente.getNome());
                   //textInputNome.getEditText().setTextColor(R.color.black);
                   textInputMail.getEditText().setText(cliente.getEmail());
                   textInputTelefone.getEditText().setText(cliente.getTelefone());
                   textInputCep.getEditText().setText(String.valueOf(cliente.getCep()));
                   textLogradouro.getEditText().setText(cliente.getEnderco());
                   textInputNumero.getEditText().setText(String.valueOf(cliente.getNumero()));
                   textInputComplemento.getEditText().setText(cliente.getComplemento());

                   editTextCpf.getEditText().setText(cliente.getCpf());

                   getFoto();
                   //editTextNomeCartao.getEditText().setText(cliente.get);

               }else {
                   Log.i("Erro 1 ",response.message());
               }
           }

           @Override
           public void onFailure(Call<Cliente> call, Throwable t) {

               Log.i("Erro 2 ",t.getMessage());

           }
       });


    }

    private void getFoto(){

        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<ImageInfo> call = restService.getFoto("Bearer "+auth);

        call.enqueue(new Callback<ImageInfo>() {
            @Override
            public void onResponse(Call<ImageInfo> call, Response<ImageInfo> response) {


                Log.i("RESPONSE", response.toString());

                if(response.isSuccessful()){

                    ImageInfo imageInfo = response.body();

                    //Decondificando imagem recebida do JSON
                    byte[]  stringDecodificada = Base64.decode(imageInfo.getData(), Base64.DEFAULT);
                    imgbtmap = BitmapFactory.decodeByteArray(stringDecodificada, 0, stringDecodificada.length);

                    imgPerf.setImageBitmap(imgbtmap);

                }else {
                    Toast.makeText(getContext(), "Usuário ou Senha Inválido", Toast.LENGTH_SHORT).show();
                    Log.i("Erro:  ",response.message());


                }
            }

            @Override
            public void onFailure(Call<ImageInfo> call, Throwable t) {
                Toast.makeText(getContext(), "Erro", Toast.LENGTH_SHORT).show();
                Log.i("Falha:  ",t.getMessage());

            }
        });


    }
}