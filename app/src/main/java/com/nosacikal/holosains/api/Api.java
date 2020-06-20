package com.nosacikal.holosains.api;

import com.nosacikal.holosains.models.EvaluasiResponse;
import com.nosacikal.holosains.models.LoginResponse;
import com.nosacikal.holosains.models.MateriResponse;
import com.nosacikal.holosains.models.SimulasiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {
    String BASE = "http://192.168.1.3/";

    String IMAGE_MATERI_URL = BASE + "holo-sains/assets/img/materi/";
    String IMAGE_SIMULASI_URL = BASE + "holo-sains/assets/img/simulasi/";
    String IMAGE_SOAL_URL = BASE + "holo-sains/assets/img/soal/";

    @FormUrlEncoded
    @POST("siswa/login")
    Call<LoginResponse> loginSiswa(
            @Field("nis") String nis,
            @Field("password") String password
    );

    @GET("materi")
    Call<MateriResponse> getAllMateri();

    @GET("simulasi")
    Call<SimulasiResponse> getAllSimulasi();

    @GET("evaluasi")
    Call<EvaluasiResponse> getAllEvaluasi();

}
