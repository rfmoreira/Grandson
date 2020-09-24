package com.example.grandson.Utils;

import android.content.Context;
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
        TextView nome = view.findViewById(R.id.txtViewNomeCliente);
        Comentario c = listComentarios.get(position);

        comentario.setText(c.getComentario());
        nome.setText(c.getNomePessoa());

        return view;
    }
}