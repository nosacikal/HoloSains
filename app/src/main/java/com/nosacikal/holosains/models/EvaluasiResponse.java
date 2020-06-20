package com.nosacikal.holosains.models;

import java.util.List;

public class EvaluasiResponse {
    private boolean status;
    private String message;
    private List<Evaluasi> evaluasi;

    public EvaluasiResponse(boolean status, String message, List<Evaluasi> evaluasi) {
        this.status = status;
        this.message = message;
        this.evaluasi = evaluasi;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Evaluasi> getEvaluasi() {
        return evaluasi;
    }
}
