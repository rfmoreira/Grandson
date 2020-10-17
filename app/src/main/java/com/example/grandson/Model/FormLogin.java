package com.example.grandson.Model;

import android.text.Editable;

import com.google.gson.annotations.SerializedName;

public class FormLogin {

    @SerializedName("email")
    private String email;
    @SerializedName("senha")
    private String senha;

    public FormLogin(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
