package com.example.grandson.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FormCadastrarServico {

    @SerializedName("horario")
    @Expose
    private String horario;
    @SerializedName("idCliente")
    @Expose
    private int idCliente;
    @SerializedName("idParceiro")
    @Expose
    private int idParceiro;
    @SerializedName("quantidadeDeHoras")
    @Expose
    private double quantidadeDeHoras;
    @SerializedName("valor")
    @Expose
    private double valor;


    public FormCadastrarServico(String horario, int idCliente, int idParceiro, double quantidadeDeHoras, double valor) {
        this.horario = horario;
        this.idCliente = idCliente;
        this.idParceiro = idParceiro;
        this.quantidadeDeHoras = quantidadeDeHoras;
        this.valor = valor;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdParceiro() {
        return idParceiro;
    }

    public void setIdParceiro(int idParceiro) {
        this.idParceiro = idParceiro;
    }

    public double getQuantidadeDeHoras() {
        return quantidadeDeHoras;
    }

    public void setQuantidadeDeHoras(double quantidadeDeHoras) {
        this.quantidadeDeHoras = quantidadeDeHoras;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
