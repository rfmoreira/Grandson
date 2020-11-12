package com.example.grandson.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FormEditarCliente {

    @SerializedName("cep")
    @Expose
    private Integer cep;
    @SerializedName("cidade")
    @Expose
    private String cidade;
    @SerializedName("complemento")
    @Expose
    private String complemento;
    @SerializedName("endereco")
    @Expose
    private String endereco;
    @SerializedName("estado")
    @Expose
    private String estado;
    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("numero")
    @Expose
    private Integer numero;
    @SerializedName("telefone")
    @Expose
    private String telefone;

    public FormEditarCliente(Integer cep, String cidade, String complemento, String endereco, String estado, String nome, Integer numero, String telefone) {
        this.cep = cep;
        this.cidade = cidade;
        this.complemento = complemento;
        this.endereco = endereco;
        this.estado = estado;
        this.nome = nome;
        this.numero = numero;
        this.telefone = telefone;
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
