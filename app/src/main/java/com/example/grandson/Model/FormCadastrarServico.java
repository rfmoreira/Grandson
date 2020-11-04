package com.example.grandson.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FormCadastrarServico {

    @SerializedName("cep")
    @Expose
    private int cep;
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
    @SerializedName("horario")
    @Expose
    private String horario;
    @SerializedName("idParceiro")
    @Expose
    private int idParceiro;
    @SerializedName("numero")
    @Expose
    private int numero;
    @SerializedName("quantidadeDeHoras")
    @Expose
    private int quantidadeDeHoras;
    @SerializedName("valor")
    @Expose
    private double valor;
    @SerializedName("descricao")
    @Expose
    private String descricao;


    public FormCadastrarServico(int cep, String cidade, String complemento, String endereco, String estado, String horario, int idParceiro, int numero, int quantidadeDeHoras, double valor, String descricao) {
        this.cep = cep;
        this.cidade = cidade;
        this.complemento = complemento;
        this.endereco = endereco;
        this.estado = estado;
        this.horario = horario;
        this.idParceiro = idParceiro;
        this.numero = numero;
        this.quantidadeDeHoras = quantidadeDeHoras;
        this.valor = valor;
        this.descricao = descricao;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
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

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public int getIdParceiro() {
        return idParceiro;
    }

    public void setIdParceiro(int idParceiro) {
        this.idParceiro = idParceiro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getQuantidadeDeHoras() {
        return quantidadeDeHoras;
    }

    public void setQuantidadeDeHoras(int quantidadeDeHoras) {
        this.quantidadeDeHoras = quantidadeDeHoras;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
