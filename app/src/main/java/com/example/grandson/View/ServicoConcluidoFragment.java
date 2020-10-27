package com.example.grandson.View;

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
import android.widget.Toast;

import com.example.grandson.Api.RetrofitClientGrandson;
import com.example.grandson.Model.ListaParceiro;
import com.example.grandson.Model.ServicosAgendados;
import com.example.grandson.Model.ServicosConcluidos;
import com.example.grandson.R;
import com.example.grandson.Services.RetrofitServiceGrandson;
import com.example.grandson.Utils.AdapterListVewHomeCliente;
import com.example.grandson.Utils.AdapterListVewServicosAgendados;
import com.example.grandson.Utils.AdapterListVewServicosConcluidos;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServicoConcluidoFragment extends Fragment {

    private ArrayList<ListaParceiro> listaParceiros;
    private ListView listServConcluido;
    private List<ServicosConcluidos> lServicosConcluidos = new ArrayList<>();

    private String auth;

    @Nullable
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        SharedPreferences pref = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        auth = pref.getString("token","");

        listServConcluido = (ListView) view.findViewById(R.id.listServConcluido);

        //listaParceiros = preencherList();
        listarServicosAgendados();

        /*listServConcluido.setVisibility(View.VISIBLE);
        // Chamando Adaptador para preenchimento do list View
        AdapterListVewHomeCliente adapter = new AdapterListVewHomeCliente(this.getContext(),listaParceiros);
        // Setenado adptador no list view
        listServConcluido.setAdapter(adapter);*/


    // Metodo de onClick do list view
        listServConcluido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Toast.makeText(view.getContext(), "Posição: "+ lServicosConcluidos.get(position).getNome(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(view.getContext(),AvaliacaoParceiro.class);
            intent.putExtra("idServico", lServicosConcluidos.get(position).getIdServico());
            startActivity(intent);
        }
    });

    }


    // Metodo para Preencher ListView
    private ArrayList<ListaParceiro> preencherList() {
        ArrayList<ServicosConcluidos> list = new ArrayList<ServicosConcluidos>();

        return null;
    }


    private void listarServicosAgendados(){

        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<List<ServicosConcluidos>> call = restService.getServicosConcluidos("Bearer "+auth);

        call.enqueue(new Callback<List<ServicosConcluidos>>() {
            @Override
            public void onResponse(Call<List<ServicosConcluidos>> call, Response<List<ServicosConcluidos>> response) {

                Log.i("Response Code", String.valueOf(response.code()));

                if(response.isSuccessful()){
                    lServicosConcluidos.addAll(response.body());
                    //Log.i("Response", lListaParceiro.get(0).getNota());

                    // Verificando se lista esta vazia
                    if (lServicosConcluidos.isEmpty()){
                        AdapterListVewServicosConcluidos adapter = new AdapterListVewServicosConcluidos(getActivity(),null);
                    }else {
                        // Chamando Adaptador para preenchimento do list View
                        AdapterListVewServicosConcluidos adapter = new AdapterListVewServicosConcluidos(getContext(), lServicosConcluidos);
                        // Setenado adptador no list view
                        listServConcluido.setAdapter(adapter);
                    }

                }else {
                    Toast.makeText(getContext(), "Erro", Toast.LENGTH_SHORT).show();
                    Log.i("Erro:  ",response.message());

                }
            }

            @Override
            public void onFailure(Call<List<ServicosConcluidos>> call, Throwable t) {
                Toast.makeText(getContext(), "Erro", Toast.LENGTH_SHORT).show();
                Log.i("Falha:  ",t.getMessage());


            }
        });


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_servico_concluido, container, false);
    }
}