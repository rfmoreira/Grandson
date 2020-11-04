package com.example.grandson.Model;

import com.google.gson.annotations.SerializedName;

public class Comentario {

    @SerializedName("foto")
    private Foto foto;
    @SerializedName("nome")
    private String nome;
    @SerializedName("texto")
    private String texto;


    public Comentario(Foto foto, String nome, String texto) {
        this.foto = foto;
        this.nome = nome;
        this.texto = texto;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
