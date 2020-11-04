package com.example.grandson.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grandson.Api.RetrofitClientGrandson;
import com.example.grandson.Model.ModelDetalharServico;
import com.example.grandson.Model.Parceiro;
import com.example.grandson.Model.Resposta;
import com.example.grandson.R;
import com.example.grandson.Services.RetrofitServiceGrandson;
import com.example.grandson.Utils.MetodosCadastro;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalharServico extends AppCompatActivity {

    private CircleImageView imgPerfilParceiro;
    private TextView nomeParceiro,txtNotaParceiro,txtDataServico,txtHoraServico
            ,txtValorServico,txtQtdHorasServico,txtCepServico,txtEnderecoServico
            ,txtNumeroEnd,txtComplemento;
    private Button bt_cancelar;

    private ModelDetalharServico detalharServico;

    private String auth;
    private int idServico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhar_servico);

        SharedPreferences pref = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        auth = pref.getString("token","");

        idServico = getIntent().getExtras().getInt("idServico");

        imgPerfilParceiro = (CircleImageView) findViewById(R.id.imgPerfilParceiro);

        nomeParceiro = (TextView) findViewById(R.id.nomeParceiro);
        txtNotaParceiro = (TextView) findViewById(R.id.txtNotaParceiro);
        txtDataServico = (TextView) findViewById(R.id.txtDataServico);
        txtHoraServico = (TextView) findViewById(R.id.txtHoraServico);
        txtValorServico = (TextView) findViewById(R.id.txtValorServico);
        txtQtdHorasServico = (TextView) findViewById(R.id.txtQtdHorasServico);
        txtCepServico = (TextView) findViewById(R.id.txtCepServico);
        txtEnderecoServico = (TextView) findViewById(R.id.txtEnderecoServico);
        txtNumeroEnd = (TextView) findViewById(R.id.txtNumeroEnd);
        txtComplemento = (TextView) findViewById(R.id.txtComplemento);

        bt_cancelar = (Button) findViewById(R.id.bt_cancelar);

        getServico();

        bt_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelarServico();
            }
        });

    }



    private void getServico(){

        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<ModelDetalharServico> call = restService.getServico("Bearer "+auth,idServico);

        call.enqueue(new Callback<ModelDetalharServico>() {
            @Override
            public void onResponse(Call<ModelDetalharServico> call, Response<ModelDetalharServico> response) {
                if(response.isSuccessful()){
                    detalharServico = response.body();

                    nomeParceiro.setText(detalharServico.getNome());
                    txtNotaParceiro.setText(detalharServico.getNota());
                    String[] data = detalharServico.getDia().split("-");
                    txtDataServico.setText(data[2]+"/"+data[1]+"/"+data[0]);
                    String hora = detalharServico.getHorario();
                    int i = hora.length();
                    txtHoraServico.setText(hora.substring(0,i-3));
                    String valor = String.valueOf(detalharServico.getValor());
                    txtValorServico.setText("R$ "+valor.replace(".",",")+"0");
                    txtQtdHorasServico.setText(String.valueOf(detalharServico.getQuantidadeHoras())+":00");
                    String cep = MetodosCadastro.addMask(String.valueOf(detalharServico.getEndereco().getCep()),"##.###-###");
                    txtCepServico.setText(cep);
                    txtEnderecoServico.setText(detalharServico.getEndereco().getEndereco());
                    txtNumeroEnd.setText(String.valueOf(detalharServico.getEndereco().getNumero()));
                    txtComplemento.setText(detalharServico.getEndereco().getComplemento());

                    Toast.makeText(DetalharServico.this, "Sucesso", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DetalharServico.this, "Erro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelDetalharServico> call, Throwable t) {
                Toast.makeText(DetalharServico.this, "Falha" , Toast.LENGTH_SHORT).show();
                Log.i("Falha", t.toString());
            }
        });

    }

    private void cancelarServico(){

        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<Resposta> call = restService.cancelarServico("Bearer "+auth,idServico);

        call.enqueue(new Callback<Resposta>() {
            @Override
            public void onResponse(Call<Resposta> call, Response<Resposta> response) {
                if(response.isSuccessful()){
                    Toast.makeText(DetalharServico.this, response.body().getMensagem(), Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(DetalharServico.this, "Erro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Resposta> call, Throwable t) {
                Toast.makeText(DetalharServico.this, "Falha", Toast.LENGTH_SHORT).show();
            }
        });
    }


}