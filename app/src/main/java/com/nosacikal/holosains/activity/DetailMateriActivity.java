package com.nosacikal.holosains.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.nosacikal.holosains.R;
import com.nosacikal.holosains.api.Api;
import com.nosacikal.holosains.api.RetrofitClient;
import com.nosacikal.holosains.models.Materi;
import com.nosacikal.holosains.models.MateriResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class DetailMateriActivity extends AppCompatActivity {

    private Button btnNext;
    private Button btnPrev;
    private List<Materi> materiList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_materi);

        TextView judulMateri = findViewById(R.id.dt_judul_materi);
        TextView subTema = findViewById(R.id.dt_sub_tema);
        TextView isiMateri = findViewById(R.id.dt_isi_materi);
        ImageView imageMateri = findViewById(R.id.dt_image);
        btnNext = findViewById(R.id.dtn_btn_next);
        btnPrev = findViewById(R.id.dtn_btn_prev);

        Materi materiModel = getIntent().getParcelableExtra("Materi");

        if (materiModel != null) {
            judulMateri.setText(materiModel.getJudul_materi());
            subTema.setText(materiModel.getSub_tema());
            isiMateri.setText(materiModel.getIsi_materi());
            Glide.with(this).load(Api.IMAGE_MATERI_URL + materiModel.getGambar()).into(imageMateri);

            getMateri();
        }

    }

    private void getMateri() {
        Call<MateriResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllMateri();

        call.enqueue(new Callback<MateriResponse>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<MateriResponse> call, Response<MateriResponse> response) {
                assert response.body() != null;
                materiList = response.body().getMateri();

                int index = Objects.requireNonNull(getIntent().getExtras()).getInt("index");

                if (index == 0 ) {
                    btnNext.setVisibility(View.VISIBLE);
                    btnPrev.setVisibility(View.GONE);
                } else if (index == materiList.size() - 1) {
                    btnNext.setVisibility(View.GONE);
                    btnPrev.setVisibility(View.VISIBLE);
                }

                if (materiList != null) {

                    btnNext.setOnClickListener(v -> {

                        if (index < materiList.size() - 1) {

                            Materi materi = materiList.get(index + 1);
                            Intent intent = new Intent(DetailMateriActivity.this, DetailMateriActivity.class);
                            intent.putExtra("Materi", materi);
                            intent.putExtra("index", index+1);
                            startActivity(intent);
                            Animatoo.animateSlideLeft(DetailMateriActivity.this);
                        }
                    });

                    btnPrev.setOnClickListener(v -> {

                        if (materiList.size() != 0 && index != 0) {
                            Materi materi = materiList.get(index - 1);
                            Intent intent = new Intent(DetailMateriActivity.this, DetailMateriActivity.class);
                            intent.putExtra("Materi", materi);
                            intent.putExtra("index", index-1);
                            startActivity(intent);
                            Animatoo.animateSlideRight(DetailMateriActivity.this);
                        }
                    });
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<MateriResponse> call, Throwable t) {
                Toast.makeText(DetailMateriActivity.this, "Periksa koneksi internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateSlideDown(this); //fire the slide left animation
    }

}
