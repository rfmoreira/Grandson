package com.example.grandson.Model;

import android.util.Base64;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Foto {


    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("data")
    @Expose
    private String data;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Foto{" +
                ", nome='" + nome + '\'' +
                ", type='" + type + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
