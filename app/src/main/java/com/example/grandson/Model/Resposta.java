package com.example.grandson.Model;

import com.google.gson.annotations.SerializedName;

public class Resposta {

    @SerializedName("mensagem")
    private String mensagem;
    @SerializedName("object")
    private Integer object;

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Integer getObject() {
        return object;
    }

    public void setObject(Integer object) {
        this.object = object;
    }
}
