package com.example.grandson.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServicosConcluidos {

    @SerializedName("idServico")
    @Expose
    private Integer idServico;
    @SerializedName("idParceiro")
    @Expose
    private Integer idParceiro;
    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("nota")
    @Expose
    private String nota;
    @SerializedName("foto")
    @Expose
    private Foto foto;

    public Integer getIdServico() {
        return idServico;
    }

    public void setIdServico(Integer idServico) {
        this.idServico = idServico;
    }

    public Integer getIdParceiro() {
        return idParceiro;
    }

    public void setIdParceiro(Integer idParceiro) {
        this.idParceiro = idParceiro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
