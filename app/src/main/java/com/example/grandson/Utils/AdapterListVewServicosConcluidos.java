package com.example.grandson.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.grandson.Model.ServicosAgendados;
import com.example.grandson.Model.ServicosConcluidos;
import com.example.grandson.R;

import java.util.List;

public class AdapterListVewServicosConcluidos extends ArrayAdapter<ServicosConcluidos> {

    Context context;
    List<ServicosConcluidos> lServicosConcluidos;
    //int lImagem[];

    public AdapterListVewServicosConcluidos(@NonNull Context context, List<ServicosConcluidos> lServicosAgendados) {
        super(context, R.layout.row_list_view_cliente, lServicosAgendados);
        this.context = context;
        this.lServicosConcluidos = lServicosAgendados;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.row_list_view_cliente, parent, false);
        TextView nomeParceiro = view.findViewById(R.id.NomeParceiro);
        TextView nota = view.findViewById(R.id.txtAvaliacao);
        ServicosConcluidos p = lServicosConcluidos.get(position);
        //Log.i("Nome Parciero", p.getNome());

        nomeParceiro.setText(p.getNome());
        nota.setText(p.getNota());

        return view;
    }



}
