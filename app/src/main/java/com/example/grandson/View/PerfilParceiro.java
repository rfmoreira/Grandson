package com.example.grandson.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grandson.Api.RetrofitClientGrandson;
import com.example.grandson.Model.Comentario;
import com.example.grandson.Model.ListaParceiro;
import com.example.grandson.Model.Parceiro;
import com.example.grandson.R;
import com.example.grandson.Services.RetrofitServiceGrandson;
import com.example.grandson.Utils.AdapterListViewComentario;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilParceiro extends AppCompatActivity {

    private TextView nomeCliente, txtNotaPerf,txtViewQtdCompanhia,txtViewDataInicio;
    private CircleImageView imgPerfil;
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

        nomeCliente =(TextView) findViewById(R.id.nomeCliente);
        txtNotaPerf =(TextView) findViewById(R.id.txtNotaPerf);
        txtViewQtdCompanhia =(TextView) findViewById(R.id.txtViewQtdCompanhia);
        txtViewDataInicio =(TextView) findViewById(R.id.txtViewDataInicio);
        imgPerfil =(CircleImageView) findViewById(R.id.imgPerfil);
        listViewComentarios =(ListView) findViewById(R.id.listViewComentarios);
        bt_agendar =(Button) findViewById(R.id.bt_agendar);
        idParceiro = getIntent().getExtras().getInt("idParceiro");

        getPerfilParceiro();

        //Preenchendo Lista de comentarios
       /* listaCometarios = preencherList();

        // Verificando se lista esta vazia
        if (listaCometarios.isEmpty()){
            AdapterListViewComentario adapter = new AdapterListViewComentario(this,null);
        }else {
            // Chamando Adaptador para preenchimento do list View
            AdapterListViewComentario adapter = new AdapterListViewComentario(this,listaCometarios);
            // Setenado adptador no list view
            listViewComentarios.setAdapter(adapter);
        }*/

        bt_agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),SolicitaServico.class);
                intent.putExtra("idParceiro", idParceiro);
                startActivity(intent);
                finish();
            }
        });

    }

    // Metodo para Preencher ListView
    private ArrayList<Comentario> preencherList() {
        ArrayList<Comentario> list = new ArrayList<Comentario>();
       /* Comentario c = new Comentario("","Lucas Francelino","Ótima pessoa, gosteis muito da comanhia");

        for(int i = 0; i < 4 ;i++){
           list.add(c);
           c = new Comentario(""
                   ,"Rafael Moreira"
                   ,"Ótima pessoa, gosteis muito da comanhia"
                   );
           list.add(c);
           c = new Comentario(""
                   ,"Luan Amor"
                   ,"Ótimo profissional muito atencioso e dedicado, confiavel e tem um otimo papo pena que não é muito bom e jogos peder todos, kkkk"
                   );
           list.add(c);
           c = new Comentario(""
                   ,"Ferdinando Garcia"
                   ,"Ótima pessoa, gosteis muito da comanhia"
                   );
       }*/
        return list;
    }


    public void getPerfilParceiro(){

        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<Parceiro> call = restService.detalharParceiro("Bearer "+auth,idParceiro);

        call.enqueue(new Callback<Parceiro>() {
            @Override
            public void onResponse(Call<Parceiro> call, Response<Parceiro> response) {
                if(response.isSuccessful()){
                    Parceiro parceiro = response.body();

                    nomeCliente.setText(parceiro.getNome());
                    txtNotaPerf.setText(parceiro.getNota());
                    txtViewQtdCompanhia.setText(String.valueOf(parceiro.getQuantidadeServico()));
                    String[] date1 = parceiro.getDataInicio().split("T");
                    String[] date2 = date1[0].split("-");
                    txtViewDataInicio.setText(date2[2]+"/"+date2[1]+"/"+date2[0]);
                    //Preenchendo Lista de comentarios
                    listaCometarios = new ArrayList<>(parceiro.getComentarios());
                    if(parceiro.getFoto() == null){

                    }else {
                        byte[]  stringDecodificada = Base64.decode(parceiro.getFoto().getData(), Base64.DEFAULT);
                        Bitmap imgbtmap = BitmapFactory.decodeByteArray(stringDecodificada, 0, stringDecodificada.length);
                        imgPerfil.setImageBitmap(imgbtmap);
                    }

                    // Verificando se lista esta vazia
                    if (listaCometarios.isEmpty()){
                        AdapterListViewComentario adapter = new AdapterListViewComentario(PerfilParceiro.this,null);
                    }else {
                        // Chamando Adaptador para preenchimento do list View
                        AdapterListViewComentario adapter = new AdapterListViewComentario(PerfilParceiro.this,listaCometarios);
                        // Setenado adptador no list view
                        listViewComentarios.setAdapter(adapter);
                    }

                }else {
                    Toast.makeText(PerfilParceiro.this, "Erro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Parceiro> call, Throwable t) {
                Toast.makeText(PerfilParceiro.this, "Falha", Toast.LENGTH_SHORT).show();
                Log.i("Falha", t.toString());
            }
        });

    }



}