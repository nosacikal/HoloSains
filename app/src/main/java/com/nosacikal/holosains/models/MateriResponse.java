package com.nosacikal.holosains.models;

import java.util.List;

public class MateriResponse {
    private boolean status;
    private String message;
    private List<Materi> materi;

    public MateriResponse(boolean status, String message, List<Materi> materi) {
        this.status = status;
        this.message = message;
        this.materi = materi;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Materi> getMateri() {
        return materi;
    }
}
