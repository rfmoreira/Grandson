package com.example.grandson.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FormCadastroCliente implements Parcelable {

    //Primeira Tela
    @SerializedName("nome")
    private String nome;
    @SerializedName("email")
    private String email;
    @SerializedName("telefone")
    private String telefone;
    @SerializedName("senha")
    private String senha;

    private String uriFoto;

    //Segunda Tela Cadastro
    @SerializedName("cep")
    private int cep;
    @SerializedName("endereco")
    private String endereco;
    @SerializedName("numero")
    private int numero;
    @SerializedName("cidade")
    private String cidade;
    @SerializedName("estado")
    private String estado;
    @SerializedName("complemento")
    private String complemento;


    //Terceira Tela cadastro
    @SerializedName("cpf")
    private  String cpf;
    @SerializedName("nomeCartao")
    private String nomeCartao;
    @SerializedName("numeroCartao")
    private String numeroCartao;
    @SerializedName("cvv")
    private int cvv;
    @SerializedName("dataValidade")
    private String dataValidade;


    public FormCadastroCliente(String nome, String email, String telefone, String senha, String uriFoto) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.uriFoto = uriFoto;
    }


    protected FormCadastroCliente(Parcel in) {
        nome = in.readString();
        email = in.readString();
        telefone = in.readString();
        senha = in.readString();
        cep = in.readInt();
        endereco = in.readString();
        numero = in.readInt();
        cidade = in.readString();
        estado = in.readString();
        complemento = in.readString();
        cpf = in.readString();
        nomeCartao = in.readString();
        numeroCartao = in.readString();
        cvv = in.readInt();
        dataValidade = in.readString();
        uriFoto = in.readString();
    }

    public static final Creator<FormCadastroCliente> CREATOR = new Creator<FormCadastroCliente>() {
        @Override
        public FormCadastroCliente createFromParcel(Parcel in) {
            return new FormCadastroCliente(in);
        }

        @Override
        public FormCadastroCliente[] newArray(int size) {
            return new FormCadastroCliente[size];
        }
    };

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public String getUriFoto() {
        return uriFoto;
    }

    public void setUriFoto(String uriFoto) {
        this.uriFoto = uriFoto;
    }

    public String toString(){

        return "Nome: "+ this.nome
                +"\n Email: "+ this.email
                +"\n Telefone: "+ this.telefone
                +"\n Senha: " +this.senha
                +"\n Cep: "+ this.cep
                +"\n Enderco: "+ this.endereco
                +"\n Numero: "+ this.numero
                +"\n complemento: "+ this.complemento
                +"\n cpf: "+ this.cpf
                +"\n nomeCartao: "+ this.nomeCartao
                +"\n numeroCartao: "+ this.numeroCartao
                +"\n cvv: "+ this.cvv
                +"\n Data: "+ this.dataValidade;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nome);
        parcel.writeString(email);
        parcel.writeString(telefone);
        parcel.writeString(senha);
        parcel.writeInt(cep);
        parcel.writeString(endereco);
        parcel.writeInt(numero);
        parcel.writeString(cidade);
        parcel.writeString(estado);
        parcel.writeString(complemento);
        parcel.writeString(cpf);
        parcel.writeString(nomeCartao);
        parcel.writeString(numeroCartao);
        parcel.writeInt(cvv);
        parcel.writeString(dataValidade);
        parcel.writeString(uriFoto);
    }
}
