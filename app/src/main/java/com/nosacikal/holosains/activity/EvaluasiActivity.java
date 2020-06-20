package com.nosacikal.holosains.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.nosacikal.holosains.R;

public class EvaluasiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluasi);

        Button btnMulai = findViewById(R.id.e_btn);
        btnMulai.setOnClickListener(v -> {
            Intent intent = new Intent(EvaluasiActivity.this, SoalEvaluasiActivity.class);
            startActivity(intent);
            Animatoo.animateSlideUp(this);
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(EvaluasiActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        Animatoo.animateSlideDown(this); //fire the slide left animation
    }
}
