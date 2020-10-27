package com.example.grandson.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.grandson.Api.RetrofitClientGrandson;
import com.example.grandson.Model.Comentario;
import com.example.grandson.Model.ListaParceiro;
import com.example.grandson.Model.Parceiro;
import com.example.grandson.R;
import com.example.grandson.Services.RetrofitServiceGrandson;
import com.example.grandson.Utils.AdapterListViewComentario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class PerfilParceiro extends AppCompatActivity {

    private ListView listViewComentarios;
    private ArrayList<Comentario> listaCometarios;
    private Button bt_agendar;
    private int idParceiro;

    private String auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_parceiro);

        SharedPreferences pref = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        auth = pref.getString("token","");

        listViewComentarios =(ListView) findViewById(R.id.listViewComentarios);
        bt_agendar =(Button) findViewById(R.id.bt_agendar);
        idParceiro = getIntent().getExtras().getInt("idParceiro");

        //Preenchendo Lista de comentarios
        listaCometarios = preencherList();

        // Verificando se lista esta vazia
        if (listaCometarios.isEmpty()){
            AdapterListViewComentario adapter = new AdapterListViewComentario(this,null);
        }else {
            // Chamando Adaptador para preenchimento do list View
            AdapterListViewComentario adapter = new AdapterListViewComentario(this,listaCometarios);
            // Setenado adptador no list view
            listViewComentarios.setAdapter(adapter);
        }

        bt_agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),SolicitaServico.class);
                intent.putExtra("idParceiro", idParceiro);
                startActivity(intent);
            }
        });

    }

    // Metodo para Preencher ListView
    private ArrayList<Comentario> preencherList() {
        ArrayList<Comentario> list = new ArrayList<Comentario>();
        Comentario c = new Comentario(1,"Lucas Francelino","Ótima pessoa, gosteis muito da comanhia","0");

        for(int i = 0; i < 4 ;i++){
           list.add(c);
           c = new Comentario(2
                   ,"Rafael Moreira"
                   ,"Ótima pessoa, gosteis muito da comanhia"
                   ,"0");
           list.add(c);
           c = new Comentario(3
                   ,"Luan Amor"
                   ,"Ótimo profissional muito atencioso e dedicado, confiavel e tem um otimo papo pena que não é muito bom e jogos peder todos, kkkk"
                   ,"0");
           list.add(c);
           c = new Comentario(4
                   ,"Ferdinando Garcia"
                   ,"Ótima pessoa, gosteis muito da comanhia"
                   ,"0");
       }
        return list;
    }


    public void getPerfilParceiro(){

        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<Parceiro> call = restService.detalharParceiro("Bearer "+auth,idParceiro);



    }



}