package com.example.grandson.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServicosAgendados {

    @SerializedName("foto")
    @Expose
    private Foto foto;
    @SerializedName("idCliente")
    @Expose
    private Integer idCliente;
    @SerializedName("idServico")
    @Expose
    private Integer idServico;
    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("nota")
    @Expose
    private String nota;

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdServico() {
        return idServico;
    }

    public void setIdServico(Integer idServico) {
        this.idServico = idServico;
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

}
