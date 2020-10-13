package com.example.grandson.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Parceiro {

    @SerializedName("id")
    private int id;
    @SerializedName("Nome")
    private String nome;
    @SerializedName("Nota")
    private double nota;
    @SerializedName("DataInicio")
    private Date dataInicio;
    @SerializedName("Comentarios")
    private List<Comentario> comentarios;


    public Parceiro() {
    }

    public Parceiro(int id, String nome, double nota, Date dataInicio, List<Comentario> comentarios) {
        this.id = id;
        this.nome = nome;
        this.nota = nota;
        this.dataInicio = dataInicio;
        this.comentarios = comentarios;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}
