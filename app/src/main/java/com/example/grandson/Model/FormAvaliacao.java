package com.example.grandson.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FormAvaliacao {

    @SerializedName("comentario")
    @Expose
    private String comentario;
    @SerializedName("notaParceiro")
    @Expose
    private Integer notaParceiro;

    public FormAvaliacao(String comentario, Integer notaParceiro) {
        this.comentario = comentario;
        this.notaParceiro = notaParceiro;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getNotaParceiro() {
        return notaParceiro;
    }

    public void setNotaParceiro(Integer notaParceiro) {
        this.notaParceiro = notaParceiro;
    }

}
