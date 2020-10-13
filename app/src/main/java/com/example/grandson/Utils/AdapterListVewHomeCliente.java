package com.example.grandson.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.grandson.Model.ListaParceiro;
import com.example.grandson.R;

import java.util.ArrayList;
import java.util.List;


public class AdapterListVewHomeCliente extends ArrayAdapter<ListaParceiro> {

    Context context;
    List<ListaParceiro> lListaParceiros;
    //int lImagem[];

    public AdapterListVewHomeCliente(@NonNull Context context, List<ListaParceiro> lListaParceiros) {
        super(context,R.layout.row_list_view_cliente, lListaParceiros);
        this.context = context;
        this.lListaParceiros = lListaParceiros;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.row_list_view_cliente, parent, false);
        TextView nomeParceiro = view.findViewById(R.id.NomeParceiro);
        TextView nota = view.findViewById(R.id.txtAvaliacao);
        ListaParceiro p = lListaParceiros.get(position);
        //Log.i("Nome Parciero", p.getNome());

        nomeParceiro.setText(p.getNome());
        nota.setText(p.getNota());

        return view;
    }
}
