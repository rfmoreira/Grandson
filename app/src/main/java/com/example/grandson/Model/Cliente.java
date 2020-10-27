package com.example.grandson.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Cliente {

    @SerializedName("cpf")
    @Expose
    private String cpf;
    @SerializedName("dataInicio")
    @Expose
    private String dataInicio;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("endereco")
    @Expose
    private Endereco endereco;
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
    @SerializedName("telefone")
    @Expose
    private String telefone;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "cpf='" + cpf + '\'' +
                ", dataInicio='" + dataInicio + '\'' +
                ", email='" + email + '\'' +
                ", endereco=" + endereco.toString() +
                ", foto=" + foto.toString() +
                ", id=" + id +
                ", nome='" + nome + '\'' +
                ", nota='" + nota + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}
