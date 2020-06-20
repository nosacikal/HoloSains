package com.nosacikal.holosains.models;

public class LoginResponse {
    private boolean status;
    private String message;
    private Siswa siswa;

    public LoginResponse(boolean status, String message, Siswa siswa) {
        this.status = status;
        this.message = message;
        this.siswa = siswa;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Siswa getSiswa() {
        return siswa;
    }
}
