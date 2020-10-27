package com.example.grandson.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grandson.Api.RetrofitClientGrandson;
import com.example.grandson.Model.ServicosAgendados;
import com.example.grandson.R;
import com.example.grandson.Services.RetrofitServiceGrandson;
import com.example.grandson.Utils.AdapterListVewHomeCliente;
import com.example.grandson.Utils.AdapterListVewServicosAgendados;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServicosAgendadosFragment extends Fragment {


    private ListView listView;
    private List<ServicosAgendados> lServicosAgendados = new ArrayList<>();
    private TextView textInfo;

    private String auth;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //lListaParceiro = preencherList();

        SharedPreferences pref = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        auth = pref.getString("token","");

        //Assosinado entradas da tela
        listView =(ListView) view.findViewById(R.id.listViewParceiro);
        textInfo = (TextView) view.findViewById(R.id.textInfo);

        listarServicosAgendados();

        // Metodo de onClick do list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(view.getContext(), "Posição: "+ lServicosAgendados.get(position).getIdServico(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(),DetalharServico.class);
                intent.putExtra("idServico", lServicosAgendados.get(position).getIdServico());
                startActivity(intent);
            }
        });
    }

        // Metodo para Preencher ListView
        /*private ArrayList<ListaParceiro> preencherList() {
            ArrayList<ListaParceiro> list = new ArrayList<ListaParceiro>();
            ListaParceiro p = new ListaParceiro();
            ListaParceiro p1 = new ListaParceiro();
            ListaParceiro p2 = new ListaParceiro();
            p.setNome("Rafael");
            list.add(p);
            p1.setNome("Luan");
            list.add(p1);
            p2.setNome("Lucas");
            list.add(p2);
            return list;

        }*/
        private void listarServicosAgendados(){

            //Instanciando a interface
            RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
            //Passando os dados para consulta
            Call<List<ServicosAgendados>> call = restService.getServicosAgendados("Bearer "+auth);

            call.enqueue(new Callback<List<ServicosAgendados>>() {
                @Override
                public void onResponse(Call<List<ServicosAgendados>> call, Response<List<ServicosAgendados>> response) {

                    Log.i("Response Code", String.valueOf(response.code()));

                    if(response.isSuccessful()){
                        lServicosAgendados.addAll(response.body());
                        //Log.i("Response", lListaParceiro.get(0).getNota());

                        // Verificando se lista esta vazia
                        if (lServicosAgendados.isEmpty()){
                            textInfo.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.INVISIBLE);
                            AdapterListVewServicosAgendados adapter = new AdapterListVewServicosAgendados(getActivity(),null);
                        }else {
                            listView.setVisibility(View.VISIBLE);
                            // Chamando Adaptador para preenchimento do list View
                            AdapterListVewServicosAgendados adapter = new AdapterListVewServicosAgendados(getContext(), lServicosAgendados);
                            // Setenado adptador no list view
                            listView.setAdapter(adapter);
                        }

                    }else {
                        Toast.makeText(getContext(), "Erro", Toast.LENGTH_SHORT).show();
                        Log.i("Erro:  ",response.message());

                    }
                }

                @Override
                public void onFailure(Call<List<ServicosAgendados>> call, Throwable t) {
                    Toast.makeText(getContext(), "Erro", Toast.LENGTH_SHORT).show();
                    Log.i("Falha:  ",t.getMessage());


                }
            });


        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_servicos_agendados, container, false);
    }
}