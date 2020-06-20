package com.nosacikal.holosains.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.snackbar.Snackbar;
import com.nosacikal.holosains.R;
import com.nosacikal.holosains.api.RetrofitClient;
import com.nosacikal.holosains.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtNis, edtPassword;
    public static final String PREFS_NAME = "LoginPrefs";
    private Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getString("logged", "").toString().equals("logged")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        edtNis = findViewById(R.id.edt_nis);
        edtPassword = findViewById(R.id.edt_password);

        findViewById(R.id.btn_login).setOnClickListener(this);
    }

    private void userLogin() {
        String nis = edtNis.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (nis.isEmpty()) {
            progressDialog.dismiss();
            edtNis.setError("NIS harus diisi");
            edtNis.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            progressDialog.dismiss();
            edtPassword.setError("Password harus diisi");
            edtPassword.requestFocus();
            return;
        }

        Call<LoginResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .loginSiswa(nis, password);

        call.enqueue(new Callback<LoginResponse>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                LoginResponse loginResponse = response.body();

                if (response.body() != null) {

                    assert loginResponse != null;
                    if (!loginResponse.isStatus()) {
                        new Handler().postDelayed(() -> progressDialog.dismiss(), 1500);
                        new TTFancyGifDialog.Builder(LoginActivity.this)
                                .setTitle("Terjadi Kesalahan")
                                .setMessage("Pastikan NIS dan Password yang kamu masukkan itu benar")
                                .setPositiveBtnText("Kembali")
                                .setPositiveBtnBackground("#e74c3c")
                                .setGifResource(R.drawable.splash)      //pass your gif, png or jpg
                                .isCancellable(true)
                                .build();
                    } else {
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("logged", "logged");
                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        Animatoo.animateSlideUp(LoginActivity.this);
                    }
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                new Handler().postDelayed(() -> progressDialog.dismiss(), 1500);
            }
        });

    }


    @Override
    public void onClick(View v) {

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            progressDialog = new Dialog(this);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.setContentView(R.layout.custom_dialog_progress);

            if (progressDialog.getWindow() != null)
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            progressDialog.show();

            userLogin();
        } else {
            new Handler().postDelayed(() -> progressDialog.dismiss(), 1500);
            View parentLayout = findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make(parentLayout, "Kamu sedang offline, periksa koneksi kamu..", Snackbar.LENGTH_LONG);
            snackbar.setDuration(5000);
            snackbar.show();
        }
    }
}