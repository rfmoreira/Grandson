package com.example.grandson.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CartaoCredito {

    @SerializedName("id")
    private int id;
    @SerializedName("nomeDoCartao")
    private String nomeNoCartao;
    @SerializedName("numeroDoCartao")
    private String numeroDoCartao;
    @SerializedName("codigoDeSeguranca")
    private int codigoDeSeguranca;
    @SerializedName("dataDeVencimento")
    private String dataDeVencimento;

    public CartaoCredito() {

    }

    public CartaoCredito(int id, String nomeNoCartao, String numeroDoCartao, int codigoDeSeguranca, String dataDeVencimento) {
        this.id = id;
        this.nomeNoCartao = nomeNoCartao;
        this.numeroDoCartao = numeroDoCartao;
        this.codigoDeSeguranca = codigoDeSeguranca;
        this.dataDeVencimento = dataDeVencimento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeNoCartao() {
        return nomeNoCartao;
    }

    public void setNomeNoCartao(String nomeNoCartao) {
        this.nomeNoCartao = nomeNoCartao;
    }

    public String getNumeroDoCartao() {
        return numeroDoCartao;
    }

    public void setNumeroDoCartao(String numeroDoCartao) {
        this.numeroDoCartao = numeroDoCartao;
    }

    public int getCodigoDeSeguranca() {
        return codigoDeSeguranca;
    }

    public void setCodigoDeSeguranca(int codigoDeSeguranca) {
        this.codigoDeSeguranca = codigoDeSeguranca;
    }

    public String getDataDeVencimento() {
        return dataDeVencimento;
    }

    public void setDataDeVencimento(String dataDeVencimento) {
        this.dataDeVencimento = dataDeVencimento;
    }
}
