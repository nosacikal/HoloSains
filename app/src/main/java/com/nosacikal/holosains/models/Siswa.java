package com.nosacikal.holosains.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Siswa implements Parcelable {
    private int id_siswa;
    private int id_kelas;
    private String nama_siswa;
    private String nis;
    private String password;

    public Siswa(int id_siswa, int id_kelas, String nama_siswa, String nis, String password) {
        this.id_siswa = id_siswa;
        this.id_kelas = id_kelas;
        this.nama_siswa = nama_siswa;
        this.nis = nis;
        this.password = password;
    }

    public int getId_siswa() {
        return id_siswa;
    }

    public int getId_kelas() {
        return id_kelas;
    }

    public String getNama_siswa() {
        return nama_siswa;
    }

    public String getNis() {
        return nis;
    }

    public String getPassword() {
        return password;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id_siswa);
        dest.writeInt(this.id_kelas);
        dest.writeString(this.nama_siswa);
        dest.writeString(this.nis);
        dest.writeString(this.password);
    }

    protected Siswa(Parcel in) {
        this.id_siswa = in.readInt();
        this.id_kelas = in.readInt();
        this.nama_siswa = in.readString();
        this.nis = in.readString();
        this.password = in.readString();
    }

    public static final Parcelable.Creator<Siswa> CREATOR = new Parcelable.Creator<Siswa>() {
        @Override
        public Siswa createFromParcel(Parcel source) {
            return new Siswa(source);
        }

        @Override
        public Siswa[] newArray(int size) {
            return new Siswa[size];
        }
    };
}
