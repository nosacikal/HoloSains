package com.nosacikal.holosains.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Simulasi implements Parcelable {
    private int id_simulasi;
    private int id_sub_tema;
    private String judul_simulasi;
    private String video;
    private String keyword;
    @SerializedName("judul")
    private String sub_tema;


    public int getId_simulasi() {
        return id_simulasi;
    }

    public int getId_sub_tema() {
        return id_sub_tema;
    }

    public String getJudul_simulasi() {
        return judul_simulasi;
    }

    public String getVideo() {
        return video;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getSub_tema() {
        return sub_tema;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id_simulasi);
        dest.writeInt(this.id_sub_tema);
        dest.writeString(this.judul_simulasi);
        dest.writeString(this.video);
        dest.writeString(this.keyword);
        dest.writeString(this.sub_tema);
    }

    public Simulasi() {
    }

    protected Simulasi(Parcel in) {
        this.id_simulasi = in.readInt();
        this.id_sub_tema = in.readInt();
        this.judul_simulasi = in.readString();
        this.video = in.readString();
        this.keyword = in.readString();
        this.sub_tema = in.readString();
    }

    public static final Creator<Simulasi> CREATOR = new Creator<Simulasi>() {
        @Override
        public Simulasi createFromParcel(Parcel source) {
            return new Simulasi(source);
        }

        @Override
        public Simulasi[] newArray(int size) {
            return new Simulasi[size];
        }
    };
}
