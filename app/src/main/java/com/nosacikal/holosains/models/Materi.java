package com.nosacikal.holosains.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Materi implements Parcelable {

    private String id_materi;
    @SerializedName("judul")
    private String sub_tema;
    private String judul_materi;
    private String isi_materi;
    private String gambar;

    public String getId_materi() {
        return id_materi;
    }

    public String getSub_tema() {
        return sub_tema;
    }

    public String getJudul_materi() {
        return judul_materi;
    }

    public String getIsi_materi() {
        return isi_materi;
    }

    public String getGambar() {
        return gambar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id_materi);
        dest.writeString(this.sub_tema);
        dest.writeString(this.judul_materi);
        dest.writeString(this.isi_materi);
        dest.writeString(this.gambar);
    }

    public Materi() {
    }

    protected Materi(Parcel in) {
        this.id_materi = in.readString();
        this.sub_tema = in.readString();
        this.judul_materi = in.readString();
        this.isi_materi = in.readString();
        this.gambar = in.readString();
    }

    public static final Parcelable.Creator<Materi> CREATOR = new Parcelable.Creator<Materi>() {
        @Override
        public Materi createFromParcel(Parcel source) {
            return new Materi(source);
        }

        @Override
        public Materi[] newArray(int size) {
            return new Materi[size];
        }
    };
}
