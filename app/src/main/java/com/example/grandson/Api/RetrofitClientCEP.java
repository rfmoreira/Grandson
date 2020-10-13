package com.example.grandson.Api;

import com.example.grandson.Services.RetrofitServiceCEP;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientCEP {

    private static final String BASE_URL = "https://viacep.com.br/ws/";
    private static Retrofit retrofit;
    //private Retrofit retrofit;

    public static RetrofitServiceCEP getService(){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(RetrofitServiceCEP.class);
    }
}
