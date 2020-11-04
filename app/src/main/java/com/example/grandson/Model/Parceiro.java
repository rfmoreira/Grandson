package com.example.grandson.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Parceiro {

    @SerializedName("comentarios")
    @Expose
    private List<Comentario> comentarios = null;
    @SerializedName("dataInicio")
    @Expose
    private String dataInicio;
    @SerializedName("foto")
    @Expose
    private Foto foto;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("nota")
    @Expose
    private String nota;
    @SerializedName("quantidadeServico")
    @Expose
    private Integer quantidadeServico;
    @SerializedName("telefone")
    @Expose
    private String telefone;

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getQuantidadeServico() {
        return quantidadeServico;
    }

    public void setQuantidadeServico(Integer quantidadeServico) {
        this.quantidadeServico = quantidadeServico;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
