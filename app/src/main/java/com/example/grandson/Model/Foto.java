package com.example.grandson.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import com.google.gson.annotations.SerializedName;

public class Foto implements Parcelable {

    @SerializedName("nome")
    private String nome;
    @SerializedName("type")
    private String type;
    @SerializedName("data")
    private String data;

    public Foto(String nome, String type, String dataString) {
        this.nome = nome;
        this.type = type;
        this.data = dataString;
    }

    protected Foto(Parcel in) {
        nome = in.readString();
        type = in.readString();
        data = in.readString();
    }

    public static final Creator<Foto> CREATOR = new Creator<Foto>() {
        @Override
        public Foto createFromParcel(Parcel in) {
            return new Foto(in);
        }

        @Override
        public Foto[] newArray(int size) {
            return new Foto[size];
        }
    };

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataString() {
        return data;
    }

    public void setDataString(String dataString) {
        this.data = dataString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nome);
        parcel.writeString(type);
        parcel.writeString(data);
    }
}
