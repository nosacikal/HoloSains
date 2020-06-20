package com.nosacikal.holosains.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.nosacikal.holosains.R;
import com.nosacikal.holosains.api.Api;
import com.nosacikal.holosains.api.RetrofitClient;
import com.nosacikal.holosains.models.Evaluasi;
import com.nosacikal.holosains.models.EvaluasiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SoalEvaluasiActivity extends AppCompatActivity implements View.OnClickListener {

    private int mCurrentPosition = 1;
    private int mSelectedOptionPosition = 0;
    private int mCorrectAnswer = 0;
    private List<Evaluasi> evaluasiList;

    private TextView option_one, option_two, option_three, option_four, se_progress, se_pertanyaan;
    private ProgressBar progressBar;
    private ImageView iv_image;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soal_evaluasi);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        option_one = findViewById(R.id.se_option_one);
        option_two = findViewById(R.id.se_option_two);
        option_three = findViewById(R.id.se_option_three);
        option_four = findViewById(R.id.se_option_four);
        se_progress = findViewById(R.id.se_progress);
        progressBar = findViewById(R.id.progressBar);
        se_pertanyaan = findViewById(R.id.se_pertanyaan);
        iv_image = findViewById(R.id.iv_image);
        button = findViewById(R.id.se_submit);

        setQuestion();

        option_one.setOnClickListener(this);
        option_two.setOnClickListener(this);
        option_three.setOnClickListener(this);
        option_four.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    private void setQuestion() {

        Call<EvaluasiResponse> call = RetrofitClient.getInstance().getApi().getAllEvaluasi();

        call.enqueue(new Callback<EvaluasiResponse>() {
            @Override
            public void onResponse(Call<EvaluasiResponse> call, Response<EvaluasiResponse> response) {


                if (response.isSuccessful()) {
                    evaluasiList = Objects.requireNonNull(response.body()).getEvaluasi();

                    defaultOptionView();

                    if (evaluasiList != null) {

                        if (mCurrentPosition == evaluasiList.size()) {
                            button.setText("Selesai");
                        } else {
                            button.setText("Jawab");
                        }

                        progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#01BCF1")));
                        progressBar.setMax(evaluasiList.size());
                        progressBar.setProgress(mCurrentPosition);
                        se_progress.setText(mCurrentPosition + " / " + evaluasiList.size());


                        se_pertanyaan.setText(evaluasiList.get(mCurrentPosition - 1).getPertanyaan());
                        option_one.setText(evaluasiList.get(mCurrentPosition - 1).getPilihan_A());
                        option_two.setText(evaluasiList.get(mCurrentPosition - 1).getPilihan_B());
                        option_three.setText(evaluasiList.get(mCurrentPosition - 1).getPilihan_C());
                        option_four.setText(evaluasiList.get(mCurrentPosition - 1).getPilihan_D());

                        String gambar = evaluasiList.get(mCurrentPosition - 1).getGambar();
                        if (gambar != null) {
                            Glide.with(getApplicationContext()).load(Api.IMAGE_SOAL_URL + gambar).into(iv_image);
                            iv_image.setVisibility(View.VISIBLE);
                        } else {
                            iv_image.setVisibility(View.GONE);
                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<EvaluasiResponse> call, Throwable t) {
                View parentLayout = findViewById(android.R.id.content);
                Snackbar snackbar = Snackbar.make(parentLayout, "Koneksi gagal", Snackbar.LENGTH_LONG);
                snackbar.setDuration(5000);
                snackbar.show();
            }
        });

    }

    private void defaultOptionView() {
        ArrayList<TextView> options = new ArrayList<>();

        options.add(0, option_one);
        options.add(1, option_two);
        options.add(2, option_three);
        options.add(3, option_four);

        for (TextView option : options) {
            option.setTextColor(Color.parseColor("#7A8089"));
            option.setTypeface(ResourcesCompat.getFont(this, R.font.poppins_regular));
            option.setBackground(ContextCompat.getDrawable(this, R.drawable.default_option_border_bg));
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.se_option_one:
                selectedOptionView(option_one, 1);
                break;
            case R.id.se_option_two:
                selectedOptionView(option_two, 2);
                break;
            case R.id.se_option_three:
                selectedOptionView(option_three, 3);
                break;
            case R.id.se_option_four:
                selectedOptionView(option_four, 4);
                break;
            case R.id.se_submit:

                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++;

                    if (mCurrentPosition <= evaluasiList.size()) {
                        setQuestion();
                        option_one.setOnClickListener(this);
                        option_two.setOnClickListener(this);
                        option_three.setOnClickListener(this);
                        option_four.setOnClickListener(this);
                    } else {

                        Intent intent = new Intent(SoalEvaluasiActivity.this, ScoreActivity.class);
                        intent.putExtra("jawaban", mCorrectAnswer);
                        intent.putExtra("soal", evaluasiList.size());
                        startActivity(intent);
                        finish();
                    }
                } else {

                    Evaluasi question = evaluasiList.get(mCurrentPosition - 1);

                    if (Integer.parseInt(question.getJawaban_benar()) != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg);
                    } else {
                        mCorrectAnswer++;
                    }

                    answerView(Integer.parseInt(question.getJawaban_benar()), R.drawable.correct_option_border_bg);

                    if (mCurrentPosition == evaluasiList.size()) {
                        button.setText("Selesai");
                    } else {
                        button.setText("Pertanyaan Selanjutnya");
                    }

                    mSelectedOptionPosition = 0;
                }
                break;
        }
    }

    private void selectedOptionView(TextView tv, int selectedOptionNum) {
        defaultOptionView();
        mSelectedOptionPosition = selectedOptionNum;

        tv.setTextColor(Color.parseColor("#363A43"));
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        tv.setTypeface(ResourcesCompat.getFont(this, R.font.poppins_bold));
        tv.setBackground(ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg));

    }

    private void answerView(int answer, int drawableView) {

        switch (answer) {
            case 1:
                option_one.setBackground(ContextCompat.getDrawable(this, drawableView));
                break;
            case 2:
                option_two.setBackground(ContextCompat.getDrawable(this, drawableView));
                break;
            case 3:
                option_three.setBackground(ContextCompat.getDrawable(this, drawableView));
                break;
            case 4:
                option_four.setBackground(ContextCompat.getDrawable(this, drawableView));
                break;
        }

        option_one.setOnClickListener(null);
        option_two.setOnClickListener(null);
        option_three.setOnClickListener(null);
        option_four.setOnClickListener(null);


    }

    private void insertJawaban() {
    }

    private void deleteHistory() {
    }
}
