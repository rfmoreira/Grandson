package com.example.grandson.Model;

import com.google.gson.annotations.SerializedName;

public class ListaParceiro {

    @SerializedName("id")
    private int id;
    @SerializedName("nome")
    private String nome;
    @SerializedName("nota")
    private String nota;
    @SerializedName("foto")
    private Foto foto;



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }
}
