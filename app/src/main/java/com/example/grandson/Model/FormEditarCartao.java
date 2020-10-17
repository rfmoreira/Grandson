package com.example.grandson.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FormEditarCartao {

    @SerializedName("cvv")
    @Expose
    private int cvv;
    @SerializedName("dataValidade")
    @Expose
    private String dataValidade;
    @SerializedName("nomeCartao")
    @Expose
    private String nomeCartao;
    @SerializedName("numeroCartao")
    @Expose
    private String numeroCartao;

    public FormEditarCartao(int cvv, String dataValidade, String nomeCartao, String numeroCartao) {
        this.cvv = cvv;
        this.dataValidade = dataValidade;
        this.nomeCartao = nomeCartao;
        this.numeroCartao = numeroCartao;
    }

    public FormEditarCartao() {
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(String dataValidade) {
        this.dataValidade = dataValidade;
    }

    public String getNomeCartao() {
        return nomeCartao;
    }

    public void setNomeCartao(String nomeCartao) {
        this.nomeCartao = nomeCartao;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

}
