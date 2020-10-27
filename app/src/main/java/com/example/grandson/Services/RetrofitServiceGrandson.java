package com.example.grandson.Services;

import com.example.grandson.Model.ModelDetalharServico;
import com.example.grandson.Model.FormAvaliacao;
import com.example.grandson.Model.FormEditarSenha;
import com.example.grandson.Model.Auth;
import com.example.grandson.Model.FormCadastrarServico;
import com.example.grandson.Model.CartaoCredito;
import com.example.grandson.Model.Cliente;
import com.example.grandson.Model.FormCadastroCliente;
import com.example.grandson.Model.FormEditarCartao;
import com.example.grandson.Model.Foto;
import com.example.grandson.Model.ListaParceiro;
import com.example.grandson.Model.FormLogin;
import com.example.grandson.Model.Parceiro;
import com.example.grandson.Model.Resposta;
import com.example.grandson.Model.ServicosAgendados;
import com.example.grandson.Model.ServicosConcluidos;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RetrofitServiceGrandson {

    //************** METODOS GETs *******************//


    @GET("cliente/home")
    Call<List<ListaParceiro>> listarParceiros(@Header("Authorization") String auth);

    @GET("cliente/perfil/parceiro/{id}")
    Call<Parceiro> detalharParceiro(@Header("Authorization") String auth, @Path("id") int id);

    @GET("cliente/perfil/carteira")
    Call<CartaoCredito> getCarteira(@Header("Authorization") String auth);

    @GET("foto/cliente/{id}")
    Call<Foto> getFoto(@Header("Authorization") String auth);

    @GET("cliente/perfil")
    Call<Cliente> getPerfilCliente(@Header("Authorization") String auth);

   @GET("cliente/servico/agendados")
   Call<List<ServicosAgendados>> getServicosAgendados(@Header("Authorization") String auth);

    @GET("cliente/servico/concluidos")
    Call<List<ServicosConcluidos>> getServicosConcluidos(@Header("Authorization") String auth);

    @GET("cliente/servico/detalhar/{id}")
    Call<ModelDetalharServico> getServico(@Header("Authorization") String auth, @Path("id") int id);

    //************* METODOS POSTs ******************//


    @POST("cliente/cadastrar/")
    Call<Cliente> cadastrarCliente(@Body FormCadastroCliente cliente);

    @POST("auth/cliente")
    Call<Auth> loginCliente(@Body FormLogin formLogin);

    @POST("cliente/servico/cadastrar")
    Call<ServicosAgendados> cadastrarServico(@Header("Authorization") String auth, @Body FormCadastrarServico formCadastrarServico);

    @Multipart
    @POST("foto/cliente/{id}")
    Call<Foto> uploadImagem(@Part MultipartBody.Part file, @Path("id") int id);



    //************* METODOS PUTs ******************//


    @PUT("cliente/alterar/senha")
    Call<Resposta> alterarSenha(@Header("Authorization") String auth, @Body FormEditarSenha formEditarSenha);

    @PUT("cliente/carteira")
    Call<CartaoCredito> alterarCartao(@Header("Authorization") String auth, @Body FormEditarCartao formEditarCartao);

    @Multipart
    @PUT("foto/cliente/{id}")
    Call<Foto> alterarFotoCliente(@Header("Authorization") String auth, @Part MultipartBody.Part file);

    @PUT("cliente/servico/avaliar/{id}")
    Call<ResponseBody> avaliarParceiro(@Header("Authorization") String auth, @Path("id") int id ,@Body FormAvaliacao formAvaliacao);

}
