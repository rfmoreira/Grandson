package com.example.grandson.Services;

import com.example.grandson.Model.Auth;
import com.example.grandson.Model.CartaoCredito;
import com.example.grandson.Model.Cliente;
import com.example.grandson.Model.FormCadastroCliente;
import com.example.grandson.Model.Foto;
import com.example.grandson.Model.ImageInfo;
import com.example.grandson.Model.ListaParceiro;
import com.example.grandson.Model.Login;
import com.example.grandson.Model.Parceiro;
import com.example.grandson.Model.ServicosAgendados;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RetrofitServiceGrandson {

    //************** METODOS GETs *******************//


    @GET("cliente/home")
    Call<List<ListaParceiro>> listarParceiros(@Header("Authorization") String auth);

    @GET("cliente/perfil/parceiro/{id}")
    Call<Parceiro> detalharParceiro(@Path("id") int id);

    @GET("cliente/perfil/carteira")
    Call<CartaoCredito> getCarteira(@Header("Authorization") String auth);

    @GET("foto/cliente/{id}")
    Call<ImageInfo> getFoto(@Header("Authorization") String auth);

    @GET("cliente/perfil")
    Call<Cliente> getPerfilCliente(@Header("Authorization") String auth);

   @GET("cliente/servico/agendados")
   Call<List<ServicosAgendados>> getServicosAgendados(@Header("Authorization") String auth);

    //************* METODOS POSTs ******************//


    @POST("cliente/cadastrar/")
    Call<Cliente> cadastrarCliente(@Body FormCadastroCliente cliente);

    @POST("auth/cliente")
    Call<Auth> loginCliente(@Body Login login);


    @Multipart
    @POST("foto/cliente/{id}")
    Call<ImageInfo> uploadImagem(@Part MultipartBody.Part file,@Path("id") int id);

}
