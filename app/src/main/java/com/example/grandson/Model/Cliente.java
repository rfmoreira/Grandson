package com.example.grandson.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Cliente {

    @SerializedName("id")
    private int id;
    @SerializedName("nome")
    private String nome;
    @SerializedName("telefone")
    private String telefone;
    @SerializedName("cpf")
    private  String cpf;
    @SerializedName("nota")
    private String nota;
    @SerializedName("endereco")
    private String enderco;
    @SerializedName("cep")
    private int cep;
    @SerializedName("numero")
    private int numero;
    @SerializedName("complemento")
    private String complemento;
    @SerializedName("email")
    private String email;
    @SerializedName("dataInicio")
    private String dataInicio;

    public Cliente() {
    }

    public Cliente(int id, String nome,String telefone, String cpf, String nota, String enderco
            , int cep, int numero, String complemento, String email, String dataInicio) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
        this.nota = nota;
        this.enderco = enderco;
        this.cep = cep;
        this.numero = numero;
        this.complemento = complemento;
        this.email = email;
        this.dataInicio = dataInicio;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getEnderco() {
        return enderco;
    }

    public void setEnderco(String enderco) {
        this.enderco = enderco;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\n' +
                ", telefone='" + telefone + '\n' +
                ", cpf='" + cpf + '\n' +
                ", nota=" + nota +
                ", enderco='" + enderco + '\n' +
                ", cep='" + cep + '\n' +
                ", numero=" + numero +
                ", complemento='" + complemento + '\n' +
                ", email='" + email + '\n' +
                ", dataInicio='" + dataInicio + '\n' +
                '}';
    }
}
