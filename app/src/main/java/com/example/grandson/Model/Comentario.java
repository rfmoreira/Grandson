package com.example.grandson.Model;

public class Comentario {

    private int id;
    private String nomePessoa;
    private String comentario;
    private int ftPerfil;

    public Comentario(int id, String nomePessoa, String comentario, int ftPerfil) {
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

    public int getFtPerfil() {
        return ftPerfil;
    }

    public void setFtPerfil(int ftPerfil) {
        this.ftPerfil = ftPerfil;
    }
}
