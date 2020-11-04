package com.example.grandson.View;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grandson.Api.RetrofitClientGrandson;
import com.example.grandson.Model.ListaParceiro;
import com.example.grandson.R;
import com.example.grandson.Services.RetrofitServiceGrandson;
import com.example.grandson.Utils.AdapterListVewHomeCliente;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeClienteFragment extends Fragment {

    ListView listView;
    List<ListaParceiro> lListaParceiro = new ArrayList<>();
    List<ListaParceiro> paceirosFiltrados = new ArrayList<>();
    //int imagens[] = {};

    TextView textView, nomeCabecalho;
    SearchView searchParceiro;

    private String auth;
    private String  nome;



    @Nullable
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        SharedPreferences pref = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        auth = pref.getString("token","");
        nome = pref.getString("nome","");
        //getActivity().onBackPressed();

        Log.i("Auth :", auth);
        listarParceiros();
        //Assosinado entradas da tela
        listView =(ListView) view.findViewById(R.id.listViewParceiro);
        textView = (TextView) view.findViewById(R.id.textInfo);
        searchParceiro = (SearchView) view.findViewById(R.id.searchParceiro);
        nomeCabecalho = (TextView) view.findViewById(R.id.nomeCabecalho);

        //String nome = getActivity().getIntent().getStringExtra("nome");
        nomeCabecalho.setText(nome);




        // Metodo de onClick do list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(view.getContext(), "Posição: "+ lListaParceiro.get(position).getId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(),PerfilParceiro.class);
                intent.putExtra("idParceiro",lListaParceiro.get(position).getId());
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
    private ArrayList<ListaParceiro> preencherList() {
        ArrayList<ListaParceiro> list = new ArrayList<ListaParceiro>();
       /* ListaParceiro p = new ListaParceiro();
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
        list.add(p4);*/
        return list;
    }

    public void searchContato(String name){
        paceirosFiltrados.clear();;
        for( ListaParceiro c: lListaParceiro){
            if(c.getNome().toLowerCase().contains(name.toLowerCase())){
                paceirosFiltrados.add(c);
            }
            listView.invalidateViews();
        }
    }

    private void listarParceiros(){

        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<List<ListaParceiro>> call = restService.listarParceiros("Bearer "+auth);

        call.enqueue(new Callback<List<ListaParceiro>>() {
            @Override
            public void onResponse(Call<List<ListaParceiro>> call, Response<List<ListaParceiro>> response) {

                if(response.isSuccessful()){
                    lListaParceiro.addAll(response.body());
                    //Log.i("Response", lListaParceiro.get(0).getNota());


                    // Verificando se lista esta vazia
                    if (lListaParceiro.isEmpty()){
                        Log.i("Empty", "Lista vazia");
                        textView.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.INVISIBLE);
                        AdapterListVewHomeCliente adapter = new AdapterListVewHomeCliente(getContext(),null);
                    }else {
                        paceirosFiltrados.addAll(lListaParceiro);
                        listView.setVisibility(View.VISIBLE);
                        // Chamando Adaptador para preenchimento do list View
                        AdapterListVewHomeCliente adapter = new AdapterListVewHomeCliente(getContext(),paceirosFiltrados);
                        // Setenado adptador no list view
                        listView.setAdapter(adapter);

                    }



                }else {
                    Toast.makeText(getContext(), "Usuário ou Senha Inválido", Toast.LENGTH_SHORT).show();
                    Log.i("Erro:  ",response.message());

                }
            }

            @Override
            public void onFailure(Call<List<ListaParceiro>> call, Throwable t) {
                Toast.makeText(getContext(), "Erro", Toast.LENGTH_SHORT).show();
                Log.i("Falha:  ",t.getMessage());


            }
        });

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_cliente,container,false);
        // Inflate the layout for this fragment
        return view;
    }





}