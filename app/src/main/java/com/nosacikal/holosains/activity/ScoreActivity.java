package com.nosacikal.holosains.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nosacikal.holosains.R;

import java.util.Objects;

public class ScoreActivity extends AppCompatActivity {

    private int correctAnswers;
    private int totalQuestions;
    private int nilai;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        TextView tv_nilai = findViewById(R.id.tv_nilai);
        TextView tv_header = findViewById(R.id.tv_header);
        TextView tv_sub = findViewById(R.id.tv_sub);

        Button btn_finish = findViewById(R.id.btn_finish);

        correctAnswers = Objects.requireNonNull(getIntent().getExtras()).getInt("jawaban");
        totalQuestions = Objects.requireNonNull(getIntent().getExtras()).getInt("soal");

        nilai = correctAnswers * 100 / totalQuestions;

        tv_nilai.setText("Nilai Kamu " + nilai);

        if (nilai < 65) {
            tv_header.setText("Jangan Menyerah");
            tv_sub.setText("Jangan cepat putus asa :)");
        }

        btn_finish.setOnClickListener(v -> {
            Intent intent = new Intent(ScoreActivity.this, EvaluasiActivity.class);
            startActivity(intent);
            finish();
        });

    }

}
