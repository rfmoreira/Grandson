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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grandson.Model.Parceiro;
import com.example.grandson.R;
import com.example.grandson.Utils.AdapterListVewCliente;

import java.util.ArrayList;

public class HomeClienteFragment extends Fragment {

    ListView listView;
    ArrayList<Parceiro> lParceiro;
    ArrayList<Parceiro> paceirosFiltrados = new ArrayList<>();
    //int imagens[] = {};
    TextView textView;
    SearchView searchParceiro;



    @Nullable
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lParceiro = preencherList();
        paceirosFiltrados.addAll(lParceiro);

        //Assosinado entradas da tela
        listView =(ListView) view.findViewById(R.id.listViewParceiro);
        textView = (TextView) view.findViewById(R.id.textInfo);
        searchParceiro = (SearchView) view.findViewById(R.id.searchParceiro);


        // Verificando se lista esta vazia
        if (lParceiro.isEmpty()){
            textView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
            AdapterListVewCliente adapter = new AdapterListVewCliente(this.getActivity(),null);
        }else {
            listView.setVisibility(View.VISIBLE);
            // Chamando Adaptador para preenchimento do list View
            AdapterListVewCliente adapter = new AdapterListVewCliente(this.getContext(),paceirosFiltrados);
            // Setenado adptador no list view
            listView.setAdapter(adapter);

        }

        // Metodo de onClick do list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(view.getContext(), "Posição: "+ lParceiro.get(position).getNome(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(),SolicitaServico.class);
                startActivity(intent);
            }
        });

        //Search View
        searchParceiro.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchContato(newText);
                return false;
            }
        });

    }

    // Metodo para Preencher ListView
    private ArrayList<Parceiro> preencherList() {
        ArrayList<Parceiro> list = new ArrayList<Parceiro>();
        Parceiro p = new Parceiro();
        Parceiro p1 = new Parceiro();
        Parceiro p2 = new Parceiro();
        Parceiro p3 = new Parceiro();
        Parceiro p4 = new Parceiro();
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

    public void searchContato(String name){
        paceirosFiltrados.clear();;
        for( Parceiro c: lParceiro){
            if(c.getNome().toLowerCase().contains(name.toLowerCase())){
                paceirosFiltrados.add(c);
            }
            listView.invalidateViews();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_cliente,container,false);
        // Inflate the layout for this fragment
        return view;
    }
}