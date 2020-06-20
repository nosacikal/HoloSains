package com.nosacikal.holosains.models;


public class Evaluasi {
    private String id_soal_evaluasi;
    private String id_sub_tema;
    private String pertanyaan;
    private String pilihan_A;
    private String pilihan_B;
    private String pilihan_C;
    private String pilihan_D;
    private String jawaban_benar;
    private String gambar;

    public String getId_soal_evaluasi() {
        return id_soal_evaluasi;
    }

    public String getId_sub_tema() {
        return id_sub_tema;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public String getPilihan_A() {
        return pilihan_A;
    }

    public String getPilihan_B() {
        return pilihan_B;
    }

    public String getPilihan_C() {
        return pilihan_C;
    }

    public String getPilihan_D() {
        return pilihan_D;
    }

    public String getJawaban_benar() {
        return jawaban_benar;
    }

    public String getGambar() {
        return gambar;
    }
}
