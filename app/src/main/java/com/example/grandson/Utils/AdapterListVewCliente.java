package com.example.grandson.Utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.grandson.Model.Parceiro;
import com.example.grandson.R;

import java.util.ArrayList;


public class AdapterListVewCliente extends ArrayAdapter<Parceiro> {

    Context context;
    ArrayList<Parceiro> lParceiros;
    //int lImagem[];

    public AdapterListVewCliente(@NonNull Context context, ArrayList<Parceiro> lParceiros) {
        super(context,R.layout.row_list_view_cliente,lParceiros);
        this.context = context;
        this.lParceiros = lParceiros;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.row_list_view_cliente, parent, false);
        TextView nomeParceiro = view.findViewById(R.id.NomeParceiro);
        Parceiro p = lParceiros.get(position);
        //Log.i("Nome Parciero", p.getNome());

        nomeParceiro.setText(p.getNome());

        return view;
    }
}
