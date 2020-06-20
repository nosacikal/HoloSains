package com.nosacikal.holosains.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.snackbar.Snackbar;
import com.nosacikal.holosains.R;
import com.nosacikal.holosains.adapter.MateriAdapter;
import com.nosacikal.holosains.api.RetrofitClient;
import com.nosacikal.holosains.models.Materi;
import com.nosacikal.holosains.models.MateriResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MateriActivity extends AppCompatActivity {

    private MateriAdapter materiAdapter;
    private List<Materi> materiList;
    private RecyclerView materiRecyclerView;
    private Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi);

        materiRecyclerView = findViewById(R.id.materiRecyclerView);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            progressDialog = new Dialog(this);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.setContentView(R.layout.custom_dialog_progress);

            if (progressDialog.getWindow() != null)
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            progressDialog.show();

            materiRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            getMateri();
        } else {

            View parentLayout = findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make(parentLayout, "Kamu sedang offline, periksa koneksi kamu..", Snackbar.LENGTH_LONG);
            snackbar.setDuration(5000);
            snackbar.show();
        }

    }

    private void getMateri() {

        Call<MateriResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllMateri();

        call.enqueue(new Callback<MateriResponse>() {
            @Override
            public void onResponse(Call<MateriResponse> call, Response<MateriResponse> response) {
                progressDialog.dismiss();

                assert response.body() != null;
                materiList = response.body().getMateri();

                if (materiList != null) {
                    materiAdapter = new MateriAdapter(materiList, MateriActivity.this);
                    materiRecyclerView.setAdapter(materiAdapter);
                }

            }

            @Override
            public void onFailure(Call<MateriResponse> call, Throwable t) {
                new Handler().postDelayed(() -> progressDialog.dismiss(), 1500);
                View parentLayout = findViewById(android.R.id.content);
                Snackbar snackbar = Snackbar.make(parentLayout, "Koneksi gagal", Snackbar.LENGTH_LONG);
                snackbar.setDuration(5000);
                snackbar.show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideDown(this); //fire the slide left animation
    }
}
