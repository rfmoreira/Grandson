package com.example.grandson.Model;

import com.google.gson.annotations.SerializedName;

public class CancelarServico {

    @SerializedName("motivo")
    private String motivo;

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
