package com.example.grandson.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FormEditarSenha {

    @SerializedName("novaSenha")
    private String novaSenha;
    @SerializedName("senha")
    private String senha;




    public FormEditarSenha(String novaSenha, String senha) {
        this.novaSenha = novaSenha;
        this.senha = senha;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
