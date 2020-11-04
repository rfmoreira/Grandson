package com.example.grandson.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.grandson.Model.Comentario;
import com.example.grandson.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterListViewComentario extends ArrayAdapter<Comentario> {

    Context context;
    ArrayList<Comentario> listComentarios;
    //int lImagem[];

    public AdapterListViewComentario(@NonNull Context context, ArrayList<Comentario> listComentarios) {
        super(context, R.layout.row_list_view_comentarios,listComentarios);
        this.context = context;
        this.listComentarios = listComentarios;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.row_list_view_comentarios, parent, false);
        TextView comentario = view.findViewById(R.id.txtViewComentario);
        CircleImageView imgPerfComentario = view.findViewById(R.id.imgPerfComentario);
        TextView nome = view.findViewById(R.id.txtViewNomeCliente);
        Comentario c = listComentarios.get(position);

        String foto = c.getFoto().getData();
        if(foto != null){
            //Decondificando imagem recebida do JSON
            byte[]  stringDecodificada = Base64.decode(foto, Base64.DEFAULT);
            Bitmap imgbtmap = BitmapFactory.decodeByteArray(stringDecodificada, 0, stringDecodificada.length);

            imgPerfComentario.setImageBitmap(imgbtmap);
        }

        comentario.setText(c.getTexto());
        nome.setText(c.getNome());

        return view;
    }
}