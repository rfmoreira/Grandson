package com.example.grandson.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.grandson.Model.ListaParceiro;
import com.example.grandson.R;
import com.example.grandson.Utils.AdapterListVewHomeCliente;

import java.util.ArrayList;


public class ServicoConcluidoFragment extends Fragment {

    private ArrayList<ListaParceiro> listaParceiros;
    private ListView listServConcluido;

    @Nullable
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        listServConcluido = (ListView) view.findViewById(R.id.listServConcluido);

        listaParceiros = preencherList();


        listServConcluido.setVisibility(View.VISIBLE);
        // Chamando Adaptador para preenchimento do list View
        AdapterListVewHomeCliente adapter = new AdapterListVewHomeCliente(this.getContext(),listaParceiros);
        // Setenado adptador no list view
        listServConcluido.setAdapter(adapter);


    // Metodo de onClick do list view
        listServConcluido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Toast.makeText(view.getContext(), "Posição: "+ listaParceiros.get(position).getNome(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(view.getContext(),AvaliacaoParceiro.class);
            startActivity(intent);
        }
    });

    }


    // Metodo para Preencher ListView
    private ArrayList<ListaParceiro> preencherList() {
        ArrayList<ListaParceiro> list = new ArrayList<ListaParceiro>();
        ListaParceiro p = new ListaParceiro();
        ListaParceiro p1 = new ListaParceiro();
        ListaParceiro p2 = new ListaParceiro();
        ListaParceiro p3 = new ListaParceiro();
        ListaParceiro p4 = new ListaParceiro();
        p.setNome("Rafael");
        list.add(p);
        p1.setNome("Luan");
        list.add(p1);
        p2.setNome("Lucas");
        list.add(p2);
        p3.setNome("Alessandro");
        list.add(p3);
        p4.setNome("Alex");
        list.add(p4);
        return list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_servico_concluido, container, false);
    }
}