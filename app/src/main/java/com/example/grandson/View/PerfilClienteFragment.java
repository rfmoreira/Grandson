package com.example.grandson.View;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.grandson.Model.Comentario;
import com.example.grandson.Model.Parceiro;
import com.example.grandson.R;
import com.example.grandson.Utils.AdapterListVewCliente;
import com.example.grandson.Utils.AdapterListViewComentario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class PerfilClienteFragment extends Fragment {

    private ListView listViewComentarios;
    private ArrayList<Comentario> listaCometarios;
    private FloatingActionButton bt_edit_image;
    private Uri imagenUri;
    private CircleImageView imgPerf;


    private byte[] foto;
    private Bitmap imgbtmap;

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Associando entradas da tela
        listViewComentarios =(ListView) view.findViewById(R.id.listViewComentarios);
        bt_edit_image = (FloatingActionButton) view.findViewById(R.id.bt_edit_image);
        imgPerf = (CircleImageView) view.findViewById(R.id.imgPerfil);


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
            try {
                //Capturando a Imagem
                InputStream inputStream = getActivity().getContentResolver().openInputStream(imagenUri);
                //Convertendo para Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                //Convertendo Imagem para Byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                foto = stream.toByteArray();

                //Convertendo de Byte para Bitmap
                Bitmap compressedBitmap = BitmapFactory.decodeByteArray(foto,0,foto.length);

                //Setando imagem
                imgPerf.setImageBitmap(compressedBitmap);

                //Codificando para enviar via JSON
                String imagemCodificada = Base64.encodeToString(foto, Base64.DEFAULT);


                //Decondificando imagem recebida do JSON
                byte[]  stringDecodificada = Base64.decode(imagemCodificada, Base64.DEFAULT);
                Bitmap imagem = BitmapFactory.decodeByteArray(stringDecodificada, 0, stringDecodificada.length);

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
}