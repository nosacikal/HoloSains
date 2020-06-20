package com.nosacikal.holosains.models;

import java.util.List;

public class SimulasiResponse {

    private boolean status;
    private String message;
    private List<Simulasi> simulasi;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Simulasi> getSimulasi() {
        return simulasi;
    }
}
