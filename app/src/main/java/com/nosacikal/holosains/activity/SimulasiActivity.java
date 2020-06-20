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

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.snackbar.Snackbar;
import com.nosacikal.holosains.R;
import com.nosacikal.holosains.adapter.SimulasiAdapter;
import com.nosacikal.holosains.api.RetrofitClient;
import com.nosacikal.holosains.models.Simulasi;
import com.nosacikal.holosains.models.SimulasiResponse;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SimulasiActivity extends AppCompatActivity {

    private SimulasiAdapter simulasiAdapter;
    private List<Simulasi> simulasiList;
    private RecyclerView simulasiRecyclerView;
    private Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulasi);

        simulasiRecyclerView = findViewById(R.id.simulasiRecyclerView);
        simulasiRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            progressDialog = new Dialog(this);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.setContentView(R.layout.custom_dialog_progress);

            if (progressDialog.getWindow() != null)
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            progressDialog.show();
            getSimulasi();
        } else {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make(parentLayout, "Kamu sedang offline, periksa koneksi kamu..", Snackbar.LENGTH_LONG);
            snackbar.setDuration(5000);
            snackbar.show();
        }

    }

    private void getSimulasi() {

        Call<SimulasiResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllSimulasi();

        call.enqueue(new Callback<SimulasiResponse>() {
            @Override
            public void onResponse(Call<SimulasiResponse> call, Response<SimulasiResponse> response) {

                progressDialog.dismiss();

                simulasiList = Objects.requireNonNull(response.body()).getSimulasi();

                if (simulasiList != null) {
                    simulasiAdapter = new SimulasiAdapter(simulasiList, SimulasiActivity.this);
                    simulasiRecyclerView.setAdapter(simulasiAdapter);
                } else {
                    simulasiRecyclerView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<SimulasiResponse> call, Throwable t) {
                new Handler().postDelayed(() -> progressDialog.dismiss(), 1500);
                View parentLayout = findViewById(android.R.id.content);
                Snackbar snackbar = Snackbar.make(parentLayout, "Koneksi gagal", Snackbar.LENGTH_LONG);
                snackbar.setDuration(5000);
                snackbar.show();
            }
        });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateSlideDown(this); //fire the slide left animation
    }
}