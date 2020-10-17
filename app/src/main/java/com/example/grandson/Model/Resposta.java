package com.example.grandson.Model;

import com.google.gson.annotations.SerializedName;

public class Resposta {

    @SerializedName("mensagem")
    private String mensagem;

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
