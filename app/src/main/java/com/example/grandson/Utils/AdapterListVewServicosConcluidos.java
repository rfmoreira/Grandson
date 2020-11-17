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

import com.example.grandson.Model.ServicosAgendados;
import com.example.grandson.Model.ServicosConcluidos;
import com.example.grandson.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
        CircleImageView imgPerfParc = view.findViewById(R.id.imgPerfParc);
        ServicosConcluidos p = lServicosConcluidos.get(position);
        //Log.i("Nome Parciero", p.getNome());

        String foto = p.getFoto().getData();
        if(foto != null){
            //Decondificando imagem recebida do JSON
            byte[]  stringDecodificada = Base64.decode(foto, Base64.DEFAULT);
            Bitmap imgbtmap = BitmapFactory.decodeByteArray(stringDecodificada, 0, stringDecodificada.length);

            imgPerfParc.setImageBitmap(imgbtmap);
        }
        nomeParceiro.setText(p.getNome());
        String v = p.getNota();
        if (v.length() == 1){
            nota.setText(p.getNota()+",0");
        }else {
            nota.setText(p.getNota());
        }


        return view;
    }



}
