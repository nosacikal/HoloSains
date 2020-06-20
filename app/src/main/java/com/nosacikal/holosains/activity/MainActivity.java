package com.nosacikal.holosains.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.nosacikal.holosains.R;
import com.nosacikal.holosains.adapter.MainAdapter;
import com.nosacikal.holosains.models.MainModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private RecyclerView menuRecyclerView;

    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuRecyclerView = findViewById(R.id.menuRecyclerView);

        // Nav Bar
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        // Nav Bar End


        findViewById(R.id.menuRecyclerView).setFocusable(false);
        findViewById(R.id.toolbar).requestFocus();

        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuRecyclerView.setNestedScrollingEnabled(false);

        setupMenu();
    }

    private void setupMenu() {
        List<MainModel> mainModelList = new ArrayList<>();

        MainModel menu1 = new MainModel();
        menu1.setId("1");
        menu1.setTitle("Materi");
        menu1.setDeskripsi("Materi organ gerak hewan dan manusia");
        menu1.setImage(R.drawable.icon_materi);

        MainModel menu2 = new MainModel();
        menu2.setId("2");
        menu2.setTitle("Simulasi");
        menu2.setDeskripsi("Cobain simulasi dengan hologram");
        menu2.setImage(R.drawable.icon_simulasi);

        MainModel menu3 = new MainModel();
        menu3.setId("3");
        menu3.setTitle("Evaluasi");
        menu3.setDeskripsi("Yuk kerjain soal evaluasinya");
        menu3.setImage(R.drawable.icon_evaluasi);


        mainModelList.add(menu1);
        mainModelList.add(menu2);
        mainModelList.add(menu3);

        MainAdapter mainAdapter = new MainAdapter(mainModelList, MainActivity.this);
        menuRecyclerView.setAdapter(mainAdapter);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_logout:

                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.remove("logged");
                    editor.apply();
                    finish();

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Animatoo.animateSlideDown(this);
                    finish();

                    break;
                } else {
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar snackbar = Snackbar.make(parentLayout, "Kamu sedang offline, periksa koneksi kamu..", Snackbar.LENGTH_LONG);
                    snackbar.setDuration(5000);
                    snackbar.show();
                }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
