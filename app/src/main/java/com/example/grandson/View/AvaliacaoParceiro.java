package com.example.grandson.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

import com.example.grandson.R;

public class AvaliacaoParceiro extends AppCompatActivity {

    private RatingBar avalicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao_parceiro);

        avalicao = (RatingBar) findViewById(R.id.ratingAvaliacao);

        avalicao.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if(v < 0.5){
                    avalicao.setRating(1);
                }
            }
        });


    }
}