package com.example.grandson.Services;

import com.example.grandson.Model.Cep;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface RetrofitServiceCEP {

        //consultar CEP no webservice do ViaCEP
        @GET("{cep}/json/")
        Call<Cep> consultarCEP(@Path("cep") String cep);

}

