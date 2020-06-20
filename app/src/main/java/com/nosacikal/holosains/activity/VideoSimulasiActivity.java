package com.nosacikal.holosains.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.nosacikal.holosains.R;
import com.nosacikal.holosains.api.Api;
import com.nosacikal.holosains.api.RetrofitClient;
import com.nosacikal.holosains.models.Simulasi;
import com.nosacikal.holosains.models.SimulasiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class VideoSimulasiActivity extends AppCompatActivity {

    private static final int PERMISSIONS_RECORD_AUDIO = 1;
    private VideoView videoView;
    private SpeechRecognizer speechRecognizer;
    private Intent speechRecognitionIntent;
    private String keeper = "";
    private List<Simulasi> simulasiList = new ArrayList<>();
    private Simulasi simulasiModel;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_simulasi);

        checkVoiceCommandPermission();
        RelativeLayout parentLayout = findViewById(R.id.parentLayout);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(VideoSimulasiActivity.this);
        speechRecognitionIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognitionIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognitionIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "id");
        speechRecognitionIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 1000);
        speechRecognitionIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 1000);
        speechRecognitionIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 1500);

        videoView = findViewById(R.id.videoSimulasi);

        simulasiModel = getIntent().getParcelableExtra("Simulasi");

        if (simulasiModel != null) {

            videoView.setVideoPath(Api.IMAGE_SIMULASI_URL + simulasiModel.getVideo());

            MediaController mediaController = new
                    MediaController(this);

            mediaController.setAnchorView(videoView);

            videoView.setOnPreparedListener(mp -> {
                mp.setLooping(true);
                videoView.start();
            });

            speechRecognizer.setRecognitionListener(new RecognitionListener() {

                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {
                }

                @Override
                public void onError(int error) {
                }

                @Override
                public void onResults(Bundle results) {
                    speechToText(results);
                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });

            parentLayout.setOnTouchListener((v, event) -> {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        speechRecognizer.startListening(speechRecognitionIntent);
                        break;

                    case MotionEvent.ACTION_UP:
                        speechRecognizer.stopListening();
                        break;
                }

                return false;
            });

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void checkVoiceCommandPermission() {

        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_RECORD_AUDIO);
            return;
        }
    }

    private void speechToText(Bundle results) {

        ArrayList<String> matchesFound = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        Call<SimulasiResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllSimulasi();

        call.enqueue(new Callback<SimulasiResponse>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<SimulasiResponse> call, Response<SimulasiResponse> response) {
                assert response.body() != null;
                simulasiList = response.body().getSimulasi();

                if (simulasiList != null) {

                    if (matchesFound != null) {
                        keeper = matchesFound.get(0);

                        int index = Objects.requireNonNull(getIntent().getExtras()).getInt("SimulasiIndex");

                        if (keeper.equals(Objects.requireNonNull(simulasiModel).getKeyword().trim().toLowerCase())) {

                            if (index < simulasiList.size() - 1) {
                                Simulasi simulasi = simulasiList.get(index + 1);
                                Intent intent = new Intent(getBaseContext(), VideoSimulasiActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("Simulasi", simulasi);
                                intent.putExtra("SimulasiIndex", index + 1);
                                startActivity(intent);
                                Animatoo.animateSlideLeft(VideoSimulasiActivity.this);
                            } else {
                                Intent intent = new Intent(getBaseContext(), SimulasiActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }

                        } else if (keeper.equals("kembali")) {

                            if (simulasiList.size() != 0 && index != 0) {
                                Simulasi simulasi = simulasiList.get(index - 1);
                                Intent intent = new Intent(getBaseContext(), VideoSimulasiActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("Simulasi", simulasi);
                                intent.putExtra("SimulasiIndex", index - 1);
                                startActivity(intent);
                                Animatoo.animateSlideRight(VideoSimulasiActivity.this);
                            }
                        } else if (keeper.equals("keluar")) {
                            Intent intent = new Intent(getBaseContext(), SimulasiActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }

                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<SimulasiResponse> call, Throwable t) {

            }
        });
    }


}
