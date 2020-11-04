package com.example.grandson.Model;

import com.google.gson.annotations.SerializedName;

public class Auth {

    @SerializedName("token")
    private String token;
    @SerializedName("header")
    private String header;
    @SerializedName("nome")
    private String nome;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
