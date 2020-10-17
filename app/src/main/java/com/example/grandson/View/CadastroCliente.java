package com.example.grandson.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grandson.Model.FormCadastroCliente;
import com.example.grandson.R;
import com.example.grandson.Utils.MetodosCadastro;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;


public class CadastroCliente extends AppCompatActivity {


    private TextInputLayout textInputNome,textInputMail,textInputTelefone,textInputSenha1,textInputSenha2;
    private FloatingActionButton bt_up_image;
    private FormCadastroCliente formCadastroClientem;
    private CircleImageView imgPerfil;

    private Uri imagenUri;
    private Bitmap imgBtmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        textInputNome = (TextInputLayout) findViewById(R.id.textInputNome);
        textInputMail = (TextInputLayout) findViewById(R.id.textInputMail);
        textInputTelefone = (TextInputLayout) findViewById(R.id.textInputTelefone);
        textInputSenha1 = (TextInputLayout) findViewById(R.id.textInputSenha1);
        textInputSenha2 = (TextInputLayout) findViewById(R.id.textInputSenha2);
        bt_up_image = (FloatingActionButton) findViewById(R.id.bt_up_image);
        imgPerfil = (CircleImageView) findViewById(R.id.imgPerfil);


        // MASCARA TELEFONE
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(textInputTelefone.getEditText(), simpleMaskTelefone);
        textInputTelefone.getEditText().addTextChangedListener(maskTelefone);

        bt_up_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                    String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permission,1001);
                }else{
                    procurarImagem();
                }
            }
        });
    }


    public void onClickAvancar(View v){

        String nome;
        String email;
        String telefone;
        String senha;
        String uriFoto="";
        if(imagenUri != null){
            Log.i("URI","Entrou");
            uriFoto = imagenUri.toString();
        }

        if(MetodosCadastro.isCampoVazio(textInputNome.getEditText().getText().toString())){
            textInputNome.getEditText().setError("Campo Vazio!");
            textInputNome.getEditText().requestFocus();
        }else{
            nome = textInputNome.getEditText().getText().toString();
            if(MetodosCadastro.isCampoVazio(textInputMail.getEditText().getText().toString())){
                textInputMail.getEditText().setError("Campo Vazio!");
                textInputMail.getEditText().requestFocus();
            }else {
                if(!MetodosCadastro.isEmail(textInputMail.getEditText().getText().toString())){
                    textInputMail.getEditText().setError("Email Inválido!");
                    textInputMail.getEditText().requestFocus();
                }else{
                    email = textInputMail.getEditText().getText().toString();
                    if(MetodosCadastro.isCampoVazio(textInputTelefone.getEditText().getText().toString())){
                        textInputTelefone.getEditText().setError("Campo Vazio!");
                        textInputTelefone.getEditText().requestFocus();
                    }else {
                        telefone = MetodosCadastro.unMask(textInputTelefone.getEditText().getText().toString());
                        if(MetodosCadastro.isCampoVazio(textInputSenha1.getEditText().getText().toString())){
                            textInputSenha1.setError("Campo Vazio!");
                            textInputSenha1.getEditText().requestFocus();
                        }else {
                            if(MetodosCadastro.isCampoVazio(textInputSenha2.getEditText().getText().toString())){
                                textInputSenha2.setError("Campo Vazio!");
                                textInputSenha2.getEditText().requestFocus();
                            }else {
                                if(!isPasswordsEquals()){
                                    textInputSenha1.setError("Senhas não coinciden");
                                    textInputSenha1.getEditText().requestFocus();
                                }else {
                                    senha = textInputSenha1.getEditText().getText().toString();
                                    formCadastroClientem = new FormCadastroCliente(nome,email,telefone,senha,uriFoto);
                                    Intent intent = new Intent(getApplicationContext(),CadastroCliente2.class);
                                    intent.putExtra("cliente", formCadastroClientem);
                                    startActivity(intent);
                                }
                            }
                        }
                    }
                }
            }
        }

        /*formCadastroClientem = new FormCadastroCliente(nome,email,telefone,senha,uriFoto);
        Intent intent = new Intent(getApplicationContext(),CadastroCliente2.class);
        intent.putExtra("cliente", formCadastroClientem);
        startActivity(intent);*/
    }

    //Acessar Galeria
    private void procurarImagem() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"Selecione Imagem"), 1);
    }

    // Setando imagem de perfil e criando arquivo File
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            imagenUri = data.getData();
            try {
                //Capturando a Imagem
                InputStream inputStream = getContentResolver().openInputStream(imagenUri);
                //Convertendo para Bitmap
                imgBtmp = BitmapFactory.decodeStream(inputStream);
                //Setando imagem
                imgPerfil.setImageBitmap(imgBtmp);

                //Convertendo Imagem para Byte
                /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imgBtmp.compress(Bitmap.CompressFormat.JPEG,80,stream);
                arrayBytes = stream.toByteArray();*/
                //Convertendo de Byte para Bitmap
                //imgBtmp = BitmapFactory.decodeByteArray(arrayBytes,0,arrayBytes.length);
                //Codificando para enviar via JSON
                //String imagemCodificada = Base64.encodeToString(arrayBytes, Base64.DEFAULT);
                //Decondificando imagem recebida do JSON
                //byte[]  stringDecodificada = Base64.decode(imagemCodificada, Base64.DEFAULT);
                //imgBtmp = BitmapFactory.decodeByteArray(stringDecodificada, 0, stringDecodificada.length);
                //imgPerfil.setImageBitmap(imgBtmp);

            }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public boolean isPasswordsEquals(){
        String senha1 = textInputSenha1.getEditText().getText().toString();
        String senha2 = textInputSenha2.getEditText().getText().toString();

        if(senha1.equals(senha2)){
            return true;
        }else {
            return false;
        }

    }


}