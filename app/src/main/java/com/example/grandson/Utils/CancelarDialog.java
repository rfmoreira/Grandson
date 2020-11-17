package com.example.grandson.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.grandson.Api.RetrofitClientGrandson;
import com.example.grandson.Model.CancelarServico;
import com.example.grandson.Model.Resposta;
import com.example.grandson.R;
import com.example.grandson.Services.RetrofitServiceGrandson;
import com.example.grandson.View.DetalharServico;
import com.example.grandson.View.HomeCliente;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancelarDialog extends AppCompatDialogFragment {
    private TextInputLayout comentCancel;

    CancelarServico cancelarServico = new CancelarServico();
    private String auth;
    private int idServico;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {

        SharedPreferences pref = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        auth = pref.getString("token","");

        idServico = getActivity().getIntent().getExtras().getInt("idServico");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.cancelar_dialog, null);

        comentCancel = view.findViewById(R.id.comentCancel);

        cancelarServico.setMotivo(comentCancel.getEditText().getText().toString());

        builder.setView(view)
                .setTitle("Motivo Cancelamento")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //cancelarServico();

                    }
                });


        return builder.create();
    }

    /*private void cancelarServico(){

        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<Resposta> call = restService.cancelarServico("Bearer "+auth,idServico, cancelarServico);

        call.enqueue(new Callback<Resposta>() {
            @Override
            public void onResponse(Call<Resposta> call, Response<Resposta> response) {
                if(response.isSuccessful()){
                    Log.i("Sucesso",response.body().getMensagem());
                    Intent intent = new Intent(this, HomeCliente.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else {
                    Log.i("Erro",response.message());
                }
            }

            @Override
            public void onFailure(Call<Resposta> call, Throwable t) {
                Log.i("Falha",t.getMessage());
            }
        });
    }*/



}
