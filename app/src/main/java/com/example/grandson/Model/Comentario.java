package com.example.grandson.Model;

import com.google.gson.annotations.SerializedName;

public class Comentario {

    @SerializedName("id")
    private int id;
    @SerializedName("nomePessoa")
    private String nomePessoa;
    @SerializedName("comentario")
    private String comentario;
    @SerializedName("foto")
    private String ftPerfil;

    public Comentario(int id, String nomePessoa, String comentario, String ftPerfil) {
        this.id = id;
        this.nomePessoa = nomePessoa;
        this.comentario = comentario;
        this.ftPerfil = ftPerfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFtPerfil() {
        return ftPerfil;
    }

    public void setFtPerfil(String ftPerfil) {
        this.ftPerfil = ftPerfil;
    }
}
