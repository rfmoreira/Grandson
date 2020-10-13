package com.example.grandson.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.grandson.Api.RetrofitClientGrandson;
import com.example.grandson.Model.ImageInfo;
import com.example.grandson.R;
import com.example.grandson.Services.RetrofitServiceGrandson;
import com.example.grandson.Utils.FileUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroClienteFoto extends AppCompatActivity {


    private CircleImageView imgPerfil;
    private FloatingActionButton bt_up_image;
    private Button btn_salvarFoto;


    private Uri imagenUri;
    private Bitmap imgBtmp;
    private byte[] arrayBytes;
    private String imgPath;
    private File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente_foto);

        int id = getIntent().getIntExtra("idCliente",1);

        imgPerfil = (CircleImageView) findViewById(R.id.imgPerfil);
        bt_up_image = (FloatingActionButton) findViewById(R.id.bt_up_image);
        btn_salvarFoto = (Button) findViewById(R.id.btn_salvarFoto);


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

        btn_salvarFoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                salvarFoto(1);
            }
        });

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
                file = FileUtil.getFile(this,imagenUri);

                //Capturando a Imagem
                InputStream inputStream = getContentResolver().openInputStream(imagenUri);

                //Convertendo para Bitmap
                imgBtmp = BitmapFactory.decodeStream(inputStream);

                //Setando imagem
                imgPerfil.setImageBitmap(imgBtmp);

                //Convertendo Imagem para Byte
                /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imgBtmp.compress(Bitmap.CompressFormat.JPEG,80,stream);
                arrayBytes = stream.toByteArray();
                */

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

    private String getRealPathFromUri(Uri uri){
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri,projection,null,null,null);
        Cursor cursor = loader.loadInBackground();

        int colum_idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getColumnName(colum_idx);
        cursor.close();
        return result;

    }

    // Post Multipart para Salvar Imagem
    private void salvarFoto2(){
       /* file = FileUtil.getFile(this,imagenUri);
        RequestBody requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(imagenUri)),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file",file.getName(),requestBody);

        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();

        //Passando os dados para consulta
        Call<ImageInfo> call = restService.uploadImagem(body);

        Gson gson = new Gson();
        String json = gson.toJson(body);

        Log.i("Json",json);
        Log.i("GEt Name",file.getName());
        Log.i("requestBody",requestBody.toString());
        call.enqueue(new Callback<ImageInfo>() {
            @Override
            public void onResponse(Call<ImageInfo> call, Response<ImageInfo> response) {
                if(response.isSuccessful()){
                    Toast.makeText(CadastroClienteFoto.this, "Imagem Salva com Sucesso", Toast.LENGTH_SHORT).show();
                    Log.i("Sucesso",response.message());
                }else {
                    Toast.makeText(CadastroClienteFoto.this, "Erro"+response.message(), Toast.LENGTH_SHORT).show();
                    Log.i("Erro",response.message());
                }
            }

            @Override
            public void onFailure(Call<ImageInfo> call, Throwable t) {
                Toast.makeText(CadastroClienteFoto.this, "Erro"+ t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Erro 2",t.getMessage());
            }
        });*/


    }

    private void salvarFoto(int id){
        file = FileUtil.getFile(this,imagenUri);
        RequestBody requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(imagenUri)),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file",file.getName(),requestBody);

        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();

        //RequestBody bodyId = RequestBody.create(MediaType.parse("path"), String.valueOf(id));

        //Passando os dados para consulta
        Call<ImageInfo> call = restService.uploadImagem(body,id);

        Gson gson = new Gson();
        String json = gson.toJson(body);

        Log.i("Json",json);
        Log.i("GEt Name",file.getName());
        Log.i("requestBody",requestBody.toString());
        call.enqueue(new Callback<ImageInfo>() {
            @Override
            public void onResponse(Call<ImageInfo> call, Response<ImageInfo> response) {
                if(response.isSuccessful()){
                   ImageInfo imageInfo = response.body();
                    Log.i("Sucesso",imageInfo.getNome());
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
}