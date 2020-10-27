package com.example.grandson.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Endereco {

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
    @SerializedName("numero")
    @Expose
    private int numero;

    public Endereco(int cep, String cidade, String complemento, String endereco, String estado, int numero) {
        this.cep = cep;
        this.cidade = cidade;
        this.complemento = complemento;
        this.endereco = endereco;
        this.estado = estado;
        this.numero = numero;
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

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "cep=" + cep +
                ", cidade='" + cidade + '\'' +
                ", complemento='" + complemento + '\'' +
                ", endereco='" + endereco + '\'' +
                ", estado='" + estado + '\'' +
                ", numero=" + numero +
                '}';
    }
}
